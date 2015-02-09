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

package com.facebook.buck.intellijplugin.jps.model;

import com.facebook.buck.intellijplugin.BuckPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElement;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;

/**
 * Jps buck module extension.
 */
public interface JpsBuckModuleExtension extends JpsElement {

  public static final JpsElementChildRole<JpsBuckModuleExtension> ROLE =
      JpsElementChildRoleBase.create(BuckPlugin.BUCK_PROJECT_LABEL);

  @NotNull
  String getTargetLocation();

  void setTargetLocation(@NotNull String targetLocation);

  @NotNull
  String getConfigLocation();

  void setConfigLocation(@NotNull String configLocation);
}
