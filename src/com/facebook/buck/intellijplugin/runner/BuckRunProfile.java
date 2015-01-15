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
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Buck run profile which sub classes
 */
public class BuckRunProfile implements RunProfile {

  @Nullable
  @Override
  public RunProfileState getState(Executor executor,
      ExecutionEnvironment executionEnvironment) throws ExecutionException {
    return new BuckRunProfileState(executionEnvironment,
        new BuckRunParameters(executionEnvironment.getProject()));
  }

  @Override
  public String getName() {
    return "BuckPlugin.RunProfile";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    //
    return null;
  }
}
