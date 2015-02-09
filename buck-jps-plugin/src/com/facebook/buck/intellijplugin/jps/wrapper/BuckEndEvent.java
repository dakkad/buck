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
 * Buck event for reached the end of output.
 */
public class BuckEndEvent extends BuckEvent {

  private final String name;
  private final String status;
  private final boolean cached;

  public BuckEndEvent(long timestamp, String buildId, int threadId, String name,
      String status, boolean cached) {
    super(BuckEventFactory.END_EVENT_LABEL, timestamp, buildId, threadId);
    this.name = Preconditions.checkNotNull(name);
    this.status = Preconditions.checkNotNull(status);
    this.cached = Preconditions.checkNotNull(cached);
  }

  public String getName() {
    return name;
  }

  public String getStatus() {
    return status;
  }

  public boolean isSuccessful() {
    return status.equals("SUCCESS");
  }

  public boolean isCached() {
    return cached;
  }

}
