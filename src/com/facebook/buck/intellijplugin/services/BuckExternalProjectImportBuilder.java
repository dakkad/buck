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

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.project.ProjectData;
import com.intellij.openapi.externalSystem.service.project.manage.ProjectDataManager;
import com.intellij.openapi.externalSystem.service.project.wizard.AbstractExternalProjectImportBuilder;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import javax.swing.Icon;

/**
 * Buck project import builder.
 */
public class BuckExternalProjectImportBuilder extends AbstractExternalProjectImportBuilder<ImportBuckControl> {

  private final ProjectDataManager projectDataManager;

  public BuckExternalProjectImportBuilder(@NotNull ProjectDataManager projectDataManager) {
    super(projectDataManager, new ImportBuckControl(), BuckPlugin.PROJECT_SYSTEM_ID);
    this.projectDataManager = projectDataManager;
  }

  @Override
  protected void doPrepare(WizardContext wizardContext) {
    String path = wizardContext.getProjectFileDirectory();
    getControl(wizardContext.getProject()).setLinkedProjectPath(path);
  }

  @Override
  protected void beforeCommit(DataNode<ProjectData> dataNode, Project project) {

  }

  @NotNull
  @Override
  protected File getExternalProjectConfigToUse(File file) {
    return file;
  }

  @Override
  protected void applyExtraSettings(@NotNull WizardContext wizardContext) {
  }

  @NotNull
  @Override
  public String getName() {
    return BuckPlugin.BUCK_PROJECT_LABEL;
  }

  @Override
  public Icon getIcon() {
    return BuckPlugin.ICON;
  }
}
