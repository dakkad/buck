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
 * Buck start event
 */
public class BuckStartEvent extends BuckEvent {

  private final String name;

  BuckStartEvent(long timestamp, String buildId, int threadId, String name) {
    super(BuckEventFactory.START_EVENT_LABEL, timestamp, buildId, threadId);
    this.name = Preconditions.checkNotNull(name);
  }

  public String getName() {
    return name;
  }

  public boolean matchesEndRule(BuckEndEvent endEvent) {
    Preconditions.checkNotNull(endEvent);
    return getThreadId() == endEvent.getThreadId() && getBuildId().equals(endEvent.getBuildId())
        && getName().equals(endEvent.getName());
  }
}
