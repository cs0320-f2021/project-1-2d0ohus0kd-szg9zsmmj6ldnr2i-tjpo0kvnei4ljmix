package edu.brown.cs.student.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestCommand extends Command{

  public TestCommand() {
    super(new HashSet<String>(Arrays.asList("test")));
  }

  @Override
  String run(String argString) {
    return "REPL is working!";
  }
}
