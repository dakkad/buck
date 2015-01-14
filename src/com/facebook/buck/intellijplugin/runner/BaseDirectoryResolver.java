package com.facebook.buck.intellijplugin.runner;

import com.intellij.openapi.project.Project;

import java.io.File;
import java.io.IOException;

/**
 * Base directory resolution methods.
 *
 * @author code@damienallison.com
 */
public abstract class BaseDirectoryResolver {

  private static final String BUCK_CONFIG_FILE = ".buckconfig";

  private BaseDirectoryResolver() {}

  /**
   * Resolve the base directory based on internal state.
   *
   * @return base directory path
   */
  public abstract String resolve() throws IOException;

  public static BaseDirectoryResolver fromProject(Project project) {
    return new ProjectBaseDirectoryResolver(project);
  }

  private static class ProjectBaseDirectoryResolver extends BaseDirectoryResolver {


    private final Project project;

    private ProjectBaseDirectoryResolver(Project project) {
      this.project = project;
    }

    @Override
    public String resolve() throws IOException {
      // TODO// (dka) 20150114 Start at the current file and travel up to base dir
      String basePath = project.getBasePath();
      File base = new File(basePath);
      if (!base.isDirectory()) {
        throw new IOException("Could not resolve buck project base directory. " +
            basePath + " not found");
      }
      File config = new File(base, BUCK_CONFIG_FILE);
      if (!config.isFile() || !config.exists()) {
        throw new IOException("Buck config file missing at " + basePath);
      }
      return basePath;
    }
  }
}
