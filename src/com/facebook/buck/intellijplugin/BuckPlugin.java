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
package com.facebook.buck.intellijplugin;

import com.intellij.openapi.externalSystem.model.ProjectSystemId;

/**
 * Buck Plugin provides some name space for different attributes of the buck
 * IntelliJ Plugin.
 *
 * @author code@damienallison.com
 */
public class BuckPlugin {

  public static final String PLUGIN_NAME = "BuckPlugin";
  public static final String CONFIGURATION_NAME = "Buck Configuration";
  public static final String BUCK_PROJECT_NAME = "Buck Plugin";
  public static final String BUCK_PLUGIN_ID = "BuckPlugin";
  public static final ProjectSystemId PROJECT_SYSTEM_ID = new ProjectSystemId(
      BUCK_PLUGIN_ID);

  private BuckPlugin() { }

}
