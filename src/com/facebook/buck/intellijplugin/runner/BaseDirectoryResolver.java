package com.facebook.buck.intellijplugin.runner;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Base directory resolution methods.
 *
 * @author code@damienallison.com
 */
public abstract class BaseDirectoryResolver {

  private static final Logger LOG = Logger.getInstance(BaseDirectoryResolver.class);
  private static final String BUCK_CONFIG_FILE = ".buckconfig";

  private BaseDirectoryResolver() { }

  /**
   * Resolve the base directory based on internal state.
   *
   * @return base directory path.
   * @throws java.io.IOException when not able to resolve the base directory.
   */
  public abstract String resolve() throws IOException;

  /**
   * Base directory resolver factory which constructs a base directory resolver
   * from a project.
   * @param project context
   * @return base directory resolver
   */
  @NotNull
  public static BaseDirectoryResolver fromProject(final Project project) {
    return new ProjectBaseDirectoryResolver(project);
  }

  private static final class ProjectBaseDirectoryResolver
      extends BaseDirectoryResolver {

    private final Project project;

    private ProjectBaseDirectoryResolver(Project project) {
      this.project = project;
    }

    @Override
    public String resolve() throws IOException {
      // TODO// (dka) 20150114 Start at the current file and travel up to base dir
      ProjectRootManager contentRootManager = ProjectRootManager.getInstance(project);
      VirtualFile[] roots = contentRootManager.getContentRoots();
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
