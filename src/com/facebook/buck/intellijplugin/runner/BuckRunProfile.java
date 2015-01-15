package com.facebook.buck.intellijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Buck run profile which sub classes
 */
public class BuckRunProfile implements RunProfile {

  @Nullable
  @Override
  public RunProfileState getState(Executor executor,
      ExecutionEnvironment executionEnvironment) throws ExecutionException {
    return new BuckRunProfileState(executionEnvironment,
        new BuckRunParameters(executionEnvironment.getProject()));
  }

  @Override
  public String getName() {
    return "BuckPlugin.RunProfile";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    //
    return null;
  }
}
