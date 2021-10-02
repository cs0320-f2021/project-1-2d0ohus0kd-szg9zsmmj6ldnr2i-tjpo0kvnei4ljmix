package edu.brown.cs.student.main;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class kdTest {

    KdTree gkd = new KdTree(); //global kdTree used in some tests, tests reloading data

    //Test whether kdTree was constructed properly
    @Test
    public void testBasic() {
        KdTree k = new KdTree(inputX, inputY, inputZ)
        assertEquals(x, s.getInputX(), 0);
        assertEquals(y, s.getInputY(), 0);
        assertEquals(z, s.getInputZ(), 0);
    }

    public void testNearestNeighbors() {
        Star star1 = new Star(1, "first", 0, 0, 0);
        Star star2 = new Star(2, "second", 0, 0, 1);
        Star star3 = new Star(3, "third", 0, 0, 3);
        Star star4 = new Star(4, "fourth", 0, 0, 6);
        Star star5 = new Star(5, "fifth", 0, 0, 10);
        Star star6 = new Star(6, "sixth", 0, 0, 14);
        Star star7 = new Star(7, "seventh", 0, 0, 14);
        Star star8 = new Star(8, "eight", 0, 0, 9);
        Star star9 = new Star(9, "nine", 0, 0, 9);



        //straightforward case
        assertEquals(nearestNeighbors(2, star2),(star1, star3));

        //equal distance between n stars
        assertEquals(NearestNeighbors(2, star5),(star4, star6));

        //equal distance between >n stars
        assertEquals(NearestNeighbors(2, star4), (star3, star8),;

        //fewer than 'n' nodes in the tree --> return all nodes (except for the one inputted)
        assertEquals(NearestNeighbors(500, star4), (star1, star2, star3, star5, star6, star7, star8, star9);



    }
}
