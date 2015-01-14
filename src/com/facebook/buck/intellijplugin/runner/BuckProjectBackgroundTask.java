package com.facebook.buck.intellijplugin.runner;

import com.facebook.buck.intellijplugin.buckprocess.BuckWatcher;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

import java.io.File;
import java.io.IOException;

/**
 * Background buck task to run buck project.
 *
 * @author code@damienallison.com
 */
public class BuckProjectBackgroundTask extends Task.Backgroundable {

  private static final String DEFAULT_TITLE = "Buck Project";
  private static final Logger LOG = Logger.getInstance(BuckProjectBackgroundTask.class);
  private static final double DONE_FRACTION = 1.0;

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
    progressIndicator.start();
    try {
      Runtime runtime = Runtime.getRuntime();
      progressIndicator.setFraction((double)runtime.freeMemory() /
          (double)runtime.maxMemory());

      BuckRunParameters parameters = new BuckRunParameters();
      BaseDirectoryResolver baseResolver = BaseDirectoryResolver.fromProject(getProject());
      Process process = runtime.exec(parameters.getFullCommand(),
          parameters.getEnvironment(), new File(baseResolver.resolve()));

      BuckWatcher watcher = BuckWatcher.fromProcess(process, progressIndicator);
      watcher.watch();

    } catch (IOException e) {
      progressIndicator.cancel();
      progressIndicator.setText("Buck Project Failed: " + e.getMessage());
      LOG.error("Error running buck project", e);
    }
    progressIndicator.setFraction(DONE_FRACTION);
    progressIndicator.stop();
  }
}
