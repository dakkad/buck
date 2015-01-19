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

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.openapi.externalSystem.service.project.PlatformFacade;
import com.intellij.openapi.externalSystem.settings.AbstractExternalSystemLocalSettings;
import com.intellij.openapi.project.Project;

/**
 * Local settings manager.
 *
 * @author code@damienallison.com
 */
// TODO(dka) Move to services
public class BuckLocalSettings extends AbstractExternalSystemLocalSettings {

  private final Project project;

  public BuckLocalSettings(Project project, PlatformFacade facade) {
    super(BuckPlugin.PROJECT_SYSTEM_ID, project, facade);
    this.project = project;
  }

  public static BuckLocalSettings getInstance(Project project) {
    return com.intellij.openapi.components.ServiceManager.getService(project,
        BuckLocalSettings.class);
  }
}
