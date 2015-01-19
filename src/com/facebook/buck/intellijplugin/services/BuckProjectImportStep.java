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

package com.facebook.buck.intellijplugin.services;

import com.facebook.buck.intellijplugin.components.BuckConfiguration;
import com.facebook.buck.intellijplugin.content.BuckPluginContent;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.diagnostic.Logger;

import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The buck project step adds configuration options for a buck project
 * configuration setup.
 *
 * @author code@damienallison.com
 */
public class BuckProjectImportStep extends ModuleWizardStep {

  private static final Logger LOG = Logger.getInstance(BuckProjectImportStep.class);
  private static final int INPUT_TEXT_SIZE = 32;
  private static final int PADDING = 6;

  private final WizardContext context;
  private JTextField projectInput;

  public BuckProjectImportStep(WizardContext context) {
    this.context = context;
  }

  @Override
  public JComponent getComponent() {
    LOG.info("Creating Buck Import Display Components");
    JLabel projectLabel = new JLabel(BuckPluginContent.PROJECT_NAMES_LABEL);
    projectInput = new JTextField(INPUT_TEXT_SIZE);
    FlowLayout layout = new FlowLayout(FlowLayout.LEADING, PADDING, PADDING);
    JPanel outer = new JPanel(layout);
    outer.setLayout(layout);
    outer.add(projectLabel);
    outer.add(projectInput);
    return outer;
  }

  @Override
  public void updateDataModel() {
    BuckConfiguration.setProjectNames(context.getProject(), projectInput.getText());
  }
}
