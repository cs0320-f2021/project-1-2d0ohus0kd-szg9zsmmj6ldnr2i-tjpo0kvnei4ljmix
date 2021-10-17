package recommender;

import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import kdtree.Pair;
import kdtree.kdGetter;
import kdtree.kdTree;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommender implements RecommenderInterface{
  private List<Student> allStudents = new ArrayList<>();
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
      //TODO bloomfilterrec.setBloomFilterComparator();
    }
    //Load into kdTree
    kdt.loadData(studentsToAdd, studentGetters, true);
    //Load into Bloom Filter
    bloomfilterrec = new BloomFilterRecommender<Student>(studentMap, 0.01);

  }

  @Override
  public List<Student> bestStudents(int studentID, int k) {
    if (k >= this.numStudents) {
      k = this.numStudents - 1; //Subtract one because the target can't be a result
    }
    /*
    Overall plan: Get recommendations from three sources (kdTree, Bloom Filter, edit distance)
    All three sources are weighted equally
    combine the scores from all three sources to get a final recommendation
     */

    Student target = studentMap.get(String.valueOf(studentID));
    //Get recommendations from kdTree
    List<Student> kdRecommendations = this.kdt.nearestNeighbors(k + 1, target);
    kdRecommendations.remove(target);
    //Get recommendations from Bloom Filter
    List<Student> bloomRecommendations = kdRecommendations; //TODO remove this

    // TODO Uncomment: List<Student> bloomRecommendations = this.bloomfilterrec.getTopKRecommendations(target, k);

    assert(kdRecommendations.size() == bloomRecommendations.size());

    Map<Student, Integer> studentRankings = new HashMap<>();

    //Add students to the ranking list with their scores
    for (int i = 0; i < kdRecommendations.size(); i++) {
      Student currKDStudent = kdRecommendations.get(i);
      Student currBloomStudent = bloomRecommendations.get(i);

      int score = kdRecommendations.size() - i;

      studentRankings.put(currKDStudent, studentRankings.getOrDefault(currKDStudent, 0) + score);
      studentRankings.put(currBloomStudent, studentRankings.getOrDefault(currBloomStudent, 0) + score);
    }

    //If the target student wants to be matched with a similar marginalized group, factor in string similarity
    JaroWinklerSimilarity jwSimilarity = new JaroWinklerSimilarity();
    if (target.preferGroup) {
      Set<Student> studentKeys = studentRankings.keySet();
      for (Student s : studentKeys) {
        Double groupSimilarity = jwSimilarity.apply(target.getMarginalizedGroup(), s.getMarginalizedGroup());
        //jwSimilarity is 0 for identical, 1 for completely different. Flip this so it makes sense
        groupSimilarity = 1 - groupSimilarity;
        groupSimilarity = groupSimilarity * kdRecommendations.size(); //Scale to match the other scores
        studentRankings.put(s, studentRankings.get(s) + groupSimilarity.intValue());
      }
    }

    //Finally, we have a hashmap with students as keys, and scores as values
    //Get a list of the keys, and sort!
    List<Student> finalRecommendation = new ArrayList<>(studentRankings.keySet());
    finalRecommendation.sort(new Comparator<Student>() {
      @Override
      public int compare(Student o1, Student o2) {
        int o1Rank = studentRankings.get(o1);
        int o2Rank = studentRankings.get(o2);
        if (o1Rank < o2Rank) return -1;
        if (o1Rank > o2Rank) return 1;
        return 0; //They're equal
      }
    });
    //Need to take sublist again because bloom and kd will not return same subset of students
    return finalRecommendation.subList(0,k);
  }

  @Override
  public List<Student> getAllStudents() {
    return allStudents;
  }
}
