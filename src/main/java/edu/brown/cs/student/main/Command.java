package edu.brown.cs.student.main;

import java.util.Set;

/**
 * Abstract class which every REPL command must extend
 * the run method is called when the command is invoked
 * after command invocation, the isActive method is polled
 * if isActive, then subsequent user input will be routed to run as well. (unless user types 'quit')
 * (it may be useful to implement some state-machine-like logic for complex commands)
 */

public abstract class Command {
  private boolean active = false;

  public Command() {
  }

  public boolean isActive() {
    return this.active;
  }

  public void deactivate() {
    this.active = false;
  }

  /**
   * @param argString the entire user-inputted string except for the command
   *
   * @return output to print to the user
   */
  public abstract String run(String argString);

  /**
   *
   * @return Help documentation for the given command
   */
  public String help() {
    return "This command has no help documentation";
  }
}
