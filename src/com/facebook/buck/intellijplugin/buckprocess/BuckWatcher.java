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

package com.facebook.buck.intellijplugin.buckprocess;

import com.facebook.buck.intellijplugin.tools.BuckToolWindow;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.wm.ToolWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.swing.JTextArea;
/**
 * The Buck watcher provides functions for watching a buck process and handling
 * results.
 *
 * @author code@damienallison.com
 */
public class BuckWatcher {

  private static final Logger LOG = Logger.getInstance(BuckWatcher.class);
  private static final String NEW_LINE = "\n";

  private InputStream inputStream;
  private ProgressIndicator progressIndicator;
  private ToolWindow toolWindow;
  private String buckCommand;

  protected BuckWatcher() {}

  public static BuckWatcher fromProcess(Process process,
      ProgressIndicator progressIndicator) {
    return BuckWatcher.newBuilder()
        .setInputStream(process.getErrorStream())
        .setProgressIndicator(progressIndicator)
        .build();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public void watch() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,
          StandardCharsets.UTF_8));

      JTextArea textArea = BuckToolWindow.resolveTextPane(toolWindow);
      clearTextArea(textArea);
      appendLine(textArea, "Buck Command: " + buckCommand);

      String line;
      while (null != (line = in.readLine())) {
        LOG.info("Buck: " + line);
        appendLine(textArea, line);
      }
      LOG.info("Buck: DONE");
    } catch (IOException e) {
      LOG.error("Error watching buck", e);
    }
  }

  private void clearTextArea(final JTextArea textArea) {
    ApplicationManager.getApplication().invokeLater(new Runnable() {
      @Override
      public void run() {
        textArea.setText("");
        textArea.repaint();
      }
    });
  }

  private void appendLine(final JTextArea textArea, final String line) {
    ApplicationManager.getApplication().invokeLater(new Runnable() {
      @Override
      public void run() {
        textArea.append(line);
        textArea.append(NEW_LINE);
        textArea.repaint();
      }
    });
  }

  public static class Builder {

    private BuckWatcher result = new BuckWatcher();

    public Builder setInputStream(InputStream inputStream) {
      result.inputStream = inputStream;
      return this;
    }

    public Builder setProgressIndicator(ProgressIndicator progressIndicator) {
      result.progressIndicator = progressIndicator;
      return this;
    }

    public Builder setToolWindow(ToolWindow toolWindow) {
      result.toolWindow = toolWindow;
      return this;
    }

    public BuckWatcher build() {
      return result;
    }

    public Builder setBuckCommand(String buckCommand) {
      result.buckCommand = buckCommand;
      return this;
    }
  }
}
