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
 * Buck command builder.
 */
public class BuckBuildCommand {

  private static final Logger LOG = Logger.getInstance(BuckBuildCommand.class);

  private BuckBuildCommand() { }

  public static void build(BuckCommand buckRunner, BuckTarget target) {
    int exitCode = buckRunner.executeAndListenToWebSocket("build",
        target.getFullName());
    if (exitCode != 0) {
      buckRunner.signalBuildFailed(target.getFullName());
      //LOG.error(buckRunner.getStdErr());
      LOG.info("Buck Failed to run: " + buckRunner.getStdErr());
      return;
    }
  }
}