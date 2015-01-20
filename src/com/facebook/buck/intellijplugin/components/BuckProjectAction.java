/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.intellijplugin.components;

import com.facebook.buck.intellijplugin.content.BuckPluginContent;
import com.facebook.buck.intellijplugin.runner.BuckProjectBackgroundTask;
import com.facebook.buck.intellijplugin.tools.BuckToolWindow;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * Simple action to run a buck project with project name taken from properties
 * configuration.
 *
 * @author code@damienallison.com
 */
public class BuckProjectAction extends AnAction {
  
  private static final Logger LOG = Logger.getInstance(BuckProjectAction.class);

  @Override
  public void actionPerformed(AnActionEvent actionEvent) {
    Project project = actionEvent.getProject();
    // Get the project name to run
    String projectNames = BuckConfiguration.getTargetNames(project);
    if (BuckConfiguration.DEFAULT_PROJECT_NAMES.equals(projectNames.trim())) {
      Messages.showErrorDialog(project, BuckPluginContent.NO_TARGETS_WARNING,
          BuckPluginContent.NO_TARGETS_TITLE);
      return;
    }

    // Activate the tool window
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    final ToolWindow toolWindow = toolWindowManager.getToolWindow(BuckToolWindow.ID);
    toolWindow.activate(NullAction.newInstance());

    // https://theantlrguy.atlassian.net/wiki/display/~admin/Intellij+plugin+development+notes#Intellijplugindevelopmentnotes-GUIandthreads,backgroundtasks
    Task.Backgroundable task = new BuckProjectBackgroundTask(project);
    ProgressManager progressManager = ProgressManager.getInstance();
    progressManager.run(task);
  }

  private static class NullAction implements Runnable {

    public static NullAction newInstance() {
      return new NullAction();
    }

    @Override
    public void run() {
    }
  }
}
