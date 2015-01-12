package com.facebook.buck.intellijplugin.components;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;

import org.jetbrains.annotations.NotNull;

/**
 * Code based plugin registration using action manager to register actions.
 *
 * @author code@damienallison.com
 */
public class BuckPluginRegistration implements ApplicationComponent {

  @Override
  public void initComponent() {

  }

  @Override
  public void disposeComponent() {
    ActionManager manager = ActionManager.getInstance();
    HelloAlert alert = new HelloAlert();
    manager.registerAction("sayHello", alert);
    // Get an instance of the default window menu action group
    DefaultActionGroup actionGroup = (DefaultActionGroup) manager.getAction(ActionNames.WINDOW_MENU);
    actionGroup.addSeparator();
    actionGroup.add(alert);
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "Buck Plugin";
  }
}
