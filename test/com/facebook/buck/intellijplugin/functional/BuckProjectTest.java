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

package com.facebook.buck.intellijplugin.functional;

import com.intellij.openapi.project.Project;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.builders.ModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.ModuleFixture;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl;
import org.junit.Test;

/**
 * Functional testing of the buck project functions.
 *
 * @author code@damienallison.com
 */
public class BuckProjectTest extends UsefulTestCase {

  private static final String SOURCE_ROOT = "src/java";
  private static final boolean USE_PLATFORM_SOURCE_ROOT = true;

  private TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder;
  private ModuleFixtureBuilder moduleBuilder;
  private IdeaProjectTestFixture ideaFixture;
  private LightTempDirTestFixtureImpl tempDirectory;
  private ModuleFixture ideaModule;


  @Override
  public void setUp() throws Exception {
    tempDirectory = new LightTempDirTestFixtureImpl(USE_PLATFORM_SOURCE_ROOT);

    fixtureBuilder = IdeaTestFixtureFactory.getFixtureFactory()
        .createLightFixtureBuilder();

    moduleBuilder = fixtureBuilder.addModule(JavaModuleFixtureBuilder.class);
    moduleBuilder.addContentRoot(getTestDirectoryName());
    moduleBuilder.addSourceRoot(getTestDirectoryName() + SOURCE_ROOT);


    ideaFixture = fixtureBuilder.getFixture();

    ideaFixture.setUp();
    ideaModule = moduleBuilder.getFixture();
  }

  public Project getProject() {
    return ideaFixture.getProject();
  }

  @Override
  public void tearDown() throws Exception {
    ideaFixture.tearDown();
    super.tearDown();
  }


  @Test
  public void testBuckProjectRun() {
    Project project = getProject();
    fail("Can't test clicking buck build");
  }
}
