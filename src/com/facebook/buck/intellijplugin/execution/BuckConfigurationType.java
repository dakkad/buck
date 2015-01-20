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

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.Project;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

/**
 * Buck configuration types which hooks in the buck configuration. This class
 * provides a factory that provides the buck configuration.
 */
public class BuckConfigurationType implements ConfigurationType {

  private final ConfigurationFactory factory;

  public BuckConfigurationType() {
    factory = new ConfigurationFactory(this) {
      public RunConfiguration createTemplateConfiguration(Project project) {
        return new BuckRunConfiguration(project, this,
            BuckPlugin.BUCK_PROJECT_LABEL);
      }
    };
  }

  @Override
  public String getDisplayName() {
    return "Buck";
  }

  @Override
  public String getConfigurationTypeDescription() {
    return "Build with buck";
  }

  @Override
  public Icon getIcon() {
    return BuckPlugin.ICON;
  }

  @NotNull
  @Override
  public String getId() {
    return BuckPlugin.BUCK_PLUGIN_ID;
  }

  @Override
  public ConfigurationFactory[] getConfigurationFactories() {
    return new ConfigurationFactory[]{ getFactory() };
  }

  private ConfigurationFactory getFactory() {
    return factory;
  }

  public static BuckConfigurationType getInstance() {
    return ContainerUtil.findInstance(Extensions.getExtensions(CONFIGURATION_TYPE_EP),
        BuckConfigurationType.class);
  }
}
