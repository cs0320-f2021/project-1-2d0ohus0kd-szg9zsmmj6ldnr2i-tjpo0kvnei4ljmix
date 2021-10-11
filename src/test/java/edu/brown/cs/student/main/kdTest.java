package edu.brown.cs.student.main;

import kdtree.kdGetter;
import kdtree.kdTree;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class kdTest {

    //global kdTree used in some tests, tests reloading data
    kdTree gkd = new kdTree();


    //generate an ArrayList with numToGenerate stars
    private ArrayList<Star> generateStars(int numToGenerate) {
        ArrayList<Star> starList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < numToGenerate; i++) {
            Star newStar = new Star(i, "StarNumber: " + i, r.nextDouble() * 1000, r.nextDouble() * 1000, r.nextDouble() * 1000);
            starList.add(newStar);
        }
        return starList;
    }
    //generateStars(100)


    @Test
    public void testBasic() {
        ArrayList<Star> starList = new ArrayList<>();
        generateStars(1);
//        System.out.println(starList);

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

        //from star-1 to start10, star10 is the furthest star from 0,0,0 because the values get normalized
        Star star10 = new Star(10, "nine", 1, 0, 1);

        ArrayList<Star> starList = new ArrayList<>(
            Arrays.asList(star1, star2, star3, star4, star5, star6, star7, star8, star9, star10));

        //a lit of getters, one for each coordinate (x, y, z)
        ArrayList<kdGetter<Star>> starGetters = new ArrayList<>();
        kdGetter<Star> xGetter = new kdGetter<Star>() {
            @Override
            public double getValue(Star elm) {
                return elm.getX();
            }
        };

        kdGetter<Star> yGetter = new kdGetter<Star>() {
            @Override
            public double getValue(Star elm) {
                return elm.getY();
            }
        };

        kdGetter<Star> zGetter = new kdGetter<Star>() {
            @Override
            public double getValue(Star elm) {
                return elm.getZ();
            }
        };


        //adding each coordinate getter to the starGetters list.
        starGetters.add(xGetter);
        starGetters.add(yGetter);
        starGetters.add(zGetter);

        //loading a starList and starGetters to our global kdTree
        gkd.loadData(starList, starGetters);

        //I want to search around 50,50,50
        assertEquals(gkd.nearestNeighbors(4, new Star(0 ,"", 50, 50, 50)),star10);

        //straightforward case
        assertEquals(gkd.nearestNeighbors(2, star2),Arrays.asList(star2, star1));

        //equal distance between n stars
        assertEquals(gkd.nearestNeighbors(2, star5),Arrays.asList(star5, star4));

        //equal distance between >n stars
        assertEquals(gkd.nearestNeighbors(2, star4), Arrays.asList(star4, star3));

        //fewer than 'n' nodes in the tree --> return all nodes (including the one inputted)
        assertEquals(gkd.nearestNeighbors(500, star4), Arrays.asList(star1, star2, star3, star4, star5, star6, star7, star8, star9));

        //0 nodes
        assertEquals(gkd.nearestNeighborsExcludingCenter(0, star4), Arrays.asList());
        //non-existent coordinates -->
        assertEquals(gkd.nearestNeighborsExcludingCenter(2, new Star(11, "not in tree", 0, 0, 1000)), Arrays.asList(star7,star6));

        generateStars(1000000000);
        System.out.println(starList);
//        gkd.loadData(starList, starGetters);
    }

        public void testNearestNeighborsExcludingCenter() {
            Star star1 = new Star(1, "first", 0, 0, 0);
            Star star2 = new Star(2, "second", 0, 0, 1);
            Star star3 = new Star(3, "third", 0, 0, 3);
            Star star4 = new Star(4, "fourth", 0, 0, 6);
            Star star5 = new Star(5, "fifth", 0, 0, 10);
            Star star6 = new Star(6, "sixth", 0, 0, 14);
            Star star7 = new Star(7, "seventh", 0, 0, 14);
            Star star8 = new Star(8, "eight", 0, 0, 9);
            Star star9 = new Star(9, "nine", 0, 0, 9);
            //This is the furthest star from 0,0,0 because the values get normalized

            Star star10 = new Star(10, "nine", 1, 0, 1);

            //Comment: Getters and Setters (e.g. elm.age). instead of returning the reference of class-level objects,
            //return copies of them.
            //Comment: Replace ArrayList with List on the left side of these equations (as seen below, change).

            ArrayList<Star> starList = new ArrayList<>(
                    Arrays.asList(star1, star2, star3, star4, star5, star6, star7, star8, star9, star10));

            ArrayList<kdGetter<Star>> starGetters = new ArrayList<>();
            kdGetter<Star> xGetter = new kdGetter<Star>() {
                @Override
                public double getValue(Star elm) {
                    return elm.getX();
                }
            };

            kdGetter<Star> yGetter = new kdGetter<Star>() {
                @Override
                public double getValue(Star elm) {
                    return elm.getY();
                }
            };

            kdGetter<Star> zGetter = new kdGetter<Star>() {
                @Override
                public double getValue(Star elm) {
                    return elm.getZ();
                }
            };

            starGetters.add(xGetter);
            starGetters.add(yGetter);
            starGetters.add(zGetter);

            gkd.loadData(starList, starGetters);

            //I want to search around 50,50,50
            gkd.nearestNeighbors(4, new Star(0 ,"", 50, 50, 50));
            //straightforward case
            assertEquals(gkd.nearestNeighborsExcludingCenter(2, star2), Arrays.asList(star1, star3));

            //equal distance between n stars
            assertEquals(gkd.nearestNeighborsExcludingCenter(2, star5),Arrays.asList(star4, star6));

            //equal distance between >n stars
            assertEquals(gkd.nearestNeighborsExcludingCenter(2, star4), Arrays.asList(star3, star8)),;

            //fewer than 'n' nodes in the tree --> return all nodes (except for the one inputted)
            assertEquals(gkd.nearestNeighborsExcludingCenter(500, star4), Arrays.asList(star1, star2, star3, star5, star6, star7, star8, star9));

            //0 nodes
            assertEquals(gkd.nearestNeighborsExcludingCenter(0, star4), Arrays.asList());

            //non-existent coordinates -->
            assertEquals(gkd.nearestNeighborsExcludingCenter(2, new Star(10, "not in tree", 1, 1, 1)), Arrays.asList(star10));

        }
//        public void testGetAll() {
//
//        //for empty kdtree
//            assertEquals();

}
