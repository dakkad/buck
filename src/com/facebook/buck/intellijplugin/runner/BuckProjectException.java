package com.facebook.buck.intellijplugin.runner;

import com.intellij.execution.ExecutionException;

/**
 * Execution exception for errors running the buck project component.
 *
 * @author code@damienallison.com
 */
public class BuckProjectException extends ExecutionException {

  public BuckProjectException(String message, Throwable source) {
    super(message, source);
  }

  public BuckProjectException(String message) {
    super(message);
  }
}
