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

import com.facebook.buck.intellijplugin.buckbuilder.BuckBuildTarget;
import com.facebook.buck.intellijplugin.buckbuilder.BuckSourceRootDescriptor;
import com.facebook.buck.intellijplugin.jps.model.BuckBuildTargetType;
import com.facebook.buck.intellijplugin.jps.model.JpsBuckProjectExtension;
import com.facebook.buck.intellijplugin.jps.model.JpsBuckProjectExtensionSerializer;
import com.facebook.buck.intellijplugin.jps.wrapper.BuckBuildCommand;
import com.facebook.buck.intellijplugin.jps.wrapper.BuckCommand;
import com.facebook.buck.intellijplugin.jps.wrapper.BuckEventListener;
import com.facebook.buck.intellijplugin.jps.wrapper.BuckTarget;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildOutputConsumer;
import org.jetbrains.jps.builders.DirtyFilesHolder;
import org.jetbrains.jps.builders.java.JavaBuilderUtil;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.ProjectBuildException;
import org.jetbrains.jps.incremental.TargetBuilder;
import org.jetbrains.jps.incremental.java.JavaBuilder;
import org.jetbrains.jps.model.JpsProject;
import org.jetbrains.jps.model.module.JpsModule;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * The buck target builder which runs buck and streams output back to IntelliJ
 */
public class BuckTargetBuilder extends TargetBuilder<BuckSourceRootDescriptor, BuckBuildTarget> {

  private static Logger LOG = Logger.getInstance(BuckTargetBuilder.class);

  public BuckTargetBuilder() {
    super(Collections.singletonList(BuckBuildTargetType.getInstance()));
    LOG.info("Buck Target Builder Created");
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Buck Compiler";
  }

  @Override
  public void buildStarted(CompileContext compileContext) {
    super.buildStarted(compileContext);
    LOG.info("Build Started. Disabling java compiler");
    JpsProject project = compileContext.getProjectDescriptor().getProject();
    JpsBuckProjectExtension extension = JpsBuckProjectExtensionSerializer.find(project);
    // TODO(dka) Check whether we should use buck for compiling - for now yes
    if (projectContainsRelevantModules(project)) {
      JavaBuilder.IS_ENABLED.set(compileContext, false);
    }
  }

  private boolean projectContainsRelevantModules(JpsProject project) {
    for (JpsModule modules : project.getModules()) {
      if (null != JpsBuckProjectExtensionSerializer.find(project)) {
        return true;
      }
    }
    return false;
  }


  @Override
  public void build(BuckBuildTarget buckBuildTarget,
      DirtyFilesHolder<BuckSourceRootDescriptor, BuckBuildTarget> dirtyFilesHolder,
      BuildOutputConsumer buildOutputConsumer, final CompileContext compileContext)
      throws ProjectBuildException, IOException {

    LOG.info("Build called for target: " + buckBuildTarget.getId());

    File canonicalPath = new File(buckBuildTarget.getTargetPath());

    // Check whether this build should even be run
    if (!dirtyFilesHolder.hasDirtyFiles() &&
        !JavaBuilderUtil.isForcedRecompilationAllJavaModules(compileContext)) {
      compileContext.processMessage(BuckMessage.info("No changes to compile"));
      return;
    }

    // TODO(dka) Consider checking to make sure that buck is found

    // TODO(dka) Look at how we can use CapturingProcessHandler to run buck

    BuckEventListener eventListener = new BuckEventAdaptor(compileContext);
    BuckCommand command = new BuckCommand(buckBuildTarget, canonicalPath, eventListener);
    for (String target : buckBuildTarget.getTargetNames()) {
      BuckBuildCommand.build(command, new BuckTarget("java_library", target,
          buckBuildTarget.getTargetPath()));
    }
  }
}
