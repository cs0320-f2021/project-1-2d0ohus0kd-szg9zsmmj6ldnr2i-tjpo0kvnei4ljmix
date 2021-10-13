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
          return elm.id;
        }
      }
  );

  private Student randomStudent() {
    return new Student(rnd.nextInt(), "PlaceholderName", rnd.nextBoolean(), Grade.THIRDYEAR,
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
    String output = "";
    int numStudents = Integer.parseInt(argString);
    kdTree<Student> studentKD = new kdTree<>();
    studentKD.loadData(randomStudentList(numStudents), studentGetters);
    Student center = randomStudent();
    output += "Searching around" + center.yearsExperience + ", " + center.id + "\n";
    output += studentKD.nearestNeighbors(10, center);
    return output;
  }
}
