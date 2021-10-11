package recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DummyStudentGetter implements StudentGetter{

  @Override
  public Collection<Student> getStudents() {
    //Just return a collection of a few random students for testing
    List<Student> studentList = new ArrayList<>(List.of(
        new Student(123, "Nim Telson", true, Grade.SECONDYEAR, 6,
           Horoscope.PISCES, List.of(MeetingTime.LATE_AFTERNOON, MeetingTime.EVENING),
            "Python", "BIPOC", false),
        new Student(456, "Nimothy Telegraph", false, Grade.GRAD, 3,
           Horoscope.AIRES, List.of(MeetingTime.EARLY_AFTERNOON, MeetingTime.LATE_MORNING),
            "Brainfuck", "N/A", false),
        new Student(789, "Nimuacious Timgrand", true, Grade.FIRSTYEAR, 0,
            Horoscope.TAURUS, List.of(MeetingTime.LATE_NIGHT),
            "Java", "LGBTQ", true)
    ));
    return studentList;
  }
}
