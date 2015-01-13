package com.facebook.buck.intellijplugin.components;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * External project configuration for telling buck which projects to run.
 *
 * The external configuration is used to configure separate and independent
 * systems so is an ideal place to configure Buck on a project basis to set up
 * the project list to build.
 *
 * @author code@damieallison.com
 */
public class ExternalBuckProjectConfig implements ProjectComponent {

  public ExternalBuckProjectConfig(Project project) {
  }

  public void initComponent() {
    // TODO: insert component initialization logic here
  }

  public void disposeComponent() {
    // TODO: insert component disposal logic here
  }

  @NotNull
  public String getComponentName() {
    return "ExternalBuckProjectConfig";
  }

  public void projectOpened() {
    // called when project is opened
  }

  public void projectClosed() {
    // called when project is being closed
  }
}
