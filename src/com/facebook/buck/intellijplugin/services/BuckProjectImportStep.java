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

import com.facebook.buck.intellijplugin.components.BuckConfiguration;
import com.facebook.buck.intellijplugin.settings.BuckCompilerForm;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.ProjectManager;

import javax.swing.JComponent;

/**
 * The buck project step adds configuration options for a buck project
 * configuration setup.
 */
public class BuckProjectImportStep extends ModuleWizardStep {

  private static final Logger LOG = Logger.getInstance(BuckProjectImportStep.class);

  private final WizardContext context;
  private BuckCompilerForm form = BuckCompilerForm.newInstance();

  public BuckProjectImportStep(WizardContext context) {
    super();
    this.context = context;
  }

  @Override
  public JComponent getComponent() {
    LOG.info("Creating Buck Import Display Components");
    form.setText(BuckConfiguration.getTargetNames(
        ProjectManager.getInstance().getDefaultProject()));
    return form.getComponent();
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return form.getComponent();
  }

  @Override
  public void updateDataModel() {
    BuckConfiguration.setProjectNames(context.getProject(), form.getText());
  }

  @Override
  public void onStepLeaving() {
    LOG.info("Completing wizard step");
  }

  @Override
  public boolean isStepVisible() {
    return true;
  }

  @Override
  public boolean validate() {
    return true;
  }
}
