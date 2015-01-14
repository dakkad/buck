package com.facebook.buck.intellijplugin.components;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.facebook.buck.intellijplugin.runner.BuckProjectBackgroundTask;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * Basic action to run a buck project
 */
public class BuckProjectAction extends AnAction {
  
  private static final String DEFAULT_PROJECT_NAMES = "";
  private static final String BUCK_PROJECT_NAMES = BuckPlugin.PLUGIN_NAME +
      ".BuckProjectNames";


  public void actionPerformed(AnActionEvent actionEvent) {
    Project project = actionEvent.getProject();

    // Get the project name to run
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    String projectNames = projectProperties.getValue(BUCK_PROJECT_NAMES, 
        DEFAULT_PROJECT_NAMES);
    if (DEFAULT_PROJECT_NAMES.equals(projectNames)) {
      Messages.showErrorDialog(project, "No Project Names Specified. " +
          "Please check your buck settings", "Buck Projects");
      //return;
    }

    // Get a reference to the tool manager to access content
    ToolWindowManager windowManager = ToolWindowManager.getInstance(project);
    ToolWindow window = windowManager.getToolWindow("BuckPlugin.ToolWindow");

    // https://theantlrguy.atlassian.net/wiki/display/~admin/Intellij+plugin+development+notes#Intellijplugindevelopmentnotes-GUIandthreads,backgroundtasks
    Task.Backgroundable task = new BuckProjectBackgroundTask(project);
    ProgressManager progressManager = ProgressManager.getInstance();
    progressManager.run(task);
  }
}
