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

import com.google.common.base.Preconditions;

/**
 * An event from Buckd
 */
public class BuckEvent {

  protected final String type;
  protected final long timestamp;
  protected final String buildId;
  protected final long threadId;

  BuckEvent(String type, long timestamp, String buildId, int threadId) {
    this.type = Preconditions.checkNotNull(type);
    this.timestamp = Preconditions.checkNotNull(timestamp);
    this.buildId = Preconditions.checkNotNull(buildId);
    this.threadId = Preconditions.checkNotNull(threadId);
  }

  public String getType() {
    return type;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getBuildId() {
    return buildId;
  }

  public long getThreadId() {
    return threadId;
  }
}
