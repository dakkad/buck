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

import com.intellij.openapi.externalSystem.settings.ExternalProjectSettings;
import com.intellij.util.containers.ContainerUtilRt;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Buck project settings which provides an adaptor to the components settings.
 */
public class BuckProjectSettings extends ExternalProjectSettings {

  private List<String> target = ContainerUtilRt.emptyList();

  @NotNull
  @Override
  public BuckProjectSettings clone() {
    BuckProjectSettings clone = new BuckProjectSettings();
    copyTo(clone);
    return clone;
  }

  @Override
  public void copyTo(ExternalProjectSettings receiver) {
    super.copyTo(receiver);
    if (receiver instanceof BuckProjectSettings) {
      BuckProjectSettings buckReceiver = (BuckProjectSettings) receiver;
      buckReceiver.setTarget(getTarget());
    }
  }

  public List<String> getTarget() {
    return target;
  }

  public void setTarget(List<String> target) {
    this.target = target;
  }
}
