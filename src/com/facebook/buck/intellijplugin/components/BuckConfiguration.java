package com.facebook.buck.intellijplugin.components;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Buck configuration management.
 *
 * @author code@damienallison.com
 */
public class BuckConfiguration implements Configurable, ProjectComponent {

  private final Project project;

  public BuckConfiguration(Project project) {
    this.project = project;
  }

  public void initComponent() {
    // TODO: insert component initialization logic here
  }

  public void disposeComponent() {
    // TODO: insert component disposal logic here
  }

  @NotNull
  public String getComponentName() {
    return BuckPlugin.CONFIGURATION_NAME;
  }

  public void projectOpened() {
    // called when project is opened
  }

  public void projectClosed() {
    // called when project is being closed
  }

  @Nls
  @Override
  public String getDisplayName() {
    return BuckPlugin.CONFIGURATION_NAME;
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    // create a layout for the project properties
    // Set up the properties content
    return null;
  }

  /**
   * Called regularly to check if the content of the contents of the form have
   * been changed.
   *
   * @return if the contents have been changed.
   */
  @Override
  public boolean isModified() {
    // Get the project properties
    // Get the current value of the project property for the project to run name
    // return true if they are different
    return true;
  }

  @Override
  public void apply() throws ConfigurationException {
    // Get the basic project properties
    // Write the contents of the form into the project properties
  }

  @Override
  public void reset() {

  }

  @Override
  public void disposeUIResources() {

  }
}
