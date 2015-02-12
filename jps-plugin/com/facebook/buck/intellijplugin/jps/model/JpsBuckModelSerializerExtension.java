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

package com.facebook.buck.intellijplugin.jps.model;

import com.facebook.buck.intellijplugin.BuckPlugin;

import com.intellij.openapi.diagnostic.Logger;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.module.JpsModule;
import org.jetbrains.jps.model.serialization.JpsModelSerializerExtension;
import org.jetbrains.jps.model.serialization.JpsProjectExtensionSerializer;

import java.util.Collections;
import java.util.List;

/**
 * The model serializer extension. IntelliJ uses these serialisation functions
 * to pass configuration information to the external builder.
 */
public class JpsBuckModelSerializerExtension extends JpsModelSerializerExtension {

  private static final Logger LOG = Logger.getInstance(JpsBuckModelSerializerExtension.class);

  // As we are in a different context can't refer to the constants in the main project.
  // TODO(dka) Consider refactoring into common.
  @NonNls @NotNull
  public static final String EXTERNAL_SYSTEM_ID_KEY  = "external.system.id";
  @NonNls @NotNull
  public static final String LINKED_PROJECT_ID_KEY   = "external.linked.project.id";
  @NonNls @NotNull
  public static final String LINKED_PROJECT_PATH_KEY = "external.linked.project.path";


  public static JpsBuckModuleExtension findModuleExtension(JpsModule module) {
    return module.getContainer().getChild(JpsBuckModuleExtension.ROLE);
  }

  @NotNull @Override
  public List<? extends JpsProjectExtensionSerializer> getProjectExtensionSerializers() {
    return Collections.singletonList(new JpsBuckProjectExtensionSerializer());
  }

  @Override
  public void loadModuleOptions(@NotNull JpsModule module, @NotNull Element rootElement) {
    super.loadModuleOptions(module, rootElement);
    String externalSystemId = rootElement.getAttributeValue(EXTERNAL_SYSTEM_ID_KEY);
    String linkedProjectId = rootElement.getAttributeValue(LINKED_PROJECT_ID_KEY);
    String linkedProjectPath = rootElement.getAttributeValue(LINKED_PROJECT_PATH_KEY);
    if (BuckPlugin.BUCK_PLUGIN_ID.equals(externalSystemId) &&
        null != linkedProjectId && null != linkedProjectPath) {
      LOG.info("Loaded module options for buck linkedProjectPath: " +
          linkedProjectPath + " linkedProjectId: " + linkedProjectId);
      JpsBuckModuleExtension moduleExtension = new JpsDefaultBuckModuleExtension(
          linkedProjectPath, linkedProjectId);
      module.getContainer().setChild(JpsBuckModuleExtension.ROLE, moduleExtension);
    } else {
      LOG.info("Did not load module options for buck due to external system id mismatch: " +
          externalSystemId);
    }
  }
}
