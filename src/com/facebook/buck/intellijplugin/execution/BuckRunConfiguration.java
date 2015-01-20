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

import com.facebook.buck.intellijplugin.components.BuckConfigurationComponent;
import com.facebook.buck.intellijplugin.runner.BuckRunParameters;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;

/**
 * Buck run configuration implementation.
 */
public class BuckRunConfiguration extends LocatableConfigurationBase {

  private BuckRunParameters parameters;

  public BuckRunConfiguration(Project project,
      ConfigurationFactory configurationFactory, String buckProjectLabel) {
    super(project, configurationFactory, buckProjectLabel);
    parameters = new BuckRunParameters(project);
  }

  public BuckRunParameters getParameters() {
    return parameters;
  }

  @Override
  public void writeExternal(final Element element) throws WriteExternalException {
      super.writeExternal(element);
    XmlSerializer.serializeInto(parameters, element);
  }

  @Override
  public void readExternal(final Element element) throws  InvalidDataException {
    super.readExternal(element);
    XmlSerializer.deserializeInto(parameters, element);
  }

  @Override
  public RunProfileState getState(Executor executor, ExecutionEnvironment environment)
      throws ExecutionException {
    try {
      checkState();
    } catch (IllegalStateException e) {
      throw new ExecutionException(e.getMessage());
    }
    return new BuckRunningState(environment, parameters);
  }

  @Override
  public void checkConfiguration() throws RuntimeConfigurationException {
    try {
      checkState();
    } catch (IllegalStateException e) {
      throw new RuntimeConfigurationException(e.getMessage());
    }
  }

  @Override
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return new BuckRunConfigurable(getProject());
  }

  private void checkState() throws IllegalStateException {
    if (null == parameters.getCommand()) {
      throw new IllegalStateException("Run configuration command is null");
    }
    if (null == parameters.getWorkingDirectory()) {
      throw new IllegalStateException("Run working directory is null");
    }
    // TODO(dka) Implement checks for directory / executable being found
  }


  public String getTarget() {
    return BuckConfigurationComponent.getTargetNames(getProject());
  }

  public void setTarget(String target) {
    BuckConfigurationComponent.setTargetNames(getProject(), target);
  }

  @Override
  public RunConfiguration clone() {
    return new BuckRunConfiguration(getProject(), getFactory(), getName());
  }
}
