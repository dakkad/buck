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
public class BuckProjectBackgroundTask extends Task.Backgroundable {

  private static final String DEFAULT_TITLE = "Buck Project";

  public BuckProjectBackgroundTask(Project project,
      String title, boolean canBeCancelled,
      PerformInBackgroundOption backgroundOption) {
    super(project, title, canBeCancelled, backgroundOption);
  }

  public BuckProjectBackgroundTask(Project project, String title,
      boolean canBeCancelled) {
    super(project, title, canBeCancelled);
  }

  public BuckProjectBackgroundTask(Project project, String title) {
    super(project, title);
  }

  public BuckProjectBackgroundTask(Project project) {
    super(project, DEFAULT_TITLE);
  }

  @Override
  public void run(ProgressIndicator progressIndicator) {
    getProject().
    int i = 0;
    while (i < 5) {
      progressIndicator.setFraction((double) i / 4.0);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
      }
      i++;
      progressIndicator.setFraction(1.0);
    }

  }
}
