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

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.GenericProgramRunner;
import org.jetbrains.annotations.NotNull;

/**
 * The buck runner offers a basis for external compile and run functions.
 *
 * @author code@damienallison.com
 */
public class BuckRunner extends GenericProgramRunner {
  @NotNull
  @Override
  public String getRunnerId() {
    return BuckPlugin.RUNNER_ID;
  }

  @Override
  public boolean canRun(@NotNull String executorId, @NotNull RunProfile runProfile) {
    return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) &&
        runProfile instanceof BuckRunProfile;
  }
}
