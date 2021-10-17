package edu.brown.cs.student.main.commands;

import com.google.common.collect.Lists;
import edu.brown.cs.student.main.Command;
import recommender.Recommender;
import recommender.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RecsysGenGroups extends Command {

    Recommender recommender = Recommender.getInstance();
    List<Student> allStudents = recommender.getAllStudents();

    //TODO: call the recommender.bestStudents on each student to get each student's best partners
    // ^ Then, do some stable-marriage-problem algorithm to get the groups
    // ^ Eventually, return a STRING of all the best groups.
    // ^ Note that the Student class has a 'essentialInfoPrint' method that will just print the name and id of a student.

    //initialize a hashmap where the key is a studentId string, and value is a list of strings.
    //loop through each student and call bestStudents. For each student, this outputs a list of studentId strings.
    //in the for loops, each time a studentId string is visited, it is stored as a key, and its studentId list as value.

    /*
    Guiding questions:
    -What is each individual "ranking"?
    -Group size: n?
    -Stable marriage works with two sets (men and women). How do we emulate or generalize that?
     */


    public HashMap<String, List<Student>> studentMapBuilder(int numPerStudent) {
       HashMap<String, List<Student>> bestStudentMap;
       bestStudentMap = new HashMap<>();
       for (Student s : allStudents) {
         bestStudentMap.put(String.valueOf(s.id), recommender.bestStudents(s.id, numPerStudent));
      }
       return bestStudentMap;
    }

    //bestStudentMap maps a list<Student> of bestStudents as values for each studentId.

    public String groupGenerator(int groupSize) {
        //randomly shuffle all students
        Collections.shuffle(allStudents);
        //divide shuffled students into groupSize number of groups. return a list of sublists.
        for (List group : Lists.partition(allStudents, allStudents.size()/groupSize)) {
            

    }

    @Override
    public String run(String argString) {
        return null;
    }




    /*stable-marriage-problem algorithm:
    Instability is if BOTH partners would prefer some other partner.
    Each man + each woman rank the members of the opposite sex.
    Day 1: each woman proposes to her number 1 choice.
    Each man rejects all but his top suitor.

    Day 2: each rejected woman proposes to her next best choice.
    Each man rejects all but his top suitor (can even break the previous engagement. keep waiting for better offer).

    Day 3, 4, 5, ...., infinity:
    each rejected woman proposes to her next best choice.
    Each man rejects all but his top suitor (can even break the previous engagement. keep waiting for better offer).

    Once this algorithm stops, the final engagements are stable.





    -given N men and N women, where each person has ranked all members of the opposite sex in order of preference.

    -represented as 2d matrices, one matrix for men, one for women. sorted by highest to lowest pref for each person

    -given N men and N women, where each person has ranked all members of the opposite sex in order of preference,
     marry the men and women together such that there are no two people of oppose sex who would both rather have each
     other than their current partners.
     */



  }


}
