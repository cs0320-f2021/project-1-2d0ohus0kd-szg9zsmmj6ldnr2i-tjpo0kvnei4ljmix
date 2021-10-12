package edu.brown.cs.student.main;

import api.APIenum;
import api.apiImplementation;
import api.core.Justuser;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class apiImplementationTest {
    apiImplementation api = new apiImplementation();

    @Test
    public void testJsonUsers() {
        //Collection<Justuser> userResult = api.getFromJson(APIenum.USER, "data/project-1/justusersSMALL.json");
        //assertEquals(userResult.size(), 15); //Make sure the length is 15
        //It would take a long time to manually test all users, but let's test the first one at least
        // (might be useful to test a few more from the middle/end
        //Justuser userOne = new Justuser();
        /*
        THIS CURRENTLY CAUSES AN ERROR BECAUSE THE JUSTUSER CLASS STORES EVERYTHING AS A STRING
        WHEN RUIHAN FIXES THE CLASS, THEN THIS CAN BE UNCOMMENTED
        IT'S HERE AS AN EXAMPLE OF HOW TO TEST THAT A CERTAIN THING EXISTS IN A LIST
        userOne.setUser_id(151944);
        userOne.setWeight(145);
        userOne.setBust_size("34b");
        userOne.setHeight(69); //69 inches -- may change depending on the unit Ruihan uses for height
        userOne.setAge(27);
        userOne.setBody_type("athletic");
        userOne.setHoroscope("Libra");

        assertTrue(userResult.contains(new Justuser()));
         */
    }
}