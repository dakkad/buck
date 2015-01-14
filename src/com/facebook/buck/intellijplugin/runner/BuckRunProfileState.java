package com.facebook.buck.intellijplugin.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Buck run profile state captures the different aspects of a runner profile.
 *
 * @author code@damienallison.com
 */
public class BuckRunProfileState extends CommandLineState {

  private static Logger log = Logger.getInstance("BuckPlugin.BuckRunProfileState");
  private final BuckRunParameters parameters;


  public BuckRunProfileState(ExecutionEnvironment executionEnvironment,
      BuckRunParameters parameters) {
    super(executionEnvironment);
    this.parameters = parameters;
  }

  @NotNull
  @Override
  protected ProcessHandler startProcess() throws ExecutionException {
    log.info("Starting Buck Command Line Process");
    GeneralCommandLine commandLine = new GeneralCommandLine();
    String command = parameters.getFullCommand();
    if (null == command) {
      throw new ExecutionException("Invalid / Missing buck project config");
    }
    commandLine.setExePath(command);
    commandLine.setPassParentEnvironment(true);
    commandLine.addParameters(parameters.getArguments());
    OSProcessHandler processHandler = new ColoredProcessHandler(
        commandLine.withWorkDirectory(parameters.getWorkingDirectory())
        .createProcess(), commandLine.getCommandLineString());
    ProcessTerminatedListener.attach(processHandler, getEnvironment().getProject());
    return processHandler;
  }

}
