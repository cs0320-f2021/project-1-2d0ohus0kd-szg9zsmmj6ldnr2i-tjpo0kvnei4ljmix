package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import recommender.DummyStudentGetter;
import recommender.Recommender;
import recommender.RecommenderInterface;
import recommender.Student;
import recommender.StudentGetter;

import java.util.Collection;
import java.util.List;

public class RecsysloadResponses extends Command implements StudentGetter, RecommenderInterface {
    /*
    * Recsys_load should use StudentGetter to get a list of students, and should pass that list of students
    * to our Recommender.
    * */


    @Override
    public String run(String argString) {
        return null;
    }

    @Override
    public Collection<Student> getStudents() {
        return null;
    }


    @Override
    public void add(Collection<Student> studentsToAdd) {

    }



    @Override
    public List<Student> bestStudents(int studentID, int k) {
        return null;
    }

    public Recommender studentsToRecommender() {
        Recommender studentsToRec = new Recommender();
        DummyStudentGetter studentGetter = new DummyStudentGetter();
        studentsToRec.add(studentGetter.getStudents());
        return studentsToRec;
    }
}
