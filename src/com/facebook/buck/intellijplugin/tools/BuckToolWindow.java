package com.facebook.buck.intellijplugin.tools;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentManager;

/**
 * Buck tool window is used to capture and interact with the buck project and
 * test output.
 *
 * @author code@damienallison.com
 */
public class BuckToolWindow implements ToolWindowFactory {

  public static String name = "Buck Project";

  @Override
  public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    // TODO(dka) 20140113 Create tool window content console
    toolWindow.setTitle("Buck Project");
    ContentManager manager = toolWindow.getContentManager();
  }
}
