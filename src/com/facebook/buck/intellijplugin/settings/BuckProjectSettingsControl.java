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

package com.facebook.buck.intellijplugin.settings;

import com.facebook.buck.intellijplugin.components.BuckConfiguration;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.service.settings.AbstractExternalProjectSettingsControl;
import com.intellij.openapi.externalSystem.util.PaintAwarePanel;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.InvalidDataException;
import org.jdom.JDOMException;

import java.io.IOException;

/**
 * Buck project settings controls
 */
public class BuckProjectSettingsControl extends AbstractExternalProjectSettingsControl<BuckProjectSettings> {

  private static final Logger LOG = Logger.getInstance(BuckProjectSettingsControl.class);

  private BuckProjectSettings settings;
  private BuckCompilerForm form = BuckCompilerForm.newInstance();

  public BuckProjectSettingsControl(BuckProjectSettings settings) {
    super(settings);
    this.settings = settings;
  }

  @Override
  protected void fillExtraControls(PaintAwarePanel paintAwarePanel, int i) {
    paintAwarePanel.add(form.getComponent());
  }

  @Override
  protected boolean isExtraSettingModified() {
    String externalProject = getInitialSettings().getExternalProjectPath();
    String targetNames = "";
    try {
      Project project = ProjectManager.getInstance()
          .loadAndOpenProject(externalProject);
      targetNames = BuckConfiguration.getTargetNames(project);
    } catch (IOException | JDOMException | InvalidDataException e) {
      LOG.error("Error getting project for external path: " + externalProject);
    }
    return form.isModified(targetNames);
  }

  @Override
  protected void resetExtraSettings(boolean b) {
    form.reset();
  }

  @Override
  protected void applyExtraSettings(BuckProjectSettings buckProjectSettings) {
    // TODO(dka) Implement save cycle
  }

  @Override
  public boolean validate(BuckProjectSettings buckProjectSettings)
      throws ConfigurationException {
    return false;
  }


}
