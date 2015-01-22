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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.intellij.openapi.diagnostic.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Buck targets command.
 */
public class BuckTargetsCommand {

  private static final Logger LOG = Logger.getInstance(BuckTargetsCommand.class);
  private static final String BUCK_BASE_PATH = "buck.base_path";
  private static final String BUCK_TARGET_NAME = "name";
  private static final String BUCK_TARGET_TYPE = "type";

  private BuckTargetsCommand() {}

  public static List<BuckTarget> getTargets(BuckCommand buckRunner)
      throws IOException {

    int exitCode = buckRunner.execute("targets", "--json");
    if (exitCode != 0) {
      throw new RuntimeException(buckRunner.getStdErr());
    }

    // Parse output
    ObjectMapper mapper = MapperFactory.getInstance();
    JsonNode jsonNode = mapper.readTree(buckRunner.getStdOut());
    if (!jsonNode.isArray()) {
      throw new IllegalStateException();
    }

    List<BuckTarget> builder = Lists.newArrayList();
    for (JsonNode target : jsonNode) {
      if (!target.isObject()) {
        throw new IllegalStateException("Expected target node");
      }
      String basePath = target.get(BUCK_BASE_PATH).asText();
      String name = target.get(BUCK_TARGET_NAME).asText();
      String type = target.get(BUCK_TARGET_TYPE).asText();
      builder.add(new BuckTarget(type, name, basePath));
    }
    return builder;
  }
}
