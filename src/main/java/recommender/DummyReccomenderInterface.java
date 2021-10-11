package recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DummyReccomenderInterface implements RecommenderInterface{
  List<Student> students = new ArrayList<>();

  @Override
  public void add(Collection<Student> studentsToAdd) {
    this.students.addAll(studentsToAdd);
  }

  @Override
  public List<Student> bestStudents(int studentID, int k) {
    return this.students.subList(0,k);
  }
}
