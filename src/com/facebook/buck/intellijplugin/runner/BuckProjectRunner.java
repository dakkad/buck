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
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * The buck project runner is a program runner which runs the external buck
 * command line tool.
 *
 * @author code@damienallison.com
 */
public class BuckProjectRunner extends DefaultProgramRunner {

  private static final String BUCK_PROJECT_RUNNER_ID = "BuckPlugin.ProjectRunner";

  @NotNull
  @Override
  public String getRunnerId() {
    return BUCK_PROJECT_RUNNER_ID;
  }

  @Override
  public boolean canRun(String executorId, RunProfile runProfile) {
    // TODO(dka) 20150113 Check the run profile to see if is a buck profile
    return true;
  }

  @Override
  protected RunContentDescriptor doExecute(@NotNull RunProfileState state,
      @NotNull ExecutionEnvironment env) throws ExecutionException {
    BuckRunProfileState buckProfile = new BuckRunProfileState(env,
        new BuckRunParameters(env.getProject()));
    return super.doExecute(buckProfile, env);
  }
}
