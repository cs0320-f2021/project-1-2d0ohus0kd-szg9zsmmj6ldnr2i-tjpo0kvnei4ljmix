package recommender;

import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import edu.brown.cs.student.main.StarFinderNaive;
import kdtree.kdGetter;
import kdtree.kdTree;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommender implements RecommenderInterface{
  private List<Student> allStudents;
  private static Recommender instance = null;

  public static Recommender getInstance() {
    if (instance == null) {
      instance = new Recommender();
    }
    return instance;
  }

  private final List<kdGetter<Student>> studentGetters = List.of(
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
  private BloomFilterRecommender<Student> bloomfilterrec;
  private kdTree<Student> kdt = new kdTree<>();
  private HashMap<String, Student> studentMap = new HashMap<>();
  private int numStudents = 0;


  @Override
  public void add(Collection<Student> studentsToAdd) {
    //Convert to map:
    for (Student s : studentsToAdd) {
      this.allStudents.add(s);
      studentMap.put(String.valueOf(s.id), s); //Putting string because of Bloom Filter requirement
    }
    //Load into kdTree
    kdt.loadData(studentsToAdd, studentGetters, true);
    //Load into Bloom Filter
    bloomfilterrec = new BloomFilterRecommender<Student>(studentMap, 0.01);

  }

  @Override
  public List<Student> bestStudents(int studentID, int k) {
    if (k > this.numStudents) {
      k = this.numStudents;
    }
    return kdt.nearestNeighbors(k, studentMap.get(String.valueOf(studentID)));
  }

  @Override
  public List<Student> getAllStudents() {
    return allStudents;
  }
}
