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
import com.facebook.buck.intellijplugin.buckbuilder.BuckCompileOptions;
import org.jetbrains.jps.model.JpsElement;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;

import java.util.List;

/**
 * Jps buck project extensions bean.
 */
public interface JpsBuckProjectExtension extends JpsElement, BuckCompileOptions {

  public static final JpsElementChildRole<JpsBuckProjectExtension> ROLE =
      JpsElementChildRoleBase.create(BuckPlugin.BUCK_PLUGIN_ID);

  public void setTargetPath(String targetPath);

  public void setTargetNames(List<String> targetNames);
}
