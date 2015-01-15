package com.facebook.buck.intellijplugin.components;

import com.facebook.buck.intellijplugin.runner.BuckProjectBackgroundTask;
import com.facebook.buck.intellijplugin.tools.BuckToolWindow;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;

/**
 * Basic action to run a buck project
 */
public class BuckProjectAction extends AnAction {
  
  private static final Logger LOG = Logger.getInstance(BuckProjectAction.class);

  public void actionPerformed(AnActionEvent actionEvent) {
    Project project = actionEvent.getProject();

    // Get the project name to run
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    String projectNames = projectProperties.getValue(BuckConfiguration.BUCK_PROJECT_NAMES,
        BuckConfiguration.DEFAULT_PROJECT_NAMES);
    if (BuckConfiguration.DEFAULT_PROJECT_NAMES.equals(projectNames)) {
      Messages.showErrorDialog(project, "No Project Names Specified. " +
          "Please check your buck settings", "Buck Projects");
      //return;
    }

    // Activate the tool window
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    final ToolWindow toolWindow = toolWindowManager.getToolWindow(BuckToolWindow.ID);
    toolWindow.activate(new Runnable() {
      @Override
      public void run() {
      }
    });

    // https://theantlrguy.atlassian.net/wiki/display/~admin/Intellij+plugin+development+notes#Intellijplugindevelopmentnotes-GUIandthreads,backgroundtasks
    Task.Backgroundable task = new BuckProjectBackgroundTask(project);
    ProgressManager progressManager = ProgressManager.getInstance();
    progressManager.run(task);

    WindowManager generalManager = WindowManager.getInstance();

  }
}
