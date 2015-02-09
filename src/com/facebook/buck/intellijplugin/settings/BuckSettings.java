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

import com.facebook.buck.intellijplugin.runner.BaseDirectoryResolver;
import com.google.common.collect.Sets;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.openapi.externalSystem.settings.AbstractExternalSystemSettings;
import com.intellij.openapi.externalSystem.settings.ExternalSystemSettingsListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.annotations.AbstractCollection;

import java.io.Serializable;
import java.util.Set;

/**
 * Global buck settings manager.
 */
// TODO(dka) Move to services
@State(
    name = "BuckSettings",
    storages = {
        @Storage(file = StoragePathMacros.PROJECT_FILE),
        @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + BaseDirectoryResolver.BUCK_CONFIG_FILE,
            scheme = StorageScheme.DIRECTORY_BASED)
    }
)
public class BuckSettings extends AbstractExternalSystemSettings<BuckSettings, BuckProjectSettings, BuckSettingsListener>
    implements PersistentStateComponent<BuckSettings.BuckState> {


  private boolean compileWithBuck;

  public BuckSettings(Project project) {
    super(BuckSettingsListener.TOPIC, project);
  }

  public boolean getCompileWithBuck() {
    return compileWithBuck;
  }

  public void setCompileWithBuck(boolean compileWithBuck) {
    this.compileWithBuck = compileWithBuck;
  }

  @Override
  public void subscribe(ExternalSystemSettingsListener externalSystemSettingsListener) {

  }

  @Override
  protected void copyExtraSettingsFrom(BuckSettings buckSettings) {
    this.compileWithBuck = buckSettings.getCompileWithBuck();
  }

  @Override
  protected void checkSettings(BuckProjectSettings oldSettings,
      BuckProjectSettings currentSettings) {
  }

  public static BuckSettings getInstance(Project project) {
    return ServiceManager.getService(project, BuckSettings.class);
  }

  @Override
  public BuckState getState() {
    BuckState state = new BuckState();
    state.setCompileWithBuck(compileWithBuck);
    fillState(state);
    return state;
  }

  @Override
  public void loadState(BuckState state) {
    super.loadState(state);
    setCompileWithBuck(state.getCompileWithBuck());
  }

  public static class BuckState implements State<BuckProjectSettings>, Serializable {

    private Set<BuckProjectSettings> linkedExternalProjectSettings = Sets.newTreeSet();
    private boolean compileWithBuck;

    @Override
    @AbstractCollection(surroundWithTag = false, elementTypes = {BuckProjectSettings.class})
    public Set<BuckProjectSettings> getLinkedExternalProjectsSettings() {
      return linkedExternalProjectSettings;
    }

    @Override
    public void setLinkedExternalProjectsSettings(Set<BuckProjectSettings> set) {
      linkedExternalProjectSettings = set;
    }

    public void setCompileWithBuck(boolean compileWithBuck) {
      this.compileWithBuck = compileWithBuck;
    }

    public boolean getCompileWithBuck() {
      return compileWithBuck;
    }
  }
}
