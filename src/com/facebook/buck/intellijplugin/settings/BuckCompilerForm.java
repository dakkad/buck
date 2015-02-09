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

import com.facebook.buck.intellijplugin.content.BuckPluginContent;
import com.intellij.openapi.ui.ComboBox;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Compiler form that allows a user to select the regular javac or the buck
 * compiler option.
 */
public class BuckCompilerForm {

  private static final int PADDING = 6;
  private JPanel outer;
  private JComboBox<CompilerChoice> compilerChooser;
  private CompilerChoice buckChoice = new CompilerChoice(BuckPluginContent.BUCK_COMPILER_LABEL);
  private CompilerChoice intelliJChoice = new CompilerChoice(BuckPluginContent.INTELLIJ_COMPILER_LABEL);

  public BuckCompilerForm() {

    FlowLayout layout = new FlowLayout(FlowLayout.LEADING, PADDING, PADDING);
    outer = new JPanel(layout);
    JLabel label = new JLabel(BuckPluginContent.COMPILER_CHOICE_LABEL);
    outer.add(label);
    compilerChooser = new ComboBox(new CompilerChoice[] {
        buckChoice,
        intelliJChoice
    });
    outer.add(compilerChooser);
  }

  public JPanel getMainPanel() {
    return outer;
  }

  public boolean isBuckCompile() {
    return buckChoice == compilerChooser.getSelectedItem();
  }

  public void setBuckCompile(boolean buckCompile) {
    compilerChooser.setSelectedItem(buckCompile? buckChoice : intelliJChoice);
  }

  public static class CompilerChoice {

    private String name;

    public CompilerChoice(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String toString() {
      return name;
    }
  }
}
