package com.facebook.buck.intellijplugin.runner;

import com.facebook.buck.intellijplugin.components.BuckConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.util.EnvironmentUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Buck run parameters capture the required project names and other buck
 * parameters passed to buck.
 *
 * @author code@damienallison.com
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
    String projects = BuckConfiguration.getProjectNames(project);
    return Arrays.asList(projects);
  }

  public String getWorkingDirectory() {
    return ".";
  }

  public String[] getEnvironment() {
    return EnvironmentUtil.getEnvironment();
  }
}
