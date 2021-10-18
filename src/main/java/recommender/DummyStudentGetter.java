package recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class DummyStudentGetter implements StudentGetter{

  private Student randomStudent() {
    Random rnd = new Random();
    return new Student(rnd.nextInt(1000), "Nim Telson", rnd.nextBoolean(), Grade.valueOf(rnd.nextInt(7)),
        rnd.nextInt(10), Horoscope.PISCES, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.EVENING),
        "Python", "", false, rnd.nextInt(10), rnd.nextInt(10),
        rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10),
        List.of("Positive stuff"), List.of("Negative stuff"), List.of("Interests"));
  }

  @Override
  public Collection<Student> getStudents() {
    //Just return a collection of a few random students for testing
    int NUM_STUDENTS = 100;
    List<Student> studentList = new ArrayList<>();
    for (int i = 0; i < NUM_STUDENTS; i++) {
      studentList.add(randomStudent());
    }

    studentList.add(new Student(1001, "Nim Telson", true, Grade.SECONDYEAR, 6,
          Horoscope.PISCES, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.EVENING),
          "Python", "", false, 1, 2,
          3, 4, 5, 6,
          List.of("Positive stuff"), List.of("Negative stuff"), List.of("Interests"))
    );

    return studentList;
  }
}
