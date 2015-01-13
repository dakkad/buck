package com.facebook.buck.intellijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ProgramRunner;
import org.jetbrains.annotations.Nullable;

/**
 * Buck run profile state captures the different aspects of a runner profile.
 *
 * @author code@damienallison.com
 */
public class BuckRunProfileState implements RunProfileState {

  @Nullable
  @Override
  public ExecutionResult execute(Executor executor, ProgramRunner programRunner)
      throws ExecutionException {
    return null;
  }
}
