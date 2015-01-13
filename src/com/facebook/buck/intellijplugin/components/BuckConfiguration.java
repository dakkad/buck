package com.facebook.buck.intellijplugin.components;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dak on 13/01/15.
 */
public class BuckConfiguration implements ProjectComponent {
  public BuckConfiguration(Project project) {
  }

  public void initComponent() {
    // TODO: insert component initialization logic here
  }

  public void disposeComponent() {
    // TODO: insert component disposal logic here
  }

  @NotNull
  public String getComponentName() {
    return "BuckConfiguration";
  }

  public void projectOpened() {
    // called when project is opened
  }

  public void projectClosed() {
    // called when project is being closed
  }
}
