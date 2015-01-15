package com.facebook.buck.intellijplugin.buckprocess;

import com.facebook.buck.intellijplugin.tools.BuckToolWindow;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Buck watcher provides functions for watching a buck process and handling
 * results.
 *
 * @author code@damienallison.com
 */
public class BuckWatcher {

  private static final Logger LOG = Logger.getInstance(BuckWatcher.class);
  private static final String EMPTY = "";
  private static final String NEW_LINE = "\n";

  private InputStream inputStream;
  private ProgressIndicator progressIndicator;
  private ToolWindow toolWindow;

  protected BuckWatcher() {}

  public static BuckWatcher fromProcess(Process process,
      ProgressIndicator progressIndicator) {
//    try {
//      process.waitFor();
//    } catch (InterruptedException e) {
//      throw new RuntimeException("Interrupted waiting for buck");
//    }
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
      BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
      /*while (!in.ready()) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          LOG.error("Interrupted waiting for buck input stream");
        }
      } */

      JTextArea textArea = BuckToolWindow.resolveTextPane(toolWindow);
      textArea.setText(EMPTY);
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
  }
}
