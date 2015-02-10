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

package com.facebook.buck.intellijplugin.jps.incremental;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.facebook.buck.intellijplugin.jps.wrapper.BuckEvent;
import com.facebook.buck.intellijplugin.jps.wrapper.BuckEventListener;

import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.messages.BuildMessage.Kind;
import org.jetbrains.jps.incremental.messages.CompilerMessage;

/**
 * Listen to buck events and adapt them to intellij messages.
 */
public class BuckEventAdaptor implements BuckEventListener {

  private final CompileContext context;

  public BuckEventAdaptor(CompileContext compileContext) {
    this.context = compileContext;
  }

  @Override
  public void onEvent(BuckEvent event) {
    // BuildMessage Kind:ERROR/WARNING/INFO/PROGRESS

    // DoneSomethingNotification relates to build message

    // BuilderStatisticsMessage elapsedMillis

    // ProgressMessage doneRatio

    // Compiler Message Kind:ERROR/WARNING/INFO/PROGRESS,
    //    message, sourcePath, problemBeginOffset, problemEndOffset,
    //    problemLocationOffset, locationLine, locationColumn

    // BuildTargetProgressMessage targets, BuildTargetProgressMessage.Event:STARTED/FINISHED

    // CustomBuilderMessage Message Type / Message Text

    // FileGeneratedEvent filePaths

    // FileDeletedEvent filePaths
    context.processMessage(new CompilerMessage(BuckPlugin.BUCK_PLUGIN_ID,
        Kind.WARNING, event.getType()));
  }
}
