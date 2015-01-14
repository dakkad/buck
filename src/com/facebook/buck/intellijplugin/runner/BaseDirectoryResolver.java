package com.facebook.buck.intellijplugin.runner;

import com.intellij.openapi.project.Project;

/**
 * Base directory resolution methods.
 *
 * @author code@damienallison.com
 */
public abstract class BaseDirectoryResolver {

  private BaseDirectoryResolver() {}

  /**
   * Resolve the base directory based on internal state.
   *
   * @return base directory path
   */
  public abstract String resolve();

  public static BaseDirectoryResolver fromProject(Project project) {
    return new ProjectBaseDirectoryResolver(project);
  }

  private static class ProjectBaseDirectoryResolver extends BaseDirectoryResolver {

    private final Project project;

    private ProjectBaseDirectoryResolver(Project project) {
      this.project = project;
    }

    @Override
    public String resolve() {
      // TODO(dka) 20150114 Start at the current file and travel up to base dir
      return project.getBasePath();
    }
  }
}
