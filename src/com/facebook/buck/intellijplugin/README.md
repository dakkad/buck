Buck IntelliJ Plugin
=============================
This project provides an IntelliJ plugin to support running buck project and test rules from within
IntelliJ. It also includes functionality for core developer work patterns related to buck based
development.

# Developer Project Setup

General instruction are available on [configuring IntelliJ](https://confluence.jetbrains.com/display/IDEADEV/Getting+Started+with+Plugin+Development#GettingStartedwithPluginDevelopment-anchor2)

* Install IntelliJ IDEA 14 Community Edition.
* Clone the IntelliJ source code (for debugging) from
    * git clone git://git.jetbrains.org/idea/community.git idea
* Import IntelliJ Module for plugin development.
* Set the path to the plugin directory to be buck/src/com/facebook/buck/intellijplugin/resources
* Set up the project SDK (Project Structure -> SDK -> (+ Add) -> Select IntelliJ Platform Plugin SDK
* Set your compiler to use compatibility with 1.6 (Settings -> Build, Execution and Deployment ->
  Compiler -> Java Compiler -> Project Bytecode Version = 1.6.

# Entry Points
The initial version of the runner supports basic buck project commands with the ability
to feed back that buck is running in a background. The BuckProjectAction contains
the primary entry point for the buck project action.

# Plugin Packaging




