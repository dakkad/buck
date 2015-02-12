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
import com.google.common.collect.Lists;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildTargetLoader;
import org.jetbrains.jps.builders.BuildTargetType;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.JpsProject;

import java.util.List;

/**
 * Buck build target type information.
 */
public class BuckBuildTargetType extends BuildTargetType<BuckBuildTarget> {

  private static final Logger LOG = Logger.getInstance(BuckBuildTargetType.class);

  private BuckBuildTargetType() {
    super(BuckPlugin.BUCK_TARGET_COMPILE);
  }

  public static BuckBuildTargetType getInstance() {
    return new BuckBuildTargetType();
  }

  @NotNull
  @Override
  public List<BuckBuildTarget> computeAllTargets(@NotNull JpsModel jpsModel) {
    List<BuckBuildTarget> buildTargets = Lists.newArrayList();
    BuckBuildTarget target = getTarget(jpsModel);
    if (null != target) {
      buildTargets.add(target);
    }
    return buildTargets;
  }

  private BuckBuildTarget getTarget(JpsModel jpsModel) {
    JpsProject project = jpsModel.getProject();
    JpsBuckProjectExtension extension = JpsBuckProjectExtensionSerializer.findProjectExtension(
        project);
    LOG.info("Loaded buck project extensions? " + (null != extension));
    // TODO(dka) Figure out if this is a buck compile project
    boolean buckCompile = null != extension && extension.getBuildWithBuck();
    // TODO (dka) If not a buck compile don't return a buck target

    if (buckCompile) {
      LOG.info("Returning buck build target as is a buck project");
      return new BuckBuildTarget(extension.getTargetPath(), extension.getTargetNames());
    } else {
      LOG.info("Returning vanilla buck build target as no buck project extension");
      return new BuckBuildTarget("/home/dak/dev/hello/src/src/com/facebook/buck",
          Lists.newArrayList("hello"));
    }
  }

  @NotNull
  @Override
  public BuildTargetLoader<BuckBuildTarget> createLoader(
      @NotNull final JpsModel jpsModel) {
    return new BuildTargetLoader<BuckBuildTarget>() {
      @Nullable
      @Override
      public BuckBuildTarget createTarget(@NotNull String targetId) {
        // TODO(dka) Consider checking target id
        return getTarget(jpsModel);
      }
    };
  }
}
