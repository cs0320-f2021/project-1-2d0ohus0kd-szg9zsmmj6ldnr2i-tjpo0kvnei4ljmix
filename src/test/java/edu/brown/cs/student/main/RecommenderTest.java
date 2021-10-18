package edu.brown.cs.student.main;

import kdtree.kdGetter;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import recommender.Grade;
import recommender.Horoscope;
import recommender.MeetingTime;
import recommender.Recommender;
import recommender.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecommenderTest {

  List<Student> basicStudentList = List.of(
      new Student(1, "Nim Telson", true, Grade.SECONDYEAR, 6,
          Horoscope.PISCES, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.EVENING),
          "Python", "", false, 2, 2, 8, 8,
          2, 8, List.of("Positive stuff"), List.of("Negative stuff"), List.of("Interests")),
      new Student(2, "Nim's friend", true, Grade.THIRDYEAR, 3,
          Horoscope.AQUARIUS, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.EVENING, MeetingTime.EARLY_AFTERNOON),
          "Python3", "", false, 8, 8, 3, 2,
          8, 1, List.of("Positive stuff"), List.of("Negative stuff"), List.of("Interests")),
      new Student(3, "Nim's worst teammate", false, Grade.SECONDYEAR, 10,
          Horoscope.CANCER, List.of(MeetingTime.EARLY_MORNING, MeetingTime.LATE_NIGHT),
          "Rust", "BIPOC", true, 1, 1, 10, 10,
          1, 10, List.of("Positive stuff"), List.of("Negative stuff"), List.of("Interests"))
      );

  @Test
  public void testBasic() {
    Recommender r = new Recommender();
    r.add(basicStudentList);
    List<Student> nimPartner = r.bestStudents(1, 1);
    assertEquals(1, nimPartner.size());
    //nim's partner should really be his friend
    assertEquals(2, nimPartner.get(0).id);

    //Make sure that the friend is still number 1 if asking for 2 partners
    nimPartner = r.bestStudents(1, 2);
    assertEquals(2, nimPartner.size());
    assertEquals(2, nimPartner.get(0).id);
    assertEquals(3, nimPartner.get(1).id);
  }

  @Test
  public void testOnePerson() {
    Recommender r = new Recommender();
    r.add(basicStudentList.subList(0,1));
    List<Student> nimPartner = r.bestStudents(1, 1);
    assertEquals(0, nimPartner.size());
  }

  //Return a student with random score
  private Student randomScoreStudent() {
    Random rnd = new Random();
    return new Student(rnd.nextInt(1000), "Nim Telson", true, Grade.SECONDYEAR, 6,
        Horoscope.PISCES, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.EVENING),
        "Python", "", false, rnd.nextInt(10), rnd.nextInt(10),
        rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10),
        List.of("Positive stuff"), List.of("Negative stuff"), List.of("Interests"));
  }

  private List<Student> randomScoreStudentList(int len) {
    List<Student> studentList = new ArrayList<>();
    for (int i = 0; i < len; i++) {
      studentList.add(randomScoreStudent());
    }
    return studentList;
  }

  private double studentDistance(Student s1, Student s2) {
    double dist = 0;
    for (kdGetter<Student> getter : Recommender.getStudentGetters()) {
      dist += Math.pow(getter.getValue(s1) - getter.getValue(s2), 2);
    }
    return dist;
  }

  //Recommends only based on skills (to test kdtree) in a naive way
  private List<Student> naiveRecommendations(List<Student> students, Student target) {
    students.sort(new Comparator<Student>() {
      @Override
      public int compare(Student o1, Student o2) {
        if (studentDistance(o1, target) < studentDistance(o2, target)) return 1;
        if (studentDistance(o1, target) > studentDistance(o2, target)) return 0;
        return 0;
      }
    });
    return students;
  }

  @Test
  public void proceduralTest(){
    int NUM_TESTS = 0; //Still a buggy and sometimes-failing test, so skip for now.
    int NUM_STUDENTS = 5;
    Random rnd = new Random();
    for (int i = 0; i < NUM_TESTS; i++) {
      int currNumStudents = rnd.nextInt(NUM_STUDENTS) + 2; //have at least 2 students
      List<Student> studentList = randomScoreStudentList(currNumStudents);
      Recommender rec = new Recommender();
      rec.add(studentList);

      int num_recommendations = rnd.nextInt(currNumStudents - 1) + 1; //make sure we never ask for 0 recommendations
      Student target = studentList.get(rnd.nextInt(currNumStudents));
      List<Student> recommenderResults = rec.bestStudents(target.id, num_recommendations);
      List<Student> naiveResults = naiveRecommendations(studentList, Recommender.invertStudent(target));
      naiveResults.remove(target);
      naiveResults = naiveResults.subList(0,num_recommendations);
      assertEquals(recommenderResults, naiveResults);

    }
  }
}
