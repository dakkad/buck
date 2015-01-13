package com.facebook.buck.intellijplugin.runner;

import java.util.Arrays;
import java.util.List;

/**
 * Buck run parameters capture the required project names and other buck
 * parameters passed to buck.
 *
 * @author code@damienallison.com
 */
public class BuckRunParameters {
  public String getFullCommand() {
    return "buck";
  }

  public List<String> getArguments() {
    return Arrays.asList("targets");
  }

  public String getWorkingDirectory() {
    return ".";
  }
}
