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

import com.facebook.buck.intellijplugin.settings.BuckExecutionSettings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.ProjectKeys;
import com.intellij.openapi.externalSystem.model.project.ContentRootData;
import com.intellij.openapi.externalSystem.model.project.ModuleData;
import com.intellij.openapi.externalSystem.model.project.ProjectData;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.externalSystem.service.project.ExternalSystemProjectResolver;
import com.intellij.openapi.module.ModuleTypeId;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Buck project resolver which identifies projects.
 */
public class BuckProjectResolver implements ExternalSystemProjectResolver<BuckExecutionSettings> {

  private static final String BUCK_WORKING_DIRECTORY = "/.idea/buck/";
  private static final Logger LOG = Logger.getInstance(BuckProjectResolver.class);

  @Nullable
  @Override
  public DataNode<ProjectData> resolveProjectInfo(
      ExternalSystemTaskId externalSystemTaskId, String projectPath,
      boolean isPreview,
      BuckExecutionSettings executionSettings,
      ExternalSystemTaskNotificationListener notificationListener)
      throws ExternalSystemException, IllegalArgumentException,
      IllegalStateException {

    LOG.info(
        "Attempting to resolve external project information for " + projectPath);

    ProjectData projectData = new ProjectData(
        BuckPlugin.PROJECT_SYSTEM_ID,
        getProjectName(projectPath),
        projectPath + BUCK_WORKING_DIRECTORY,
        projectPath);

    DataNode<ProjectData> projectDataNode = new DataNode<ProjectData>(
        ProjectKeys.PROJECT, projectData, null);


    ModuleData moduleData = new ModuleData(
        getModuleName(projectPath),
        BuckPlugin.PROJECT_SYSTEM_ID,
        ModuleTypeId.JAVA_MODULE,
        getModuleName(projectPath),
        getModulePath(projectPath),
        projectPath);
    DataNode<ModuleData> moduleDataNode =
        projectDataNode.createChild(ProjectKeys.MODULE, moduleData);
    ContentRootData rootData = new ContentRootData(BuckPlugin.PROJECT_SYSTEM_ID,
        "src/");
    moduleDataNode.createChild(ProjectKeys.CONTENT_ROOT, rootData);
    return projectDataNode;
  }

  private String getModulePath(String projectPath) {
    return projectPath + "/buck";
  }

  private String getModuleName(String projectPath) {
    return "buck";
  }


  private String getProjectName(String projectPath) {
    return PathUtil.getParentPath(projectPath);
  }

  @Override
  public boolean cancelTask(ExternalSystemTaskId externalSystemTaskId,
      ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener) {
    return false;
  }
}
