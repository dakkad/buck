package com.facebook.buck.intellijplugin.buckprocess;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;

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

  private InputStream inputStream;
  private ProgressIndicator progressIndicator;

  protected BuckWatcher() {}

  public static BuckWatcher fromProcess(Process process,
      ProgressIndicator progressIndicator) {
    return BuckWatcher.newBuilder()
        .setInputStream(process.getInputStream())
        .setProgressIndicator(progressIndicator)
        .build();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public void watch() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while (null != (line = in.readLine())) {
        LOG.info("Buck: " + line);
      }
    } catch (IOException e) {
      LOG.error("Error watching buck", e);
    }
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

    public BuckWatcher build() {
      return result;
    }
  }
}
