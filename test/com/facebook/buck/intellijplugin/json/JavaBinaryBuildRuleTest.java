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

package com.facebook.buck.intellijplugin.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the java binary build rule serialisation.
 *
 * @author code@damienallison.com
 */
public class JavaBinaryBuildRuleTest {

  private JavaBinaryBuildRule rule;

  @Before
  public void setUp() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    rule = mapper.readValue("{\n" +
        "  \"blacklist\" : [ ],\n" +
        "  \"buck.base_path\" : \"src/com/facebook/buck/android/agent\",\n" +
        "  \"deps\" : [ \":agent-lib\" ],\n" +
        "  \"mainClass\" : \"com.facebook.buck.android.agent.AgentMain\",\n" +
        "  \"manifestFile\" : null,\n" +
        "  \"mergeManifests\" : null,\n" +
        "  \"metaInfDirectory\" : null,\n" +
        "  \"name\" : \"agent-for-host\",\n" +
        "  \"type\" : \"java_binary\",\n" +
        "  \"visibility\" : [ ]\n" +
        "}", JavaBinaryBuildRule.class);
  }

  @Test
  public void testName() {
    assertEquals("agent-for-host", rule.getName());
  }

  @Test
  public void testVisibility() {
    String[] visibility = rule.getVisibility();
    assertTrue("Visbility should not be null", null != visibility);
    assertEquals("There should be 1 visibility rule", 0, visibility.length);
  }
}
