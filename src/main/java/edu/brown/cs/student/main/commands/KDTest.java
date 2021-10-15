package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import kdtree.kdGetter;
import kdtree.kdTree;
import recommender.Grade;
import recommender.Horoscope;
import recommender.MeetingTime;
import recommender.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTest extends Command {
  private Random rnd = new Random();
  private List<kdGetter<Student>> studentGetters = List.of(
      new kdGetter<Student>() {
        @Override
        public double getValue(Student elm) {
          return elm.yearsExperience;
        }
      },
      new kdGetter<Student>() {
        @Override
        public double getValue(Student elm) {
          return elm.grade.getValue();
        }
      }
  );

  private Student randomStudent() {
    return new Student(rnd.nextInt(), "PlaceholderName", rnd.nextBoolean(), Grade.valueOf(rnd.nextInt(7)),
        rnd.nextInt(12), Horoscope.CANCER, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.LATE_NIGHT),
        "English lol", "N/A", rnd.nextBoolean(), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10),
        rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10), List.of("Coding"), List.of("Testing"), List.of("Thinking"));
  }

  private List<Student> randomStudentList(int n) {
    List<Student> studentList = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      studentList.add(randomStudent());
    }
    return studentList;
  }

  @Override
  public String run(String argString) {
    int numStudents = 0;
    try {
      numStudents = Integer.parseInt(argString);
    } catch (NumberFormatException e) {
      numStudents = 12;
    }
    kdTree<Student> studentKD = new kdTree<>();
    List<Student> rsl = randomStudentList(numStudents);
    studentKD.loadData(rsl, studentGetters);
    for (Student s : rsl) {
      System.out.println(s.yearsExperience + ", " + s.grade.getValue());
    }
    //Student center = randomStudent();
    //System.out.println("Searching around" + center.yearsExperience + ", " + center.grade.getValue());
    //System.out.println("---");
    System.out.println(studentKD);
    //System.out.println(studentKD.nearestNeighbors(10, center));
    return "";
  }
}
