package edu.brown.cs.student.main;

import kdtree.kdGetter;
import kdtree.kdTree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class kdTreeTest {

    //global kdTree used in some tests, tests reloading data
    kdTree<Star> gkd = new kdTree<Star>();
    Star origin = new Star(0, "OriginStar", 0, 0, 0);

    List<kdGetter<Star>> starGetters = List.of(
        new kdGetter<Star>() {
            @Override
            public double getValue(Star elm) {
                return elm.getX();
            }
        },
        new kdGetter<Star>() {
            @Override
            public double getValue(Star elm) {
                return elm.getY();
            }
        },
        new kdGetter<Star>() {
            @Override
            public double getValue(Star elm) {
                return elm.getZ();
            }
        }
    );

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

    private boolean testListTie(List<Star> expected, List<Star> actual, List<Star> swappables) {
        if (expected.equals(actual)) {
            return true;
        }
        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (expected.get(i).equals(actual.get(i))) {
                continue;
            } else {
               for (Star s : swappables) {
                   if (actual.get(i).equals(s)) {
                       continue;
                   }
               }
               //Here means they're not equal
                return false;
            }
        }
        return true;
    }


    @Test
    public void testBasic() {
        //Just one element, nearest-neighbors should return that element
        List<Star> starList = List.of(new Star(0, "SingleBoi", 10, 10, 10));
        gkd.loadData(starList, starGetters);
        assertEquals(gkd.nearestNeighbors(1, origin), starList);
    }

    //Define these outside any method, so they can be used globally
    Star star1 = new Star(1, "first", 0, 0, 0);
    Star star2 = new Star(2, "second", 0, 0, 1);
    Star star3 = new Star(3, "third", 0, 0, 3);
    Star star4 = new Star(4, "fourth", 0, 0, 6);
    Star star5 = new Star(5, "fifth", 0, 0, 10);
    Star star6 = new Star(6, "sixth", 0, 0, 14);
    Star star7 = new Star(7, "seventh", 0, 0, 14);
    Star star8 = new Star(8, "eight", 0, 0, 9);
    Star star9 = new Star(9, "nine", 0, 0, 9);
    Star star10 = new Star(10, "nine", 2, 0, 1);

    ArrayList<Star> tenStarList = new ArrayList<>(
        Arrays.asList(star1, star2, star3, star4, star5, star6, star7, star8, star9, star10));

    @Test
    public void basic() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        assertEquals(Arrays.asList(star2, star1), gkd.nearestNeighbors(2, star2));
    }

    @Test
    public void basic2() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //equal distance between >n stars
        assertEquals(Arrays.asList(star4, star3), gkd.nearestNeighbors(2, star4));
    }

    @Test
    public void equalDistance() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //equal distance between n stars
        assertTrue(testListTie(gkd.nearestNeighbors(2, star5), Arrays.asList(star5, star4), List.of(star4, star6, star7)));
    }

    @Test
    public void tooLargeN() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //fewer than 'n' nodes in the tree --> return all nodes (including the one inputted)
        assertEquals(Arrays.asList(star1, star2, star3, star4, star5, star6, star7, star8, star9),
            gkd.nearestNeighbors(500, star4));
    }

    @Test
    public void zeroNodes() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //0 nodes
        assertEquals(gkd.nearestNeighbors(0, star4), Arrays.asList());
    }

    @Test
    public void nonexistentCoords() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //non-existent coordinates -->
        assertEquals(
            Arrays.asList(star7, star6),
            gkd.nearestNeighbors(2, new Star(11, "not in tree", 0, 0, 1000)));
    }
}
