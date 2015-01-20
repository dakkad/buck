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

package com.facebook.buck.intellijplugin.services;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.facebook.buck.intellijplugin.settings.BuckProjectSettings;
import com.facebook.buck.intellijplugin.settings.BuckProjectSettingsControl;
import com.facebook.buck.intellijplugin.settings.BuckSettings;
import com.facebook.buck.intellijplugin.settings.BuckSettingsListener;
import com.intellij.openapi.externalSystem.service.settings.AbstractImportFromExternalSystemControl;
import com.intellij.openapi.externalSystem.util.ExternalSystemSettingsControl;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Import buck control class.
 */
public class ImportBuckControl extends
    AbstractImportFromExternalSystemControl<
        BuckProjectSettings,
        BuckSettingsListener,
        BuckSettings> {

  public ImportBuckControl() {
    super(BuckPlugin.PROJECT_SYSTEM_ID,
        new BuckSettings(ProjectManager.getInstance()
            .getDefaultProject()), new BuckProjectSettings(
            ProjectManager.getInstance().getDefaultProject()),
            true);

  }

  private BuckProjectSettings getInitialProjectSettings() {
    return new BuckProjectSettings(ProjectManager.getInstance().getDefaultProject());
  }

  @Override
  protected void onLinkedProjectPathChange(String projectPath) {
//    if (null != projectPath && !projectPath.isEmpty()) {
//        ((BuckProjectSettingsControl)getProjectSettingsControl())
//            .onProjectPathChanged(projectPath);
//
//      // TODO(dka) handle project path change events (and load settings)
//    }
  }


  @Override
  protected ExternalSystemSettingsControl<BuckProjectSettings> createProjectSettingsControl(
      BuckProjectSettings settings) {
    return new BuckProjectSettingsControl(settings);
  }

  @Nullable
  @Override
  protected ExternalSystemSettingsControl<BuckSettings> createSystemSettingsControl(
      @NotNull BuckSettings systemSettings) {
    return null;
  }
}
