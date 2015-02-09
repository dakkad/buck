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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.ex.JpsElementBase;

/**
 * JPS buck module extension default implementation.
 */
public class JpsDefaultBuckModuleExtension extends JpsElementBase<JpsDefaultBuckModuleExtension>
    implements JpsBuckModuleExtension {

  private String configLocation;
  private String targetLocation;

  public JpsDefaultBuckModuleExtension(String configLocation, String targetLocation) {
    this.configLocation = configLocation;
    this.targetLocation = targetLocation;
  }

  @NotNull
  @Override
  public String getTargetLocation() {
    return targetLocation;
  }

  @Override
  public void setTargetLocation(@NotNull String targetLocation) {
    this.targetLocation = targetLocation;
  }

  @NotNull
  @Override
  public String getConfigLocation() {
    return configLocation;
  }

  @Override
  public void setConfigLocation(@NotNull String configLocation) {
    this.configLocation = configLocation;
  }

  @NotNull
  @Override
  public JpsDefaultBuckModuleExtension createCopy() {
    return new JpsDefaultBuckModuleExtension(configLocation, targetLocation);
  }

  @Override
  public void applyChanges(@NotNull JpsDefaultBuckModuleExtension scope) {
    scope.setConfigLocation(configLocation);
    scope.setTargetLocation(targetLocation);
  }
}
