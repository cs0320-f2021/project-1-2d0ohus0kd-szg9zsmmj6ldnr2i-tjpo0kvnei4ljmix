package recommender;

import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import kdtree.kdGetter;
import kdtree.kdTree;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommender implements RecommenderInterface{
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
  private BloomFilterRecommender<Student> bloomfilterrec;
  private kdTree<Student> kdt;
  private HashMap<String, Student> studentMap;


  @Override
  public void add(Collection<Student> studentsToAdd) {
    studentMap = new HashMap<>();
    kdt = new kdTree<Student>();
    //Convert to map:
    for (Student s : studentsToAdd) {
      studentMap.put(String.valueOf(s.id), s); //Putting string because of Bloom Filter requirement
    }
    //Load into kdTree
    kdt.loadData(studentsToAdd, studentGetters, true);
    //Load into Bloom Filter
    bloomfilterrec = new BloomFilterRecommender<Student>(studentMap, 0.05);

  }

  @Override
  public List<Student> bestStudents(int studentID, int k) {
    return kdt.nearestNeighbors(k, studentMap.get(String.valueOf(studentID)));
  }
}
