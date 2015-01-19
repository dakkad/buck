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
package com.facebook.buck.intellijplugin.components;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.facebook.buck.intellijplugin.content.BuckPluginContent;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Buck configuration management using basic project properties component to
 * persist the project selection.
 *
 * @author code@damienallison.com
 */
public class BuckConfiguration extends BaseConfigurable implements SearchableConfigurable {

  private static final Logger LOG = Logger.getInstance(BuckConfiguration.class);
  public static final String BUCK_PROJECT_NAMES = BuckPlugin.PLUGIN_NAME +
      ".BuckProjectNames";
  public static final String DEFAULT_PROJECT_NAMES = "";
  private static final String TARGETS = "targets";
  private static final int INPUT_TEXT_SIZE = 32;
  private static final int PADDING = 5;
  private static final String PROJECT_PREFIX = "project ";

  private final Project project;

  // TODO(dka) Move to buck compiler form
  private String projectNames = DEFAULT_PROJECT_NAMES;
  private JTextField projectsInput;

  public BuckConfiguration(Project project) {
    this.project = project;
  }

  public static String getProjectNames(Project project) {
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    String result = PROJECT_PREFIX + projectProperties.getValue(BUCK_PROJECT_NAMES,
        DEFAULT_PROJECT_NAMES);
    if (DEFAULT_PROJECT_NAMES.equals(result)) {
      result = TARGETS;
    }
    return result;
  }

  public static void setProjectNames(Project project, String projectNames) {
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    projectProperties.setValue(BUCK_PROJECT_NAMES, projectNames);
  }

  @Override
  public String getId() {
    return BuckPlugin.BUCK_PLUGIN_ID;
  }

  @Nullable
  @Override
  public Runnable enableSearch(String s) {
    return null;
  }


  @Nls
  @Override
  public String getDisplayName() {
    return BuckPlugin.CONFIGURATION_NAME;
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    projectNames = getProjectNames(project);
    LOG.info("Loaded Buck Project Names " + projectNames);
    // https://confluence.jetbrains.com/display/IDEADEV/Customizing+the+IDEA+Settings+Dialog
    // create a layout for the project properties

    JLabel projectsLabel = new JLabel(BuckPluginContent.PROJECT_NAMES_LABEL);
    projectsInput = new JTextField(projectNames, INPUT_TEXT_SIZE);
    FlowLayout layout = new FlowLayout(FlowLayout.LEADING, PADDING, PADDING);
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
    String current = getProjectNames(project);
    return !current.equals(projectsInput.getText().trim());
  }

  @Override
  public void apply() throws ConfigurationException {
    // Get the basic project properties
    // Write the contents of the form into the project properties

    String text = projectsInput.getText();
    setProjectNames(project, text);
  }

  @Override
  public void reset() {
    projectsInput.setText(DEFAULT_PROJECT_NAMES);
  }

  @Override
  public void disposeUIResources() {
    // TODO(dka) 20150115 null components
  }
}
