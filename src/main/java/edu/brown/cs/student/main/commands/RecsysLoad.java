package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import recommender.DummyStudentGetter;
import recommender.Recommender;
import recommender.Student;

import java.util.Collection;

public class RecsysLoad extends Command {

  public String run(String args) {
    Recommender studentsToRec = Recommender.getInstance();
    DummyStudentGetter studentGetter = new DummyStudentGetter();
    Collection<Student> students = studentGetter.getStudents();
    studentsToRec.add(students);
    int numStudents = students.size();
    return "Loaded " + numStudents + " Students";
  }

  public String help(){
    return "loads students into recommender: e.g. 'recsys_load responses'";
  }

}
