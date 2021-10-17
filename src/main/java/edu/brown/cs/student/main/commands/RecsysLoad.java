package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import recommender.DummyStudentGetter;
import recommender.Recommender;
import recommender.Student;
import recommender.StudentGetterImplementation;

import java.util.Collection;

public class RecsysLoad extends Command {

  public String run(String args) {
    //Currently, loads students regardless of args
    Recommender studentsToRec = Recommender.getInstance();
    //DummyStudentGetter studentGetter = new DummyStudentGetter();
    StudentGetterImplementation studentGetter = new StudentGetterImplementation();
    Collection<Student> students = studentGetter.getStudents();
    if (students == null) {
      return "ERROR: Students list was null";
    }
    for (Student s : students) {
      System.out.print(s);
    }
    studentsToRec.add(students);
    int numStudents = students.size();
    return "Loaded " + numStudents + " Students";
  }

  public String help(){
    return "loads students into recommender: e.g. 'recsys_load responses'";
  }

}
