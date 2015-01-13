package com.facebook.buck.intellijplugin.components;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by dak on 13/01/15.
 */
public class BuckProjectAction extends AnAction {
  public void actionPerformed(AnActionEvent actionEvent) {
    Project project = actionEvent.getProject();
    String text = Messages.showInputDialog(project.getName(),
        "What is your name good sir?",
        Messages.getQuestionIcon());
    Messages.showMessageDialog(project.getName(), "Hi " + text,Messages.getInformationIcon());
  }
}
