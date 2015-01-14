package com.facebook.buck.intellijplugin.runner;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

/**
 * Base directory resolution methods.
 *
 * @author code@damienallison.com
 */
public abstract class BaseDirectoryResolver {

  private static final Logger LOG = Logger.getInstance(BaseDirectoryResolver.class);
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
      ProjectRootManager contentRootManager = ProjectRootManager.getInstance(project);
      VirtualFile[] roots = contentRootManager.getContentRoots();
      if (null == roots) {
        throw new IOException("No content root set for project");
      }
      String basePath = null;
      for (VirtualFile candidate : roots) {
        LOG.info("Considering Buck Content Root " + candidate.getPath());
        VirtualFile[] children = candidate.getChildren();
        if (null == children) {
          continue;
        }
        for (VirtualFile child : children) {
          LOG.info("Considering child file " + child.getPath() +
              " and name " + child.getName());
          if (child.getName().equals(BUCK_CONFIG_FILE)) {
            basePath = candidate.getPath();
            break;
          }
        }
      }
      if (null == basePath) {
        throw new IOException("Base path not found");
      }
      return basePath;
    }
  }
}
