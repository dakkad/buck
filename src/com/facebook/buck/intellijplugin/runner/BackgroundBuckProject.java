package com.facebook.buck.intellijplugin.runner;

import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

/**
 * Background buck task to run buck project.
 *
 * @author code@damienallison.com
 */
public class BackgroundBuckProject extends Task.Backgroundable {

  private static final String DEFAULT_TITLE = "Buck Project";

  public BackgroundBuckProject(Project project,
      String title, boolean canBeCancelled,
      PerformInBackgroundOption backgroundOption) {
    super(project, title, canBeCancelled, backgroundOption);
  }

  public BackgroundBuckProject(Project project, String title,
      boolean canBeCancelled) {
    super(project, title, canBeCancelled);
  }

  public BackgroundBuckProject(Project project, String title) {
    super(project, title);
  }

  public BackgroundBuckProject(Project project) {
    super(project, DEFAULT_TITLE);
  }

  @Override
  public void run(ProgressIndicator progressIndicator) {


  }
}
