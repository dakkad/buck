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
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;

/**
 * Buck test output event
 */
public class BuckTestEvent extends BuckEvent {

  private static final String RESULT_SET = "results";
  private static final String TEST_CASE_SET = "testCases";
  private static final String TEST_CASE_FIELD = "testCaseName";
  private static final String TOTAL_TIME_FIELD = "totalTime";
  private static final String SUCCESS_INDICATOR_FIELD = "success";
  private static final String TEST_RESULT_SET = "testResults";
  private static final String TEST_NAME_FIELD = "testName";
  private static final String TEST_TIME_FIELD = "time";
  private static final String MESSAGE_FIELD = "message";
  private static final String STACK_TRACE_FIELD = "stacktrace";
  private static final String STANDARD_OUT_FIELD = "stdOut";
  private static final String STANDARD_ERROR_FIELD = "stdErr";

  private final List<TestCase> testCases;

  private BuckTestEvent(long timestamp, String buildId, int threadId, List<TestCase> testCases) {
    super(BuckEventFactory.TEST_RESULTS_AVAILABLE, timestamp, buildId, threadId);
    this.testCases = Preconditions.checkNotNull(testCases);
  }

  public static BuckTestEvent factory(JsonNode node, long timestamp, String buildId,
      int threadId) {
    JsonNode resultsObject = node.get(RESULT_SET);
    Iterator<JsonNode> testCases = resultsObject.get(TEST_CASE_SET).elements();
    ImmutableList.Builder<TestCase> testCasesBuilder = ImmutableList.builder();
    while(testCases.hasNext()) {
      JsonNode test = testCases.next();
      TestCase testCase = TestCase.factory(test);
      testCasesBuilder.add(testCase);
    }
    List<TestCase> testList = testCasesBuilder.build();
    return new BuckTestEvent(timestamp, buildId, threadId, testList);
  }

  public static class TestCase {

    private final String testCaseName;
    private final long totalTime;
    private final boolean success;
    private final List<TestResult> testResults;

    private TestCase(String testCaseName, long totalTime, boolean success,
        List<TestResult> testResults) {
      this.testCaseName = Preconditions.checkNotNull(testCaseName);
      this.totalTime = totalTime;
      this.success = success;
      this.testResults = Preconditions.checkNotNull(testResults);
    }

    public static TestCase factory(JsonNode testCase) {
      String testCaseName = testCase.get(TEST_CASE_FIELD).asText();
      int totalTime = testCase.get(TOTAL_TIME_FIELD).asInt();
      boolean success = testCase.get(SUCCESS_INDICATOR_FIELD).asBoolean();
      Iterator<JsonNode> testResults = testCase.get(TEST_RESULT_SET).elements();
      ImmutableList.Builder<TestResult> testResultsBuilder = ImmutableList.builder();
      while (testResults.hasNext()) {
        JsonNode testResult = testResults.next();
        TestResult result = TestResult.factory(testResult);
        testResultsBuilder.add(result);
      }
      List<TestResult> resultList = testResultsBuilder.build();
      return new TestCase(testCaseName, totalTime, success, resultList);
    }

    private static class TestResult {
      private final String testName;
      private final boolean success;
      private final long time;
      private final String message;
      private final String stacktrace;
      private final String stdOut;
      private final String stdErr;

      private TestResult(String testName, boolean success, long time, String message,
          String stacktrace, String stdOut, String stdErr) {
        this.testName = Preconditions.checkNotNull(testName);
        this.success = success;
        this.time = time;
        this.message = message;
        this.stacktrace = stacktrace;
        this.stdOut = stdOut;
        this.stdErr = stdErr;
      }

      public static TestResult factory(JsonNode testResult) {
        String testName = testResult.get(TEST_NAME_FIELD).asText();
        boolean success = testResult.get(SUCCESS_INDICATOR_FIELD).asBoolean();
        long time = testResult.get(TEST_TIME_FIELD).asLong();
        String message = testResult.get(MESSAGE_FIELD).asText();
        String stacktrace = testResult.get(STACK_TRACE_FIELD).asText();
        String stdOut = testResult.get(STANDARD_OUT_FIELD).asText();
        String stdErr = testResult.get(STANDARD_ERROR_FIELD).asText();
        return new TestResult(testName, success, time, message, stacktrace, stdOut, stdErr);
      }
    }
  }
}
