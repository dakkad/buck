package com.facebook.buck.intellijplugin.runner;

import com.facebook.buck.intellijplugin.BuckPlugin;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.GenericProgramRunner;
import org.jetbrains.annotations.NotNull;

/**
 * The buck runner offers a basis for external compile and run functions.
 *
 * @author code@damienallison.com
 */
public class BuckRunner extends GenericProgramRunner {
  @NotNull
  @Override
  public String getRunnerId() {
    return BuckPlugin.RUNNER_ID;
  }

  @Override
  public boolean canRun(@NotNull String executorId, @NotNull RunProfile runProfile) {
    return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) &&
        runProfile instanceof BuckRunProfile;
  }
}
