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

package com.facebook.buck.intellijplugin.buckbuilder;

import com.google.common.collect.Sets;
import com.intellij.util.Processor;
import org.jetbrains.jps.builders.java.JavaSourceRootDescriptor;
import org.jetbrains.jps.builders.storage.BuildDataPaths;
import org.jetbrains.jps.incremental.ModuleBuildTarget;
import org.jetbrains.jps.indices.IgnoredFileIndex;
import org.jetbrains.jps.indices.ModuleExcludeIndex;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.JpsProject;

import java.util.List;
import java.util.Set;

/**
 * The buck target generator uses process build target interface to offer
 * filter / generator functionality to construct a list of targets.
 */
public class BuckTargetGenerator implements Processor<ModuleBuildTarget> {

  private JpsProject project;
  private JpsModel model;
  private ModuleExcludeIndex moduleExcludeIndex;
  private IgnoredFileIndex ignoredFileIndex;
  private BuildDataPaths buildDataPaths;
  private Set<BuckSourceRootDescriptor> result = Sets.newHashSet();
  private BuckBuildTarget buildTarget;

  public BuckTargetGenerator() { }

  @Override
  public boolean process(ModuleBuildTarget target) {
    List<JavaSourceRootDescriptor> descriptors =
        target.computeRootDescriptors(model, moduleExcludeIndex,
            ignoredFileIndex, buildDataPaths);
    for (JavaSourceRootDescriptor javaSourceRootDescriptor : descriptors) {
      result.add(new BuckSourceRootDescriptor(buildTarget, javaSourceRootDescriptor.getRootFile(),
              javaSourceRootDescriptor.isGenerated(), javaSourceRootDescriptor.getExcludedRoots()
          )
      );
    }
    return true;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Set<BuckSourceRootDescriptor> getBuckTargets() {
    return result;
  }

  public static class Builder {

    private BuckTargetGenerator generator = new BuckTargetGenerator();

    public Builder setProject(JpsProject project) {
      generator.project = project;
      return this;
    }

    public Builder setModel(JpsModel model) {
      generator.model = model;
      return this;
    }

    public Builder setModuleExcludeIndex(ModuleExcludeIndex moduleExcludeIndex) {
      generator.moduleExcludeIndex = moduleExcludeIndex;
      return this;
    }

    public Builder setIgnoredFileIndex(IgnoredFileIndex ignoredFileIndex) {
      generator.ignoredFileIndex = ignoredFileIndex;
      return this;
    }

    public Builder setBuildDataPaths(BuildDataPaths buildDataPaths) {
      generator.buildDataPaths = buildDataPaths;
      return this;
    }

    public Builder setBuildTarget(BuckBuildTarget buildTarget) {
      generator.buildTarget = buildTarget;
      return this;
    }

    public BuckTargetGenerator build() {
      return generator;
    }
  }
}
