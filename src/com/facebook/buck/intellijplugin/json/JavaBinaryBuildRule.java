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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Java binary build rule representation.
 *
 * @author code@damienallison.com
 */
public class JavaBinaryBuildRule extends BuildRule {

  @JsonIgnore
  private static final String[] EMPTY_DEPENDENCIES = { };

  private String[] dependencies;
  private String mainClass;
  private String manifestFile;
  private String metaInformationDirectory;
  @JsonUnwrapped
  private BuildRuleVisibity[] visability;

  @JsonProperty("deps")
  public String[] getDependencies() {
    return dependencies;
  }

  @JsonProperty("deps")
  public void setDependencies(String[] dependencies) {
    if (null == dependencies) {
      dependencies = EMPTY_DEPENDENCIES;
    }
    this.dependencies = dependencies;
  }

  @JsonProperty("main_class")
  public String getMainClass() {
    return mainClass;
  }

  @JsonProperty("main_class")
  public void setMainClass(String mainClass) {
    this.mainClass = mainClass;
  }

  @JsonProperty("manifest_file")
  public String getManifestFile() {
    return manifestFile;
  }

  @JsonProperty("manifest_file")
  public void setManifestFile(String manifestFile) {
    this.manifestFile = manifestFile;
  }

  @JsonProperty("meta_inf_directory")
  public String getMetaInformationDirectory() {
    return metaInformationDirectory;
  }

  @JsonProperty("meta_inf_directory")
  public void setMetaInformationDirectory(String metaInformationDirectory) {
    this.metaInformationDirectory = metaInformationDirectory;
  }
}
