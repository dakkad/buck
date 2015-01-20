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

package com.facebook.buck.intellijplugin;

import com.facebook.buck.intellijplugin.runner.BaseDirectoryResolver;
import com.facebook.buck.intellijplugin.services.BuckTaskManager;
import com.facebook.buck.intellijplugin.settings.BuckConfigurable;
import com.facebook.buck.intellijplugin.settings.BuckExecutionSettings;
import com.facebook.buck.intellijplugin.settings.BuckLocalSettings;
import com.facebook.buck.intellijplugin.settings.BuckProjectSettings;
import com.facebook.buck.intellijplugin.settings.BuckSettings;
import com.facebook.buck.intellijplugin.settings.BuckSettingsListener;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.SimpleJavaParameters;
import com.intellij.ide.actions.OpenProjectFileChooserDescriptor;
import com.intellij.openapi.externalSystem.ExternalSystemConfigurableAware;
import com.intellij.openapi.externalSystem.ExternalSystemManager;
import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.externalSystem.service.project.ExternalSystemProjectResolver;
import com.intellij.openapi.externalSystem.task.ExternalSystemTaskManager;
import com.intellij.openapi.externalSystem.util.ExternalSystemConstants;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;

/**
 * Buck manager helper.
 */
public class BuckManager implements
    ExternalSystemConfigurableAware,
    // ExternalSystemAutoImportAware,
    // ExternalSystemUiAware,
    // StartupActivity,
    ExternalSystemManager<BuckProjectSettings,
            BuckSettingsListener,
            BuckSettings,
            BuckLocalSettings,
            BuckExecutionSettings> {

  private static final FileChooserDescriptor BUILD_FILE_CHOOSER_DESCRIPTOR =
      new OpenProjectFileChooserDescriptor(true) {

        @Override
        public boolean isFileSelectable(VirtualFile file) {
          return BaseDirectoryResolver.isBuckConfig(file);
        }

        @Override
        public boolean isFileVisible(VirtualFile file, boolean showHiddenFiles) {
          return super.isFileVisible(file, showHiddenFiles);
        }
      };

  /*@Nullable
  @Override
  public String getAffectedExternalProjectPath(String changedPath, Project project) {
    VirtualFile file = project.getBaseDir().findFileByRelativePath(changedPath);
    return null == file? null : file.getCanonicalPath();
  }*/

  @NotNull
  @Override
  public Configurable getConfigurable(Project project) {
    return new BuckConfigurable(project);
  }

  @NotNull
  @Override
  public ProjectSystemId getSystemId() {
    return BuckPlugin.PROJECT_SYSTEM_ID;
  }

  @NotNull
  @Override
  public Function<Project, BuckSettings> getSettingsProvider() {
    return new Function<Project, BuckSettings>() {
      @Override
      public BuckSettings fun(Project project) {
        return new BuckSettings(project);
      }
    };
  }

  @NotNull
  @Override
  public Function<Project, BuckLocalSettings> getLocalSettingsProvider() {
    return new Function<Project, BuckLocalSettings>() {
      @Override
      public BuckLocalSettings fun(Project project) {
        return BuckLocalSettings.getInstance(project);
      }
    };
  }

  @NotNull
  @Override
  public Function<Pair<Project, String>, BuckExecutionSettings>
      getExecutionSettingsProvider() {
    return new Function<Pair<Project, String>, BuckExecutionSettings>() {
      @Override
      public BuckExecutionSettings fun(Pair<Project, String> projectStringPair) {
        Project project = projectStringPair.getFirst();
        String path = projectStringPair.getSecond();
        BuckExecutionSettings settings = new BuckExecutionSettings(project, path);
        // TODO(dka) Look at the extensions that are needing registration
        return settings;
      }
    };
  }

  @NotNull
  @Override
  public Class<? extends ExternalSystemProjectResolver<BuckExecutionSettings>> getProjectResolverClass() {
    return BuckProjectResolver.class;
  }

  @Override
  public Class<? extends ExternalSystemTaskManager<BuckExecutionSettings>>
      getTaskManagerClass() {
    return BuckTaskManager.class;
  }

  @NotNull
  @Override
  public FileChooserDescriptor getExternalProjectDescriptor() {
    return BUILD_FILE_CHOOSER_DESCRIPTOR;
  }

  @Override
  public void enhanceRemoteProcessing(
      @NotNull SimpleJavaParameters simpleJavaParameters)
      throws ExecutionException {
    simpleJavaParameters.getVMParametersList().addProperty(
        ExternalSystemConstants.EXTERNAL_SYSTEM_ID_KEY,
        BuckPlugin.PROJECT_SYSTEM_ID.getId());
  }

  @Override
  public void enhanceLocalProcessing(@NotNull List<URL> list) {

  }

  /*@Override
  public void runActivity(@NotNull Project project) {

  }

  @NotNull
  @Override
  public String getProjectRepresentationName(@NotNull String projectPath,
      @Nullable String projectRoot) {
    return ExternalSystemApiUtil.getProjectRepresentationName(projectPath,
        projectRoot);
  }

  @Nullable
  @Override
  public FileChooserDescriptor getExternalProjectConfigDescriptor() {
    return BUILD_FILE_CHOOSER_DESCRIPTOR;
  }

  @Nullable
  @Override
  public Icon getProjectIcon() {
    return BuckPlugin.ICON;
  }

  @Nullable
  @Override
  public Icon getTaskIcon() {
    return BuckPlugin.FAVICON;
  } */
}
