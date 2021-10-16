package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import recommender.Recommender;
import recommender.Student;

import java.util.List;

public class RecsysRec extends Command {

  @Override
  public String run(String argString) {
    //parse argString into k and studentID
    String[] args = argString.split(" ");
    if (args.length != 2) {
      //bad number of args
      return "Invalid number of arguments! Usage: recsys_rec <num_students> <student_id>";
    }
    int k;
    int studentID;
    try{
      k = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      return "Could not translate " + args[0] + " to a number";
    }
    try{
      studentID = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      return "Could not translate " + args[1] + " to a number";
    }

    Recommender recommender = Recommender.getInstance();
    List<Student> recommendations = recommender.bestStudents(studentID, k);

    String output = "Best Students for ID " + studentID;
    for (Student s : recommendations) {
      output = output + s.essentialInfoPrint() + "\n";
    }
    return output;
  }

}
