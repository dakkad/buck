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

package com.facebook.buck.intellijplugin.settings;

import com.facebook.buck.intellijplugin.content.BuckPluginContent;
import com.intellij.openapi.externalSystem.settings.ExternalSystemSettingsListener;
import com.intellij.util.messages.Topic;

/**
 * Buck settings listener.
 */
public interface BuckSettingsListener extends ExternalSystemSettingsListener<BuckProjectSettings> {
  public static final Topic<BuckSettingsListener> TOPIC =
      Topic.create(BuckPluginContent.SETTINGS_LABEL, BuckSettingsListener.class);
}
