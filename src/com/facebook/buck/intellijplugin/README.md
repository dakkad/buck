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

## Action Events

One of the simplest ways to get started with Intellij plugin development is by
using action events which bind to key strokes. These can overload the events in
the UI. In an early version of this project we used the 'buck build' action to
override the shift-f10 'Run' command which ran buck in the background. Buck was
run using a 'background task' which shows up in the status bar to highlight that
it is running. The output was then sent to a 'tool window'. The tool window
feature allows creating arbitrary UI elements which are toggled by a side bar
button. These features combined with the 'configuration component' allow
creating fairly sophisticated UI using a pretty standard integration point.

## External System Extensions

To integrate the buck compiler into IntelliJ as the main compiler used by
IntelliJ it is necessary to use the external system hooks in 'extensions'. The
current plugin development is broken into a number of parts:

### External System Manager
The external system manager provides a number of 'binding' functions that allow
IntelliJ to identify certain key function classes etc. The main function sesms
to be to register external system identifiers and register key functions like
the external system project resolver. Note the different functions of the
Configurable, Configuration and Settings (local and scoped) and how they
interact.

### The Build Target Scope Provider

As the name suggests the primary funciton of this component is to analyse the
build changed file list and generate build targets.

### The Compiler Server Plugin

The compiler server plugin runs a java service container as a standard java
service and sends commands to it in order to build the system. This part of the
system is built under a specific top level directory 'jps-plugin' which IntelliJ
knows to compile and package as a dependency of the plugin. As such IntelliJ
will build this first and create a jar that is packaged. This is then specified
in the classpath of the compile server plugin.

### Project Import Provider

The project import functions are split into two extension points. The import
provider and the import builder. Both are needed for import functions, one
as it's name suggests is used to configure the import flow for the project type
and the other does the actual work of configuration of the project during import.

The import builder registers a number of wizard 'steps' which are used to
configure the project.

# Plugin Packaging

To support packaging of external builders IntelliJ uses an external service which
is passed the details of the files to build. The build is split into two parts

* jps-plugin : This is the external builder plugin which is called by IntelliJ
* IntelliJ Plugin : The plugin registers the external service and provides
  scoping etc setup for it.

The concept is that the external module can provide what ever compilation
functionality it needs and then once it has run it will




