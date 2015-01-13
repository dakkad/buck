package com.facebook.buck.intellijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * The buck project runner is a program runner which runs the external buck
 * command line tool.
 *
 * Currently the buck project runner is executed along with the other default
 * runners
 */
public class BuckProjectRunner extends DefaultProgramRunner {

  private static final String BUCK_PROJECT_RUNNER_ID = "BuckPlugin.ProjectRunner";

  @NotNull
  @Override
  public String getRunnerId() {
    return BUCK_PROJECT_RUNNER_ID;
  }

  @Override
  public boolean canRun(String executorId, RunProfile runProfile) {
    // TODO(dka) 20150113 Check the run profile to see if is a buck profile
    return true;
  }

  @Override
  protected RunContentDescriptor doExecute(@NotNull RunProfileState state,
      @NotNull ExecutionEnvironment env) throws ExecutionException {
    RunProfile profile = env.getRunProfile();
    BuckRunProfileState buckProfile = new BuckRunProfileState(env, new BuckRunParameters());
    return super.doExecute(buckProfile, env);
  }
}
