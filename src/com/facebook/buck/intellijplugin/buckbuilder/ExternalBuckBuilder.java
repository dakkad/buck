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

package com.facebook.buck.intellijplugin.buckbuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildTargetType;
import org.jetbrains.jps.incremental.BuilderService;
import org.jetbrains.jps.incremental.ModuleLevelBuilder;
import org.jetbrains.jps.incremental.TargetBuilder;

import java.util.Collections;
import java.util.List;

/**
 * The external buck builder acts as an interface to pass source files from
 * the build target calculation to buck to build. This will happen with an extra
 * step in the case of buck as buck needs to figure out which targets to build
 * for a collection of input files.
 *
 * @author code@damienallison.com
 */
public class ExternalBuckBuilder extends BuilderService {

  @Override
  @NotNull
  public List<? extends BuildTargetType<?>> getTargetTypes() {
    return Collections.emptyList();
  }

  @Override
  @NotNull
  public List<? extends ModuleLevelBuilder> createModuleLevelBuilders() {
    return Collections.emptyList();
  }

  @Override
  @NotNull
  public List<? extends TargetBuilder<?, ?>> createBuilders() {
    return Collections.emptyList();
  }
}
