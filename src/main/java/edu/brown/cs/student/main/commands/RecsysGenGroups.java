package edu.brown.cs.student.main.commands;

import com.google.common.collect.Lists;
import edu.brown.cs.student.main.Command;
import recommender.Recommender;
import recommender.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecsysGenGroups extends Command {

    Recommender recommender = Recommender.getInstance();
    List<Student> allStudents = recommender.getAllStudents();

    //TODO: call the recommender.bestStudents on each student to get each student's best partners
    // ^ Then, do some stable-marriage-problem algorithm to get the groups
    // ^ Eventually, return a STRING of all the best groups.
    // ^ Note that the Student class has a 'essentialInfoPrint' method that will just print the name and id of a student.


    //return bestStudentMap, which maps a list<Student> of bestStudents as values for each studentId.
    public HashMap<String, List<Student>> studentMapBuilder(int numPerStudent) {
       HashMap<String, List<Student>> bestStudentMap;
       bestStudentMap = new HashMap<>();
       for (Student s : allStudents) {
         bestStudentMap.put(String.valueOf(s.id), recommender.bestStudents(s.id, numPerStudent));
      }
       return bestStudentMap;
    }


    public String groupGenerator(int groupSize) {
        //using studentMapBuilder, generate a hashmap for allStudents, w/ groupSize - 1 ranked students per student.
        HashMap<String, List<Student>> bestStudentMap;
        bestStudentMap = studentMapBuilder(groupSize - 1);

        //randomly shuffle all students
        Collections.shuffle(allStudents);
        //divide shuffled students into groupSize number of groups. return a list of sublists.

        //partition allStudents into sublists
        List<List<Student>> partitionedList = Lists.partition(allStudents, allStudents.size() / groupSize);

        //define the first sublist as the proposing sublist
        List<Student> proposingStudents = partitionedList.get(0);


        //iterate over proposingStudents, create groups comprising the proposing students and their bestStudents.
        //return in String form.

        String idealGroups = null;
        for (Student s : proposingStudents) {
            //create a group with student s and their bestStudent list
            ArrayList<Student> groupCreated;
            groupCreated = new ArrayList<Student>();
            groupCreated.add(s);
            //create a list including each student and their respective bestStudents, iteratively.
            idealGroups = Stream.of(groupCreated, bestStudentMap.get(s)).flatMap(x -> x.stream()).collect(Collectors.toList()).toString();
        }

        return idealGroups;
    }

    @Override
    public String run(String argString) {
      int groupsize;
      try{
        groupsize = Integer.parseInt(argString);
      } catch (NumberFormatException e) {
        return "Could not convert: " + argString + " to a number";
      }
        return groupGenerator(groupsize);
    }
}
