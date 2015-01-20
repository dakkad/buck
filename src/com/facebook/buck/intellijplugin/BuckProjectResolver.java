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
import com.facebook.buck.intellijplugin.settings.BuckExecutionSettings;
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.ProjectKeys;
import com.intellij.openapi.externalSystem.model.project.ProjectData;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.externalSystem.service.project.ExternalSystemProjectResolver;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jdom.JDOMException;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Buck project resolver which identifies projects.
 */
public class BuckProjectResolver implements ExternalSystemProjectResolver<BuckExecutionSettings> {

  private static final String BUCK_WORKING_DIRECTORY = "/.idea/buck/";

  @Nullable
  @Override
  public DataNode<ProjectData> resolveProjectInfo(
      ExternalSystemTaskId externalSystemTaskId, String projectPath, boolean isPreview,
      BuckExecutionSettings executionSettings,
      ExternalSystemTaskNotificationListener notificationListener)
      throws ExternalSystemException, IllegalArgumentException,
      IllegalStateException {

    VirtualFile root = VirtualFileManager.getInstance()
        .findFileByUrl(VfsUtil.pathToUrl(projectPath));

    if (!BaseDirectoryResolver.hasIntellijProject(root)) {
      // run some init
      runBuckProject();
    }

    ProjectData projectData = new ProjectData(
        BuckPlugin.PROJECT_SYSTEM_ID,
        root.getName(),
        root.getPath() + BUCK_WORKING_DIRECTORY + projectPath,
        projectPath);

    // Load the project from the working directory
    Project project = ProjectManager.getInstance()
        .getDefaultProject();

    try {
      project = ProjectManager.getInstance()
          .loadAndOpenProject(projectPath);
    } catch (JDOMException | InvalidDataException | IOException e) {
      throw new ExternalSystemException("Failed to load prior buck project", e);
    }


    // inspect the project DataNode<ProjectData>

    // inspect the module DataNode<ModuleData>

    DataNode<ProjectData> projectDataNode = new DataNode<ProjectData>(
        ProjectKeys.PROJECT, projectData, null);
    return projectDataNode;
  }

  private void runBuckProject() {
    // TODO(dka) Do some work!
  }

  @Override
  public boolean cancelTask(ExternalSystemTaskId externalSystemTaskId,
      ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener) {
    return false;
  }
}
