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

package com.facebook.buck.intellijplugin.services;

import com.facebook.buck.intellijplugin.settings.BuckExecutionSettings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.externalSystem.task.AbstractExternalSystemTaskManager;
import com.intellij.openapi.externalSystem.task.ExternalSystemTaskManager;

import java.util.List;

/**
 * Buck task manager handles tasks.
 */
public class BuckTaskManager extends AbstractExternalSystemTaskManager<BuckExecutionSettings>
    implements ExternalSystemTaskManager<BuckExecutionSettings> {

  private static final Logger LOG = Logger.getInstance(BuckTaskManager.class);

  @Override
  public void executeTasks(ExternalSystemTaskId externalSystemTaskId,
      List<String> taskNames, String projectPath, BuckExecutionSettings settings,
      List<String> vmOptions, List<String> scriptParameters, String debugSetup,
      ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener)
      throws ExternalSystemException {
    //throw new UnsupportedOperationException("Task running support not implemented");
    LOG.info("Skipping external task");
  }

  @Override
  public boolean cancelTask(ExternalSystemTaskId externalSystemTaskId,
      ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener)
      throws ExternalSystemException {
    //throw new UnsupportedOperationException("Task cancellation not implemented");
    return false;
  }
}
