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

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.ex.JpsElementBase;

import java.util.List;

/**
 * Jps Buck Project Extension bean used to store project configuration.
 */
public class JpsBuckProjectExtensionBean extends JpsElementBase<JpsBuckProjectExtensionBean>
    implements JpsBuckProjectExtension {

  private String targetPath;
  private List<String> targetNames;

  public JpsBuckProjectExtensionBean(String targetPath, List<String> targetNames) {
    this.targetPath = targetPath;
    this.targetNames = targetNames;
  }
  @Override
  public void setTargetPath(String targetPath) {
    this.targetPath = targetPath;
  }

  @Override
  public void setTargetNames(List<String> targetNames) {
    this.targetNames = targetNames;
  }

  @NotNull
  @Override
  public String getTargetPath() {
    return targetPath;
  }

  @NotNull
  @Override
  public List<String> getTargetNames() {
    return targetNames;
  }

  @NotNull
  @Override
  public JpsBuckProjectExtensionBean createCopy() {
    return new JpsBuckProjectExtensionBean(targetPath, Lists.newArrayList(targetNames));
  }

  @Override
  public void applyChanges(
      @NotNull JpsBuckProjectExtensionBean update) {
    setTargetPath(update.getTargetPath());
    setTargetNames(update.getTargetNames());
  }
}
