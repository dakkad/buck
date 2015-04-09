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

package com.facebook.buck.intellijplugin.jps.incremental;

import com.facebook.buck.intellijplugin.jps.model.BuckBuildTarget;
import com.facebook.buck.intellijplugin.jps.model.BuckBuildTargetType;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildTargetType;
import org.jetbrains.jps.incremental.BuilderService;
import org.jetbrains.jps.incremental.TargetBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Buck builder provides a java service which provides the main entry point for
 * the intellij plugin to access buck functions.
 */
public class BuckBuilderService extends BuilderService {

  private static final Logger LOG = Logger.getInstance(BuckBuilderService.class);

  @NotNull
  @Override
  public List<? extends BuildTargetType<?>> getTargetTypes() {
    BuildTargetType<BuckBuildTarget> type = BuckBuildTargetType.getInstance();
    LOG.info("Including buck build target types: " + type.getTypeId());
    return Collections.singletonList(type);
  }

  @NotNull
  @Override
  public List<? extends TargetBuilder<?, ?>> createBuilders() {
    LOG.info("Creating builder for buck targets.");
    return Collections.singletonList(new BuckTargetBuilder());
  }
}