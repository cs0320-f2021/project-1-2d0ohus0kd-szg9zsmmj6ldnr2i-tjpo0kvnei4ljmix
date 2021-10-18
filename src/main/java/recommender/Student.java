package recommender;

import bloomfilter.Item;

import java.util.List;

public class Student implements Item {
  public final int id;
  private final String name;
  public final boolean meetsInPerson;
  public final Grade grade; //can ONLY be one of the grade options
  public final int yearsExperience;
  public final Horoscope horoscope;
  private final List<MeetingTime> meetingTimeList;
  private final String preferredLanguage;
  private final String marginalizedGroup; //Maybe pair people by string similarity?
  public final boolean preferGroup;
  public final int commentingScore;
  public final int testingScore;
  public final int oopScore;
  public final int algorithmsScore;
  public final int teamworkScore;
  public final int frontendScore;
  private final List<String> positiveTraits;
  private final List<String> negativeTraits;
  private final List<String> interests;


  /*
  How fields will be used for recommendations:
  id -> unused
  name -> unused
  meetsInPerson -> bloomFilter
  ^ Find people who are the same
  grade -> kdTree
  yearsExperience -> kdTree because there's a large range of possible values
  ^ Find people who are different
  horoscope -> unused
  meetingTimeList -> Bloom Filter (or kdTree)
  ^ Find people who are the same
  preferredLanguage -> unused
  marginalizedGroup -> Bloom Filter
  ^ Find people who are similar if the student is perferGroup (otherwise ignore)
   */

  public String essentialInfoPrint() {
    return "Student Name: " + this.name + ", ID: " + this.id;
  }

  public String getName() {
    return name;
  }

  public List<MeetingTime> getMeetingTimeList() {
    return meetingTimeList;
  }

  public String getPreferredLanguage() {
    return preferredLanguage;
  }

  public String getMarginalizedGroup() {
    return marginalizedGroup;
  }

  public Student(int id, String name, boolean meetsInPerson, Grade grade, int yearsExperience,
                 Horoscope horoscope, List<MeetingTime> meetingTimeList,
                 String preferredLanguage, String marginalizedGroup, boolean preferGroup,
                 int commentingScore, int testingScore, int oopScore, int algorithmsScore,
                 int teamworkScore, int frontendScore,
                 List<String> positiveTraits, List<String> negativeTraits,
                 List<String> interests) {
    this.id = id;
    this.name = name;
    this.meetsInPerson = meetsInPerson;
    this.grade = grade;
    this.yearsExperience = yearsExperience;
    this.horoscope = horoscope;
    this.meetingTimeList = meetingTimeList;
    this.preferredLanguage = preferredLanguage;
    this.marginalizedGroup = marginalizedGroup;
    this.preferGroup = preferGroup;
    this.commentingScore = commentingScore;
    this.testingScore = testingScore;
    this.oopScore = oopScore;
    this.algorithmsScore = algorithmsScore;
    this.teamworkScore = teamworkScore;
    this.frontendScore = frontendScore;
    this.positiveTraits = positiveTraits;
    this.negativeTraits = negativeTraits;
    this.interests = interests;
  }

  @Override
  public List<String> getVectorRepresentation() {
    //Return all the fields we want to go into the bloom filter
    return List.of(
        String.valueOf(this.meetsInPerson),
        String.valueOf(this.meetingTimeList),
        String.valueOf(this.marginalizedGroup)
    );
  }

  @Override
  public String getId() {
    return String.valueOf(this.id);
  }

  public List<String> getPositiveTraits() {
    return positiveTraits;
  }

  public List<String> getNegativeTraits() {
    return negativeTraits;
  }

  public List<String> getInterests() {
    return interests;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", meetsInPerson=" + meetsInPerson +
        ", grade=" + grade +
        ", yearsExperience=" + yearsExperience +
        ", horoscope=" + horoscope +
        ", meetingTimeList=" + meetingTimeList +
        ", preferredLanguage='" + preferredLanguage + '\'' +
        ", marginalizedGroup='" + marginalizedGroup + '\'' +
        ", preferGroup=" + preferGroup +
        ", commentingScore=" + commentingScore +
        ", testingScore=" + testingScore +
        ", oopScore=" + oopScore +
        ", algorithmsScore=" + algorithmsScore +
        ", teamworkScore=" + teamworkScore +
        ", frontendScore=" + frontendScore +
        ", positiveTraits=" + positiveTraits +
        ", negativeTraits=" + negativeTraits +
        ", interests=" + interests +
        '}';
  }
}
