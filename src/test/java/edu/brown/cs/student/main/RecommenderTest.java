package edu.brown.cs.student.main;

import org.junit.Test;
import recommender.Grade;
import recommender.Horoscope;
import recommender.MeetingTime;
import recommender.Recommender;
import recommender.Student;

import java.util.List;

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
}
