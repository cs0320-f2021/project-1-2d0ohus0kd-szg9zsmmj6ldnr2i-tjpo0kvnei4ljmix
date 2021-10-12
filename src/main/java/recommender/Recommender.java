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


  @Override
  public void add(Collection<Student> studentsToAdd) {
    //Load into kdTree
    kdt = new kdTree<Student>();
    kdt.loadData(studentsToAdd, studentGetters, true);
    //Convert to Hashmap for bloom filter
    Map<String, Student> StudentMap = new HashMap<>();
    for (Student s : studentsToAdd) {
      StudentMap.put(String.valueOf(s.id), s);
    }
  }

  @Override
  public List<Student> bestStudents(int studentID, int k) {
    return null;
  }
}
