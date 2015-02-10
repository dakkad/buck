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

import com.facebook.buck.intellijplugin.jps.model.BuckBuildTargetType;
import com.google.common.collect.Lists;
import com.intellij.compiler.impl.BuildTargetScopeProvider;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerFilter;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.api.CmdlineRemoteProto.Message.ControllerMessage.ParametersMessage.TargetTypeBuildScope;

import java.util.List;

/**
 * Buck build scope provider.
 */
public class BuckBuildTargetScopeProvider extends BuildTargetScopeProvider {

  private Logger LOG = Logger.getInstance(BuckBuildTargetScopeProvider.class);

  @NotNull
  @Override
  public List<TargetTypeBuildScope> getBuildTargetScopes(@NotNull CompileScope compileScope,
      @NotNull CompilerFilter compilerFilter, @NotNull Project project, boolean forceBuild) {
    LOG.info("Buck build target scope provider returning scope");

    List<TargetTypeBuildScope> result = Lists.newArrayList(TargetTypeBuildScope.newBuilder()
        .setAllTargets(true)
        .setForceBuild(forceBuild)
        //.addTargetId(BuckConfigurationComponent.getTargetNames(project))
        .setTypeId(BuckBuildTargetType.getInstance()
            .getTypeId())
        .build());
    return result;
  }
}
