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

import com.intellij.openapi.project.Project;
import com.intellij.util.EnvironmentUtil;

/**
 * Buck run parameters capture the required project names and other buck
 * parameters passed to buck.
 */
public class BuckRunParameters implements Cloneable {

  private static final String DEFAULT_BUCK_COMMAND = "buck";
  private static final String PADDING = " ";

  private String workingDirectory;
  private String arguments;

  public BuckRunParameters(Project project) {
    this.workingDirectory = project.getBasePath();
  }

  private BuckRunParameters() { }

  public String getFullCommand() {
    StringBuilder builder = new StringBuilder(16);
    builder.append(DEFAULT_BUCK_COMMAND)
        .append(PADDING)
        .append(arguments)
        .append(PADDING);
    return builder.toString();
  }

  public String getArguments() {
    return arguments;
  }

  public String getWorkingDirectory() {
    return workingDirectory;
  }

  public String[] getEnvironment() {
    return EnvironmentUtil.getEnvironment();
  }

  @Override
  protected Object clone() {
    BuckRunParameters result = new BuckRunParameters();
    result.setWorkingDirectory(getWorkingDirectory());
    result.setArguments(getArguments());
    return result;
  }

  public void setWorkingDirectory(String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  public void setArguments(String arguments) {
    this.arguments = arguments;
  }
}
