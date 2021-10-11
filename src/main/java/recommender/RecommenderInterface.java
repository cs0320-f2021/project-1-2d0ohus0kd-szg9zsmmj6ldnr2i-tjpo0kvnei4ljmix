package recommender;

import java.util.Collection;
import java.util.List;

public interface RecommenderInterface {
  /**
   *
   * @param studentsToAdd students to add to the recommender
   */
  public void add(Collection<Student> studentsToAdd);

  /**
   *
   * @param studentID student to find recommendations for
   * @param k number of recommendations to provide
   * @return List of the best pairings for the given student (sorted from best to worst)
   */
  public List<Student> bestStudents(int studentID, int k);
}
