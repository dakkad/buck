package com.facebook.buck.intellijplugin.components;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Buck configuration management using basic project properties component to
 * persist the project selection.
 *
 * @author code@damienallison.com
 */
public class BuckConfiguration implements Configurable {

  public static final String BUCK_PROJECT_NAMES = BuckPlugin.PLUGIN_NAME +
      ".BuckProjectNames";
  public static final String DEFAULT_PROJECT_NAMES = "";
  private static final Logger LOG = Logger.getInstance(BuckConfiguration.class);
  private static final String TARGETS = "targets";

  private Project project;

  private String projectNames = DEFAULT_PROJECT_NAMES;
  private JTextField projectsInput;

  public BuckConfiguration(Project project) {
    this.project = project;
  }

  public static String getProjectNames(Project project) {
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    String result = projectProperties.getValue(BUCK_PROJECT_NAMES,
        DEFAULT_PROJECT_NAMES);
    if (DEFAULT_PROJECT_NAMES.equals(result)) {
      result = TARGETS;
    }
    return result;
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

    projectNames = getProjectNames(project);
    LOG.info("Loaded Buck Project Names " + projectNames);
    // https://confluence.jetbrains.com/display/IDEADEV/Customizing+the+IDEA+Settings+Dialog
    // create a layout for the project properties

    JLabel projectsLabel = new JLabel("Buck Projects");
    projectsInput = new JTextField(projectNames, 32);

    FlowLayout layout = new FlowLayout(FlowLayout.LEADING, 5, 5);
    JPanel outer = new JPanel(layout);

    outer.setLayout(layout);

    outer.add(projectsLabel);
    outer.add(projectsInput);

    // Set up the properties content
    return outer;
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
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    String text = projectsInput.getText();
    projectProperties.setValue(BUCK_PROJECT_NAMES, text);
  }

  @Override
  public void reset() {
    // TODO (dka) Reset the project names
  }

  @Override
  public void disposeUIResources() {

  }
}
