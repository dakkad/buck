/*
 * Copyright 2013-present Facebook, Inc.
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
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.project.ProjectData;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.externalSystem.service.project.ExternalSystemProjectResolver;
import org.jetbrains.annotations.Nullable;

/**
 * Buck project resolver which identifies projects.
 */
public class BuckProjectResolver implements ExternalSystemProjectResolver<BuckExecutionSettings> {
  @Nullable
  @Override
  public DataNode<ProjectData> resolveProjectInfo(
      ExternalSystemTaskId externalSystemTaskId, String s, boolean b,
      BuckExecutionSettings s1,
      ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener)
      throws ExternalSystemException, IllegalArgumentException,
      IllegalStateException {
    // TODO(dka) Implement buck bindings for the project data nodes.
    return null;
  }

  @Override
  public boolean cancelTask(ExternalSystemTaskId externalSystemTaskId,
      ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener) {
    return false;
  }
}
