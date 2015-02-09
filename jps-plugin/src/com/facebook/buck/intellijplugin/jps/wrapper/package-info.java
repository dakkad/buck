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

/**
 * The wrapper class is based on an adaptation of a previosly written buck
 * wrapper https://github.com/facebook/buck/commit/61266f1ac4de9026a3b50c5adb01a00ada6f5e03
 * with some modifications for readability. It is intended that this will be the
 * first thing to be refactored so don't rely on it.
 */
package com.facebook.buck.intellijplugin.jps.wrapper;