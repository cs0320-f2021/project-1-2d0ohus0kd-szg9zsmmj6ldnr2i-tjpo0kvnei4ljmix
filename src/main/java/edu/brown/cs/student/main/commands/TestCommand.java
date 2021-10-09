package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestCommand extends Command {

  public TestCommand() {
  }

  @Override
  public String run(String argString) {
    return "REPL is working!";
  }
}
