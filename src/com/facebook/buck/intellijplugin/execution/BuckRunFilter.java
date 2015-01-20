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

package com.facebook.buck.intellijplugin.execution;

import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;

/**
 * The run filter interprets the output of the buck run and generates action
 * triggers.
 */
public class BuckRunFilter implements Filter {

  private final Project project;

  public BuckRunFilter(Project project) {
    this.project = project;
  }

  @Override
  public Result applyFilter(final String text, int fullLength) {
    BuckOutputFilterMessage message = BuckOutputFilterMessage.parse(text);
    // TODO(dka) Implement highlighting of the output text
    // if we have a message return
    // Result(start, end, new OpenFileHyperlinkInfo(project, file, info.getLineNumber()));
    return null;
  }
}
