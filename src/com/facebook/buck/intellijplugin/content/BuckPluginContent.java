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

package com.facebook.buck.intellijplugin.content;

/**
 * Simple content provider.
 *
 * @author code@damienallison.com
 */
public class BuckPluginContent {
  public static final String PROJECT_NAMES_LABEL = "Buck Targets";
  public static final String SETTINGS_LABEL = "Buck Settings";
  public static final String PLUGIN_NAME = "Buck";
  public static final String CONFIGURATION_NAME = "Buck Configuration";
  public static final String NO_TARGETS_WARNING = "No Project Names Specified. " +
      "Please check your buck settings";
  public static final String NO_TARGETS_TITLE = "Buck Projects";
  public static final String COMPILER_CHOICE_LABEL = "Choose Your Compiler";
  public static final String BUCK_COMPILER_LABEL = "Buck Compiler";
  public static final String INTELLIJ_COMPILER_LABEL = "IntelliJ Compiler";

  public static String ARGUMENTS_TITLE = "Buck Configuration / Targets";
}
