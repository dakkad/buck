/*
 * Copyright 2015-present Facebook, Inc.
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
import com.facebook.buck.intellijplugin.settings.BuckCompilerForm;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

/**
 * Buck configuration management using basic project properties component to
 * persist the project selection.
 */
public class BuckConfigurationComponent extends BaseConfigurable implements SearchableConfigurable {

  private static final Logger LOG = Logger.getInstance(BuckConfigurationComponent.class);
  public static final String BUCK_PROJECT_NAMES = BuckPlugin.PLUGIN_NAME +
      ".BuckProjectNames";
  public static final String DEFAULT_PROJECT_NAMES = "";
  private static final String TARGETS = "targets";
  private static final int INPUT_TEXT_SIZE = 32;
  private static final String PROJECT_PREFIX = "project ";

  private final Project project;
  private BuckCompilerForm form = BuckCompilerForm.newInstance();


  public BuckConfigurationComponent(Project project) {
    this.project = project;
  }

  public static String getTargetNames(Project project) {
    PropertiesComponent projectProperties = PropertiesComponent.getInstance(project);
    String result = PROJECT_PREFIX + projectProperties.getValue(BUCK_PROJECT_NAMES,
        DEFAULT_PROJECT_NAMES);
    if (DEFAULT_PROJECT_NAMES.equals(result)) {
      result = TARGETS;
    }
    return result;
  }

  public static void setTargetNames(Project project, String projectNames) {
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
    return BuckPluginContent.CONFIGURATION_NAME;
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    String targets = getTargetNames(project);
    LOG.info("Loaded Buck Targets: " + targets);
    // https://confluence.jetbrains.com/display/IDEADEV/Customizing+the+IDEA+Settings+Dialog
    // create a layout for the project properties
    form.setText(targets);

    return form.getComponent();
  }

  /**
   * Called regularly to check if the content of the contents of the form have
   * been changed.
   *
   * @return if the contents have been changed.
   */
  @Override
  public boolean isModified() {
    String current = getTargetNames(project);
    return !current.equals(form.getText().trim());
  }

  @Override
  public void apply() throws ConfigurationException {
    // Get the basic project properties
    // Write the contents of the form into the project properties
    String text = form.getText();
    setTargetNames(project, text);
  }

  @Override
  public void reset() {
    form.setText(DEFAULT_PROJECT_NAMES);
  }

  @Override
  public void disposeUIResources() {
    // TODO(dka) 20150115 null components
  }
}
