package com.facebook.buck.java.intellij.plugin.components;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by dak on 12/01/15.
 */
public class HelloAlert extends AnAction {

  public HelloAlert() {
    // set the menu item name
    super("Say _Hello");
  }

  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    Project project = anActionEvent.getProject();
    String text = Messages.showInputDialog(project.getName(), "What is your name good sir?",
        Messages.getQuestionIcon());
    Messages.showMessageDialog(project.getName(), "Hi " + text,Messages.getInformationIcon());
  }
}
