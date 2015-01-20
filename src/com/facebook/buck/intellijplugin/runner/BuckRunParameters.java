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

import com.facebook.buck.intellijplugin.components.BuckConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.util.EnvironmentUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Buck run parameters capture the required project names and other buck
 * parameters passed to buck.
 */
public class BuckRunParameters {

  private static final String DEFAULT_BUCK_COMMAND = "buck";
  private static final String PADDING = " ";

  private final Project project;

  public BuckRunParameters(Project project) {
    this.project = project;
  }

  public String getFullCommand() {
    StringBuilder builder = new StringBuilder(16);
    builder.append(DEFAULT_BUCK_COMMAND)
        .append(PADDING);
    for (String argument : getArguments()) {
      builder.append(argument)
          .append(PADDING);
    }
    return builder.toString();
  }

  public List<String> getArguments() {
    String projects = BuckConfiguration.getTargetNames(project);
    return Arrays.asList(projects);
  }

  public String getWorkingDirectory() {
    return ".";
  }

  public String[] getEnvironment() {
    return EnvironmentUtil.getEnvironment();
  }
}
