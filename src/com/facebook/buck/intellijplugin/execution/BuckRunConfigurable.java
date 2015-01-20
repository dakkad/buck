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

package com.facebook.buck.intellijplugin.execution;

import com.facebook.buck.intellijplugin.settings.BuckCompilerForm;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;

/**
 * Buck run configuration editor for options.
 */
public class BuckRunConfigurable extends SettingsEditor<BuckRunConfiguration> {

  private final Project project;
  private BuckCompilerForm form = BuckCompilerForm.newInstance();

  public BuckRunConfigurable(Project project) {
    this.project = project;
  }

  @Override
  protected void resetEditorFrom(BuckRunConfiguration buckRunConfiguration) {
    form.setText(buckRunConfiguration.getTarget());
  }

  @Override
  protected void applyEditorTo(BuckRunConfiguration buckRunConfiguration)
      throws ConfigurationException {
    buckRunConfiguration.setTarget(form.getText());
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    form.setText("Run Configuration Override");
    return form.getComponent();
  }
}
