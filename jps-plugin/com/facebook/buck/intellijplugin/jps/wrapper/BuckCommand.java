/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.intellijplugin.jps.wrapper;

import com.facebook.buck.intellijplugin.jps.model.BuckBuildTarget;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Buck command runner which wraps up the buck commands.
 */
public class BuckCommand {

  private static final Logger LOG = Logger.getInstance(BuckCommand.class);
  private static final String BUCK_BIN = "buck";
  private static final String BUCKD_BIN = "buckd";
  private static final String BUCK_EXTRA_JAVA_ARGS = "BUCK_EXTRA_JAVA_ARGS";
  private static final String BUCKD_HTTPSERVER_PORT = "-Dbuck.httpserver.port";
  private static final String NEW_LINE = "\n";

  private File workingDirectory;
  private ExecutorService executorService;
  private String buckPath = "/usr/bin/buck";
  private Optional<String> buckdPath;
  private Optional<BuckdSocketClient> socket;
  private BuckEventListener listener;
  private String stdout;
  private String stderr;

  public BuckCommand(Project project, Optional<String> buckDirectory,
      BuckEventListener listener) throws BuckNotFoundError {
    Preconditions.checkNotNull(project);
    Preconditions.checkNotNull(buckDirectory);
    this.listener = Preconditions.checkNotNull(listener);
    buckdPath = Optional.absent();
    socket = Optional.absent();
    stdout = "";
    stderr = "";
    workingDirectory = new File(project.getBasePath());
    Preconditions.checkState(workingDirectory.isDirectory(),
        String.format("%s is not a valid working directory.", workingDirectory));
    executorService = Executors.newFixedThreadPool(2); // For stdout and stderr stream readers
  }

  public BuckCommand(BuckBuildTarget buckBuildTarget, File workingDirectory,
      BuckEventListener listener) {
    buckdPath = Optional.absent();
    socket = Optional.absent();
    stdout = "";
    stderr = "";
    this.workingDirectory = workingDirectory;
    this.listener = listener;
    executorService = Executors.newFixedThreadPool(2); // For stdout and stderr stream readers
  }

  public int executeAndListenToWebSocket(String... args) {
    if (socket.isPresent()) {
      socket.get().start();
    }
    int exitCode = execute(args);
    if (socket.isPresent()) {
      socket.get().stop();
    }
    return exitCode;
  }

  public int execute(String... args) {
    ImmutableList<String> command = ImmutableList.<String>builder()
        .add(buckPath)
        .addAll(ImmutableList.copyOf(args))
        .build();
    return execute(command, ImmutableMap.<String, String>of());
  }

  private int execute(ImmutableList<String> command, ImmutableMap<String, String> environment) {
    Preconditions.checkNotNull(command);
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    LOG.info("Buck command using working directory " + workingDirectory);
    processBuilder.directory(workingDirectory);
    for (ImmutableMap.Entry<String, String> entry : environment.entrySet()) {
      processBuilder.environment().put(entry.getKey(), entry.getValue());
    }
    try {
      Process process = processBuilder.start();
      int exitCode;
      Future<String> stdOutFuture = readStream(process.getInputStream());
      Future<String> stdErrFuture = readStream(process.getErrorStream());
      exitCode = process.waitFor();
      stdout = stdOutFuture.get();
      stderr = stdErrFuture.get();
      return exitCode;
    } catch (ExecutionException | InterruptedException | IOException e) {
      LOG.error(e);
    }
    return -1;
  }

  private Future<String> readStream(final InputStream stream) {
    return executorService.submit(new Callable<String>() {
      @Override
      public String call() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
          StringBuilder output = new StringBuilder();
          String line;
          while (null != (line = reader.readLine())) {
            output.append(line)
                .append(NEW_LINE);
          }
          return output.toString();
        } finally {
          reader.close();
        }
      }
    });
  }

  public String getStdOut() {
    return stdout;
  }

  public String getStdErr() {
    return stderr;
  }


  private boolean detectBuckUnderDirectory(String binDirectory) {
    boolean success = false;
    File buck = new File(binDirectory, BUCK_BIN);
    if (buck.canExecute()) {
      buckPath = buck.getAbsolutePath();
      success = true;
    }
    File buckd = new File(binDirectory, BUCKD_BIN);
    if (buckd.canExecute()) {
      buckdPath = Optional.of(buckd.getAbsolutePath());
    }
    return success;
  }

  public void launchBuckd() {
    if (buckdPath.isPresent()) {
      try {
        int port = findAvailablePortForBuckdHttpServer();
        int exitCode = execute(ImmutableList.of(buckdPath.get()),
            ImmutableMap.<String, String>of(BUCK_EXTRA_JAVA_ARGS,
                String.format("%s=%d", BUCKD_HTTPSERVER_PORT, port)));
        if (exitCode != 0) {
          throw new Exception(getStdErr());
        }
        socket = Optional.of(new BuckdSocketClient(port, listener));
      } catch (Exception e) {
        LOG.warn(String.format("Can not launch buckd: %s", e.getMessage()));
      }
    } else {
      LOG.warn("Can not find path to buckd, buck will be used barely.");
    }
  }

  private int findAvailablePortForBuckdHttpServer() throws IOException {
    ServerSocket server = new ServerSocket(0);
    int port = server.getLocalPort();
    server.close();
    return port;
  }

  public class BuckNotFoundError extends Error {
  }
}
