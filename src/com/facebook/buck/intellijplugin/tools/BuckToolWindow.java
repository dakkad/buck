/*
 * Copyright 2013-present Facebook, Inc.
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

package com.facebook.buck.intellijplugin.tools;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import javax.swing.JTextArea;

/**
 * Buck tool window is used to capture and interact with the buck project and
 * test output.
 */
public class BuckToolWindow implements ToolWindowFactory {

  public static final String ID = "Buck Project";
  private static JTextArea toolText = new JTextArea();

  @Override
  public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    // TODO(dka) 20140113 Create tool window content console
    toolWindow.setTitle("Buck Project");
    toolText.setEditable(false);
    toolWindow.getComponent()
        .add(toolText);
  }

  public static JTextArea resolveTextPane(ToolWindow toolWindow) {
    /*for (Component component : toolWindow.getComponent().getComponents()) {
      if (component instanceof JTextArea) {
        return (JTextArea) component;
      }
    }
    throw new IllegalStateException("Can't locate text text area");*/
    return toolText;
  }
}
