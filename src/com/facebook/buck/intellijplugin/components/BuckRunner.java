package com.facebook.buck.intellijplugin.components;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.GenericProgramRunner;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dak on 12/01/15.
 */
public class BuckRunner extends GenericProgramRunner {
  @NotNull
  @Override
  public String getRunnerId() {
    return null;
  }

  @Override
  public boolean canRun(@NotNull String executorId, @NotNull RunProfile runProfile) {
    return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) &&
        runProfile instanceof BuckRunProfile;
  }
}
