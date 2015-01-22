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
import com.facebook.buck.intellijplugin.buckbuilder.BuckSourceRootDescriptor;
import com.facebook.buck.intellijplugin.buckbuilder.BuckTargetGenerator;
import com.google.common.collect.Lists;
import com.intellij.openapi.util.io.FileUtil;
import gnu.trove.THashSet;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildRootIndex;
import org.jetbrains.jps.builders.BuildTarget;
import org.jetbrains.jps.builders.BuildTargetRegistry;
import org.jetbrains.jps.builders.TargetOutputIndex;
import org.jetbrains.jps.builders.java.JavaModuleBuildTargetType;
import org.jetbrains.jps.builders.storage.BuildDataPaths;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.ModuleBuildTarget;
import org.jetbrains.jps.indices.IgnoredFileIndex;
import org.jetbrains.jps.indices.ModuleExcludeIndex;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.JpsProject;
import org.jetbrains.jps.model.module.JpsModule;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Buck build target information representation.
 */
public class BuckBuildTarget extends BuildTarget<BuckSourceRootDescriptor> implements BuckCompileOptions {


  private final String path;
  private final List<String> targets;

  protected BuckBuildTarget(String path, List<String> targets) {
    super(BuckBuildTargetType.getInstance());
    this.path = path;
    this.targets = targets;
  }

  @NotNull
  @Override
  public String getTargetPath() {
    return path;
  }

  @NotNull
  @Override
  public List<String> getTargetNames() {
    return targets;
  }

  @Override
  public String getId() {
    return BuckPlugin.BUCK_PLUGIN_ID;
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return BuckPlugin.BUCK_TARGET_LABEL;
  }

  @Override
  public Collection<BuildTarget<?>> computeDependencies(
      BuildTargetRegistry buildTargetRegistry,
      TargetOutputIndex targetOutputIndex) {
    // TODO(dka) Review if need to specify dependency tree
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public List<BuckSourceRootDescriptor> computeRootDescriptors(
      JpsModel jpsModel, ModuleExcludeIndex moduleExcludeIndex,
      IgnoredFileIndex ignoredFileIndex, BuildDataPaths buildDataPaths) {

    BuckTargetGenerator generator = BuckTargetGenerator.newBuilder()
        .setProject(jpsModel.getProject())
        .setModel(jpsModel)
        .setModuleExcludeIndex(moduleExcludeIndex)
        .setIgnoredFileIndex(ignoredFileIndex)
        .setBuildDataPaths(buildDataPaths)
        .setBuildTarget(this)
        .build();

    for (JpsModule module : jpsModel.getProject().getModules()) {
      for (JavaModuleBuildTargetType javaBuildType : JavaModuleBuildTargetType.ALL_TYPES) {
        ModuleBuildTarget moduleBuildTarget = new ModuleBuildTarget(module, javaBuildType);
        // TODO(dka) Figure out where the filtering is going on
        generator.process(moduleBuildTarget);
      }
    }
    return Lists.newArrayList(generator.getBuckTargets());
  }

  @Nullable
  @Override
  public BuckSourceRootDescriptor findRootDescriptor(String s,
      BuildRootIndex buildRootIndex) {
    return null;
  }

  @NotNull
  @Override
  public Collection<File> getOutputRoots(CompileContext compileContext) {
    Set<File> files = new THashSet<File>(FileUtil.FILE_HASHING_STRATEGY);
    JpsProject project = compileContext.getProjectDescriptor().getProject();
    for (JpsModule module : project.getModules()) {
      for (JavaModuleBuildTargetType buildTargetType :
          JavaModuleBuildTargetType.ALL_TYPES) {
        ModuleBuildTarget moduleTarget = new ModuleBuildTarget(module, buildTargetType);
        files.addAll(moduleTarget.getOutputRoots(compileContext));
      }
    }
    return files;
  }

  @Override
  public boolean equals(Object other) {
    if (null == other || !(other instanceof BuckBuildTarget)) {
      return false;
    }
    BuckBuildTarget test = (BuckBuildTarget)other;
    return getTargetPath().equals(test.getTargetPath()) &&
        getTargetNames().equals(test.getTargetNames());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(27, 41)
        .append(path)
        .append(targets)
        .toHashCode();
  }
}
