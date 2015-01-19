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

package com.facebook.buck.intellijplugin.settings;

import com.intellij.openapi.externalSystem.util.ExternalSystemSettingsControl;
import com.intellij.openapi.externalSystem.util.PaintAwarePanel;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.InvalidDataException;
import org.jdom.JDOMException;

import java.io.IOException;
import javax.swing.JLabel;

/**
 * Buck project settings controls
 */
public class BuckProjectSettingsControl implements ExternalSystemSettingsControl<BuckProjectSettings> {

  private BuckProjectSettings settings;

  public BuckProjectSettingsControl(BuckProjectSettings settings) {
    this.settings = settings;
  }

  @Override
  public void fillUi(PaintAwarePanel paintAwarePanel, int i) {
    paintAwarePanel.add(new JLabel("PantsProjectSettingsControl"));
  }

  @Override
  public void reset() {

  }

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public void apply(BuckProjectSettings buckProjectSettings) {
    // TODO(dka) Implement the settings listener to store config
  }

  @Override
  public boolean validate(BuckProjectSettings buckProjectSettings)
      throws ConfigurationException {
    return false;
  }

  @Override
  public void disposeUIResources() {

  }

  @Override
  public void showUi(boolean b) {

  }

  public void resetProjectPath(String projectPath)
      throws ConfigurationException {
    try {
      settings = new BuckProjectSettings(ProjectManager.getInstance()
      .loadAndOpenProject(projectPath));
    } catch (IOException | JDOMException | InvalidDataException e) {
      throw new ConfigurationException("Failed to load project: " + e.getMessage());
    }
  }
}
