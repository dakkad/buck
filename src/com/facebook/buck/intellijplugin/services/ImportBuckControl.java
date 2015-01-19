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

package com.facebook.buck.intellijplugin.services;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.externalSystem.service.project.manage.ProjectDataManager;
import com.intellij.openapi.externalSystem.service.settings.AbstractImportFromExternalSystemControl;
import com.intellij.openapi.externalSystem.settings.AbstractExternalSystemSettings;
import com.intellij.openapi.externalSystem.settings.ExternalProjectSettings;
import com.intellij.openapi.externalSystem.util.ExternalSystemSettingsControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Import buck control class.
 *
 * @author code@damienallison.com
 */
public class ImportBuckControl extends AbstractImportFromExternalSystemControl {

  public ImportBuckControl(ProjectDataManager dataManager) {
    ImportBuckControl control = new ImportBuckControl(dataManager);
    super(BuckPlugin.PROJECT_SYSTEM_ID, dataManager., BuckPlugin.SYSTEM_ID);
  }

  @Override
  protected void onLinkedProjectPathChange(String projectPath) {

  }

  @NotNull
  @Override
  protected ExternalSystemSettingsControl createProjectSettingsControl(
      ExternalProjectSettings projectSettings) {
    return null;
  }

  @Nullable
  @Override
  protected ExternalSystemSettingsControl createSystemSettingsControl(
      @NotNull AbstractExternalSystemSettings systemSettings) {
    return null;
  }
}
