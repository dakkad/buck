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

import com.facebook.buck.intellijplugin.components.BuckConfiguration;
import com.facebook.buck.intellijplugin.content.BuckPluginContent;
import com.intellij.openapi.project.Project;

import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Buck compiler form provides settings and configuration for the buck compiler.
 */
public class BuckCompilerForm {

  private static final int PADDING = 6;
  private static final String EMPTY_TARGET_LIST = "";
  private JPanel outer;
  private JTextField field;

  private BuckCompilerForm() { }

  public static BuckCompilerForm newInstance() {
    BuckCompilerForm form = new BuckCompilerForm();
    FlowLayout layout = new FlowLayout(FlowLayout.LEADING, PADDING, PADDING);
    form.outer = new JPanel(layout);
    JLabel label = new JLabel(BuckPluginContent.PROJECT_NAMES_LABEL);
    form.outer.add(label);
    String projects = EMPTY_TARGET_LIST;
    form.field = new JTextField(projects, 36);
    form.field.setEnabled(false);
    form.outer.add(form.field);
    return form;
  }

  public static BuckCompilerForm newInstance(String targets) {
    BuckCompilerForm form = new BuckCompilerForm();
    FlowLayout layout = new FlowLayout(FlowLayout.LEADING, PADDING, PADDING);
    form.outer = new JPanel(layout);
    JLabel label = new JLabel(BuckPluginContent.PROJECT_NAMES_LABEL);
    form.outer.add(label);
    form.field = new JTextField(targets, 36);
    form.field.setEnabled(false);
    form.outer.add(form.field);
    return form;
  }

  public static BuckCompilerForm newInstance(Project project) {
    String targets = BuckConfiguration.getTargetNames(project);
    return newInstance(targets);
  }

  public JComponent getComponent() {
    return outer;
  }

  public void setText(String targetNames) {
    if (null == targetNames) {
      targetNames = EMPTY_TARGET_LIST;
    }
    field.setText(targetNames);
  }

  public String getText() {
    return field.getText();
  }

  public boolean isModified(String targetNames) {
    return !getText().equals(targetNames);
  }

  public void reset() {
    field.setText(EMPTY_TARGET_LIST);
  }
}
