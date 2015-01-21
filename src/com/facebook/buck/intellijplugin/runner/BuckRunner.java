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

package com.facebook.buck.intellijplugin.runner;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.facebook.buck.intellijplugin.execution.BuckRunConfiguration;
import com.facebook.buck.intellijplugin.execution.BuckRunningState;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * External buck runner which calls through to buck for build and test of
 * targets.
 */
public class BuckRunner extends DefaultProgramRunner {

  @NotNull
  @Override
  public String getRunnerId() {
    return BuckPlugin.BUCK_RUNNER_ID;
  }

  @Override
  public boolean canRun(@NotNull String executorId, @NotNull RunProfile runProfile) {
    return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) &&
        runProfile instanceof BuckRunConfiguration;
  }

  /**
   * Note that this method has been deprecated in order to remove it in IJ 16.
   */
  @Override
  protected RunContentDescriptor doExecute(
      @NotNull Project project,
      @NotNull RunProfileState state,
      RunContentDescriptor contentToReuse,
      @NotNull ExecutionEnvironment environment
  ) throws ExecutionException {
    // Save all of the files before build.
    FileDocumentManager.getInstance().saveAllDocuments();
    // Configure the state as a buck running state with it's parameters. This
    // is necessary to build the buck parameters into the runner setup
    BuckRunConfiguration runProfile = (BuckRunConfiguration) environment.getRunProfile();
    BuckRunParameters parameters = runProfile.getParameters();
    BuckRunningState buckState = new BuckRunningState(environment, parameters);
    return super.doExecute(project, buckState, contentToReuse, environment);
  }

}
