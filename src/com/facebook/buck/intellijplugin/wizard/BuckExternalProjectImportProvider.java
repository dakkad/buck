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

package com.facebook.buck.intellijplugin.wizard;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.facebook.buck.intellijplugin.runner.BaseDirectoryResolver;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.externalSystem.service.project.wizard.AbstractExternalProjectImportProvider;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectImportBuilder;
import com.intellij.util.ArrayUtil;

/**
 * Project import provider which takes the buck project config and pushes it
 * through the terminal 'buck project' invocation.
 */
public class BuckExternalProjectImportProvider extends AbstractExternalProjectImportProvider {

  private final ProjectImportBuilder builder;


  public BuckExternalProjectImportProvider(
      BuckExternalProjectImportBuilder builder) {
    super(builder, BuckPlugin.PROJECT_SYSTEM_ID);
    this.builder = builder;
  }

  @Override
  protected boolean canImportFromFile(VirtualFile file) {
    return BaseDirectoryResolver.isBuckConfig(file);
  }

  @Override
  public String getFileSample() {
    return "<b>Buck</b> config file (.buckconfig)";
  }

  @Override
  public ModuleWizardStep[] createSteps(WizardContext context) {
    // TODO(dka) Consider adding project JDK step where buck takes over as jdk / compiler
    return ArrayUtil.append(super.createSteps(context), new BuckProjectImportStep(context));
  }
}
