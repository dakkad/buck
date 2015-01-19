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

import com.intellij.openapi.externalSystem.model.settings.ExternalSystemExecutionSettings;
import com.intellij.openapi.project.Project;

/**
 * Buck execution settings provider.
 *
 * @author code@damienallison.com
 */
public class BuckExecutionSettings extends ExternalSystemExecutionSettings {

  private final Project project;
  private final String path;

  public BuckExecutionSettings(Project project, String path) {
    super();
    this.project = project;
    this.path = path;
  }
}
