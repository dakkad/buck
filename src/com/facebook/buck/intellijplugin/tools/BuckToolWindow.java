package com.facebook.buck.intellijplugin.tools;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Buck tool window is used to capture and interact with the buck project and
 * test output.
 *
 * @author code@damienallison.com
 */
public class BuckToolWindow implements ToolWindowFactory {

  public static final String ID = "Buck Project";

  @Override
  public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    // TODO(dka) 20140113 Create tool window content console
    toolWindow.setTitle("Buck Project");

    JTextArea toolText = new JTextArea();
    toolText.setEditable(false);
    toolWindow.getComponent()
        .add(toolText);
  }

  public static JTextArea resolveTextPane(ToolWindow toolWindow) {
    for (Component component : toolWindow.getComponent().getComponents()) {
      if (component instanceof JTextArea) {
        return (JTextArea) component;
      }
    }
    throw new IllegalStateException("Can't locate text text area");
  }
}
