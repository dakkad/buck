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

package com.facebook.buck.intellijplugin.execution;

import com.facebook.buck.intellijplugin.runner.BuckRunParameters;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;

/**
 * Buck running state information and execution launcher.
 */
public class BuckRunningState extends CommandLineState implements RunProfileState {

  private static final boolean PASS_ENVIRONMENT = true;
  private final BuckRunParameters parameters;

  public BuckRunningState(ExecutionEnvironment environment,
      BuckRunParameters parameters) {
    super(environment);
    this.parameters = parameters;
    addConsoleFilters(new BuckRunFilter(environment.getProject()));
  }

  public BuckRunParameters getParameters() {
    return parameters;
  }

  @Override
  protected ProcessHandler startProcess() throws ExecutionException {
    GeneralCommandLine commandLine = getCommandLine();
    OSProcessHandler processHandler = new ColoredProcessHandler(commandLine.createProcess(),
        commandLine.getCommandLineString());
    ProcessTerminatedListener.attach(processHandler, getEnvironment().getProject());
    return processHandler;
  }

  public GeneralCommandLine getCommandLine() throws ExecutionException {
    GeneralCommandLine result = new GeneralCommandLine();
    String command = parameters.getCommand();
    if (null == command) {
      throw new ExecutionException("Invalid execution command: " + command);
    }
    result.setExePath(command);
    result.setPassParentEnvironment(PASS_ENVIRONMENT);
    for (String argument : parameters.getArgumentList()) {
      result.addParameter(argument);
    }
    return result.withWorkDirectory(parameters.getWorkingDirectory());
  }
}
