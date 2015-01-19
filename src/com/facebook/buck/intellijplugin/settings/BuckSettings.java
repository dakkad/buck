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

import com.intellij.openapi.externalSystem.settings.AbstractExternalSystemSettings;
import com.intellij.openapi.externalSystem.settings.ExternalSystemSettingsListener;
import com.intellij.openapi.project.Project;

/**
 * Global buck settings manager.
 *
 * @author code@damienallison.com
 */
// TODO(dka) Move to services
public class BuckSettings extends AbstractExternalSystemSettings<BuckSettings, BuckProjectSettings, BuckSettingsListener> {

  private final Project project;

  public BuckSettings(Project project) {
    super(BuckSettingsListener.TOPIC, project);
    this.project = project;
  }

  @Override
  public void subscribe(ExternalSystemSettingsListener externalSystemSettingsListener) {

  }

  @Override
  protected void copyExtraSettingsFrom(BuckSettings buckSettings) {

  }

  @Override
  protected void checkSettings(BuckProjectSettings oldSettings,
      BuckProjectSettings currentSettings) {

  }
}
