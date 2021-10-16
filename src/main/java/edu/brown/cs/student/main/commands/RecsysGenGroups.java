package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import recommender.Recommender;
import recommender.Student;

import java.util.List;

public class RecsysGenGroups extends Command {
  @Override
  public String run(String argString) {
    Recommender recommender = Recommender.getInstance();
    List<Student> allStudents = recommender.getAllStudents();

    //TODO: call the recommender.bestStudents on each student to get each student's best partners
    // ^ Then, do some stable-marriage-problem algorithm to get the groups
    // ^ Eventually, return a STRING of all the best groups.
    // ^ Note that the Student class has a 'essentialInfoPrint' method that will just print the name and id of a student.

    return null;
  }
}
