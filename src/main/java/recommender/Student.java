package recommender;

import java.util.List;

public class Student {
  public final int id;
  private String name;
  public final boolean meetsInPerson;
  public final Grade grade; //can ONLY be one of the grade options
  public final int yearsExperience;
  public final Horoscope horoscope;
  private List<MeetingTime> meetingTimeList;
  private String preferredLanguage;
  private String marginalizedGroup; //Maybe pair people by string similarity?
  public final boolean preferGroup;

  /*
  How fields will be used for recommendations:
  id -> unused
  name -> unused
  meetsInPerson -> kdTree
  ^ Find people who are the same
  grade -> Bloom Filter, only a few options so it's categorical
  ^ Find people who are different
  yearsExperience -> kdTree because there's a large range of possible values
  ^ Find people who are different
  horoscope -> unused
  meetingTimeList -> Bloom Filter (or kdTree)
  ^ Find people who are the same
  preferredLanguage -> unused
  marginalizedGroup -> Bloom Filter
  ^ Find people who are similar if the student is perferGroup (otherwise ignore)
   */

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
        '}';
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
                 String preferredLanguage, String marginalizedGroup, boolean preferGroup) {
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
  }
}
