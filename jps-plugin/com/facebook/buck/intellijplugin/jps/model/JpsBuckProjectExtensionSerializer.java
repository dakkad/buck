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
import com.facebook.buck.intellijplugin.runner.BaseDirectoryResolver;

import com.google.common.collect.Lists;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.JDOMUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsProject;
import org.jetbrains.jps.model.serialization.JpsProjectExtensionSerializer;

import java.util.Collections;
import java.util.List;

/**
 * Jps buck project extension serializer which provides support methods to get
 * the project extension configuration.
 *
 */
public class JpsBuckProjectExtensionSerializer extends JpsProjectExtensionSerializer {

  private static final String LINKED_PROJECT_SETTINGS = "linkedExternalProjectsSettings";
  private static final String SUB_LIST = "list";
  private static final String OPTION_FIELD = "option";
  private static final String VALUE_ATTRIBUTE = "value";
  private static final Logger LOG = Logger.getInstance(JpsBuckProjectExtensionSerializer.class);

  public JpsBuckProjectExtensionSerializer() {
    super(BaseDirectoryResolver.BUCK_CONFIG_FILE, BuckPlugin.BUCK_SETTINGS_LABEL);
  }


  public static JpsBuckProjectExtension findProjectExtension(JpsProject project) {
    return project.getContainer().getChild(JpsBuckProjectExtension.ROLE);
  }

  @Override
  public void loadExtension(@NotNull JpsProject jpsProject, @NotNull Element componentElement) {
    Element linkedSettings = JDOMExternalizerUtil.getOption(componentElement,
        LINKED_PROJECT_SETTINGS);
    Element projectSettings = null == linkedSettings? null :
        linkedSettings.getChild(BuckPlugin.BUCK_PROJECT_SETTINGS_ELEMENT);
    if (null == projectSettings) {
      LOG.info("Buck Project Extensions not found by serializer");
      return;
    }
    String projectPath = JDOMExternalizerUtil.readField(projectSettings,
        BuckPlugin.PROJECT_PATH_SETTING);
    if (null == projectPath) {
      return;
    }

    Element targetsElement = JDOMExternalizerUtil.getOption(projectSettings,
        BuckPlugin.PROJECT_TARGETS_SETTING);
    Element targetNames = null == targetsElement? null :
        targetsElement.getChild(SUB_LIST);
    List<Element> nameList = null == targetNames? Collections.<Element>emptyList() :
        JDOMUtil.getChildren(targetNames, OPTION_FIELD);
    List<String> targetNameList = Lists.newArrayList();
    for (Element element : nameList) {
      String value = element.getAttributeValue(VALUE_ATTRIBUTE);
      if (null != value) {
        targetNameList.add(value);
      }
    }
    JpsBuckProjectExtension extension = new JpsBuckProjectExtensionBean(projectPath,
        targetNameList);
    jpsProject.getContainer().setChild(JpsBuckProjectExtension.ROLE,
        extension);
  }

  @Override
  public void saveExtension(@NotNull JpsProject jpsProject, @NotNull Element element) {
    // TODO(dka) Consider saving the extension. Not done in pants.
  }

}
