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
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.Nullable;

/**
 * Event factory that maps json output to events
 */
public class BuckEventFactory {

  public static final String START_EVENT_LABEL = "BuildRuleStarted";
  public static final String END_EVENT_LABEL = "BuildRuleFinished";
  public static final String TEST_RESULTS_AVAILABLE = "ResultsAvailable";
  private static final Logger LOG = Logger.getInstance(BuckEventFactory.class);
  private static final String RULE_TYPE = "type";
  private static final String TIMESTAMP_FIELD = "timestamp";
  private static final String BUILD_IDENTIFIER_FIELD = "buildId";
  private static final String THREAD_ID_FIELD = "threadId";
  private static final String BUILD_RULE = "buildRule";
  private static final String NAME_FIELD = "name";
  private static final String STATUS_FIELD = "status";
  private static final String CACHE_FIELD = "cacheResult";
  private static final String CACHE_HIT_INDICATOR = "HIT";

  private BuckEventFactory() {}

  @Nullable
  public static BuckEvent toEvent(JsonNode node) {
    String type = node.get(RULE_TYPE).asText();
    long timestamp = node.get(TIMESTAMP_FIELD).asLong();
    String buildId = node.get(BUILD_IDENTIFIER_FIELD).asText();
    int threadId = node.get(THREAD_ID_FIELD).asInt();
    if (START_EVENT_LABEL.equals(type)) {
      String name = node.get(BUILD_RULE)
          .get(NAME_FIELD).asText();
      return new BuckStartEvent(timestamp, buildId, threadId, name);
    } else if (END_EVENT_LABEL.equals(type)) {
      String name = node.get(BUILD_RULE)
          .get(NAME_FIELD).asText();
      String status = node.get(STATUS_FIELD).asText();
      String cache = node.get(CACHE_FIELD).asText();
      return new BuckEndEvent(timestamp, buildId, threadId, name, status,
          CACHE_HIT_INDICATOR.equals(cache));
    } else if (TEST_RESULTS_AVAILABLE.equals(type)) {
      return BuckTestEvent.factory(node, timestamp, buildId, threadId);
    }
    LOG.warn("Unhandled message: " + node.toString());
    return null;
  }}
