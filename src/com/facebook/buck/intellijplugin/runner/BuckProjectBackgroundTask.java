package com.facebook.buck.intellijplugin.runner;

import com.facebook.buck.intellijplugin.buckprocess.BuckWatcher;
import com.facebook.buck.intellijplugin.tools.BuckToolWindow;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

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
  private final Project project;

  public BuckProjectBackgroundTask(Project project,
      String title, boolean canBeCancelled,
      PerformInBackgroundOption backgroundOption) {
    super(project, title, canBeCancelled, backgroundOption);
    this.project = project;
  }

  public BuckProjectBackgroundTask(Project project, String title,
      boolean canBeCancelled) {
    super(project, title, canBeCancelled);
    this.project = project;
  }

  public BuckProjectBackgroundTask(Project project, String title) {
    super(project, title);
    this.project = project;
  }

  public BuckProjectBackgroundTask(Project project) {
    super(project, DEFAULT_TITLE);
    this.project = project;
  }

  @Override
  public void run(ProgressIndicator progressIndicator) {
    try {
      Runtime runtime = Runtime.getRuntime();
      progressIndicator.setFraction((double)runtime.freeMemory() /
          (double)runtime.maxMemory());

      BuckRunParameters parameters = new BuckRunParameters(project);
      BaseDirectoryResolver baseResolver = BaseDirectoryResolver.fromProject(getProject());
      LOG.info("Running Buck Command: " + parameters.getFullCommand());
      Process process = runtime.exec(parameters.getFullCommand(),
          parameters.getEnvironment(), new File(baseResolver.resolve()));

      // Get a reference to the tool manager to access content
      ToolWindowManager windowManager = ToolWindowManager.getInstance(project);
      ToolWindow window = windowManager.getToolWindow(BuckToolWindow.ID);

      LOG.info("Starting buck waiter");
      BuckWatcher watcher = BuckWatcher.newBuilder()
          .setBuckCommand(parameters.getFullCommand())
          .setProgressIndicator(progressIndicator)
          .setInputStream(process.getErrorStream())
          .setToolWindow(window)
          .build();
      watcher.watch();

    } catch (IOException e) {
      progressIndicator.cancel();
      progressIndicator.setText("Buck Project Failed: " + e.getMessage());
      LOG.error("Error running buck project", e);
    }
    progressIndicator.setFraction(DONE_FRACTION);
  }
}
