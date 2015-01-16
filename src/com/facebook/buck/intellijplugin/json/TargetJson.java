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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Buck target representation.
 *
 * @author code@damienallison.com
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TargetJson {

  private String buckBasePath;
  private String buckOutputFile;
  private String[] annotationProcessorDependencies;
  private String baseModule;
  private String name;
  private String output;
  private String source;
  private String[] dependencies;
  private String[] exportDependencies;
  private String ruleType;
  private String[] visibility;

  @JsonProperty("buck.base_path")
  public String getBuckBasePath() {
    return buckBasePath;
  }

  @JsonProperty("buck.base_path")
  public void setBuckBasePath(String buckBasePath) {
    this.buckBasePath = buckBasePath;
  }

  @JsonProperty("buck.output_file")
  public String getBuckOutputFile() {
    return buckOutputFile;
  }

  @JsonProperty("buck.output_file")
  public void setBuckOutputFile(String buckOutputFile) {
    this.buckOutputFile = buckOutputFile;
  }

  @JsonProperty("annotationProcessorDeps")
  public String[] getAnnotationProcessorDependencies() {
    return annotationProcessorDependencies;
  }

  @JsonProperty("annotationProcessorDeps")
  public void setAnnotationProcessorDependencies(
      String[] annotationProcessorDependencies) {
    this.annotationProcessorDependencies = annotationProcessorDependencies;
  }

  public String getBaseModule() {
    return baseModule;
  }

  public void setBaseModule(String baseModule) {
    this.baseModule = baseModule;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("out")
  public String getOutput() {
    return output;
  }

  @JsonProperty("out")
  public void setOutput(String output) {
    this.output = output;
  }

  @JsonProperty("src")
  public String getSource() {
    return source;
  }

  @JsonProperty("src")
  public void setSource(String source) {
    this.source = source;
  }

  @JsonProperty("deps")
  public String[] getDependencies() {
    return dependencies;
  }

  @JsonProperty("deps")
  public void setDependencies(String[] dependencies) {
    this.dependencies = dependencies;
  }

  @JsonProperty("exportDeps")
  public String[] getExportDependencies() {
    return exportDependencies;
  }

  @JsonProperty("exportDeps")
  public void setExportDependencies(String[] exportDependencies) {
    this.exportDependencies = exportDependencies;
  }

  @JsonProperty("type")
  public String getRuleType() {
    return ruleType;
  }

  @JsonProperty("type")
  public void setRuleType(String ruleType) {
    this.ruleType = ruleType;
  }

  public String[] getVisibility() {
    return visibility;
  }

  public void setVisibility(String[] visibility) {
    this.visibility = visibility;
  }
}
