/*
 * Copyright 2013-present Facebook, Inc.
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

package com.facebook.buck.intellijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Buck run profile state captures the different aspects of a runner profile.
 *
 * @author code@damienallison.com
 */
public class BuckRunProfileState extends CommandLineState {

  private static Logger log = Logger.getInstance("BuckPlugin.BuckRunProfileState");
  private final BuckRunParameters parameters;

  public BuckRunProfileState(ExecutionEnvironment executionEnvironment,
      BuckRunParameters parameters) {
    super(executionEnvironment);
    this.parameters = parameters;
  }

  @NotNull
  @Override
  protected ProcessHandler startProcess() throws ExecutionException {
    log.info("Starting Buck Command Line Process");
    GeneralCommandLine commandLine = new GeneralCommandLine();
    String command = parameters.getFullCommand();
    commandLine.setExePath(command);
    commandLine.setPassParentEnvironment(true);
    commandLine.addParameters(parameters.getArguments());
    OSProcessHandler processHandler = new ColoredProcessHandler(
        commandLine.withWorkDirectory(parameters.getWorkingDirectory())
        .createProcess(), commandLine.getCommandLineString());
    ProcessTerminatedListener.attach(processHandler, getEnvironment().getProject());
    return processHandler;
  }
}
