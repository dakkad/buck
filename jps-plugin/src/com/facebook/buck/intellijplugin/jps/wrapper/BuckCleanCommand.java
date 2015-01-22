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

package com.facebook.buck.intellijplugin.jps.wrapper;

import com.intellij.openapi.diagnostic.Logger;

/**
 * Clean command for buck.
 */
public class BuckCleanCommand {

  private static final Logger LOG = Logger.getInstance(BuckCleanCommand.class);

  private BuckCleanCommand() {}

  public static void clean(BuckCommand buckRunner) {
    int exitCode = buckRunner.execute("clean");
    if (exitCode != 0) {
      LOG.error(buckRunner.getStdErr());
      return;
    }
  }
}
