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

import com.intellij.openapi.util.io.FileUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildRootDescriptor;
import org.jetbrains.jps.builders.BuildTarget;

import java.io.File;
import java.util.Set;

/**
 * Source root description extension.
 */
public class BuckSourceRootDescriptor extends BuildRootDescriptor {

  private final BuckBuildTarget target;
  private final File sourceRoot;
  private final boolean isGeneratedRoot;
  private final Set<File> excludedRoots;

  public BuckSourceRootDescriptor(BuckBuildTarget target, File sourceRoot,
      boolean isGeneratedRoot, Set<File> excludedRoots) {
    this.target = target;
    this.sourceRoot = sourceRoot;
    this.isGeneratedRoot = isGeneratedRoot;
    this.excludedRoots = excludedRoots;
  }

  @Override
  public String getRootId() {
    return FileUtil.toSystemDependentName(sourceRoot.getPath());
  }

  @Override
  public File getRootFile() {
    return sourceRoot;
  }

  @Override
  public BuildTarget<?> getTarget() {
    return target;
  }

  @Override
  public boolean isGenerated() {
    return isGeneratedRoot;
  }

  @Override
  public boolean canUseFileCache() {
    return true;
  }

  @NotNull
  @Override
  public Set<File> getExcludedRoots() {
    return excludedRoots;
  }

  /**
   * Override equals.
   *
   * @param other to compare
   * @return deep comparison of this and other. True if equal
   */
  @Override
  public boolean equals(Object other) {
    if (null == other || !(other instanceof BuckSourceRootDescriptor)) {
      return false;
    }
    BuckSourceRootDescriptor test = (BuckSourceRootDescriptor)other;
    return getTarget().equals(test.getTarget()) &&
        FileUtil.filesEqual(getRootFile(), test.getRootFile()) &&
        isGenerated() == test.isGenerated() &&
        getExcludedRoots().equals(test.getExcludedRoots());

  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(37, 41)
        .append(FileUtil.fileHashCode(sourceRoot))
        .append(sourceRoot)
        .append(isGeneratedRoot)
        .append(excludedRoots)
        .toHashCode();
  }
}
