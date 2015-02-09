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

package com.facebook.buck.intellijplugin.settings;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

/**
 * Project compiler configurable settings.
 */
public class BuckProjectCompilerConfigurable extends BaseConfigurable
    implements SearchableConfigurable {

  private Project project;
  private BuckCompilerForm form = new BuckCompilerForm();

  public BuckProjectCompilerConfigurable(Project project) {
    this.project = project;
  }

  @NotNull
  @Override
  public String getId() {
    return BuckPlugin.BUCK_PROJECT_LABEL;
  }

  @Nullable
  @Override
  public Runnable enableSearch(String option) {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return BuckPlugin.BUCK_PROJECT_NAME;
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return form.getMainPanel();
  }

  @Override
  public boolean isModified() {
    return BuckSettings.getInstance(project).getCompileWithBuck() !=
        form.isBuckCompile();
  }

  @Override
  public void apply() throws ConfigurationException {
    BuckSettings settings = BuckSettings.getInstance(project);
    settings.setCompileWithBuck(form.isBuckCompile());
    // consider triggering rebuild
  }

  @Override
  public void reset() {
    BuckSettings settings = BuckSettings.getInstance(project);
    form.setBuckCompile(settings.getCompileWithBuck());
  }

  @Override
  public void disposeUIResources() {

  }
}
