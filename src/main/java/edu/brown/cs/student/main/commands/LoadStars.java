package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import edu.brown.cs.student.main.StarFinderNaive;

import java.util.Arrays;
import java.util.HashSet;

public class LoadStars extends Command {

  public LoadStars() {
  }

  public String run(String arg) {
    return StarFinderNaive.getInstance().loadStars(arg);
  }

}
