package edu.brown.cs.student.main;

import com.google.common.math.DoubleMath;
import kdtree.Pair;
import kdtree.kdGetter;
import kdtree.kdTree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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

    private boolean testStarTie(List<Star> expected, List<Star> actual, List<Star> swappables) {
        if (expected.equals(actual)) {
            return true;
        }
        if (expected.size() != actual.size()) {
            System.out.println("testListTie Size Mismatch!!");
            return false;
        }
        Set<Star> starSet = new HashSet<Star>(actual);
        if (starSet.size() != actual.size()) {
            //Duplicates in actual list
            System.out.println("Duplicates in actual list!");
            System.out.println(actual);
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!(expected.get(i).equals(actual.get(i)))) {
                boolean okay = false;
                for (Star s : swappables) {
                    if (actual.get(i).equals(s)) {
                        okay = true;
                    }
                }
                if (!okay) {
                    //Here means they're not equal
                    System.out.println("Failed testListTie!");
                    System.out.println("Expected: " + expected);
                    System.out.println("Actual: " + actual);
                    System.out.println("Swapables: " + swappables);
                    return false;
                }
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
    Star star10 = new Star(10, "ten", 2, 0, 1);

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
        assertTrue(
            testStarTie(List.of(star4, star3), gkd.nearestNeighbors(2, star4),  List.of(star3, star8, star9)));
    }

    @Test
    public void equalDistance() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //equal distance between n stars
        assertTrue(testStarTie(gkd.nearestNeighbors(2, star5), Arrays.asList(star5, star4), List.of(star4, star6, star7)));
    }

    @Test
    public void tooLargeN() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //fewer than 'n' nodes in the tree --> return all nodes (including the one inputted)
        assertEquals(Arrays.asList(star1, star2, star3, star4, star8, star9, star5, star6, star7, star10),
            gkd.nearestNeighbors(500, star1));
    }

    @Test
    public void zeroNodes() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //0 nodes
        assertEquals(Arrays.asList(), gkd.nearestNeighbors(0, star4));
    }

    @Test
    public void nonexistentCoords() {
        //loading tenStarList and starGetters to our global kdTree
        gkd.loadData(tenStarList, starGetters);
        //non-existent coordinates -->
        assertTrue(testStarTie(
            Arrays.asList(star7, star6),
            gkd.nearestNeighbors(2, new Star(11, "not in tree", 0, 0, 1000)),
            List.of(star7, star6)));
    }

    @Test
    public void emptyLoad() {
        List<Star> myList = new ArrayList<>();
        boolean caught = false;
        try {
            gkd.loadData(myList, starGetters);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        if (!caught) {
            fail("Did not error on zero-element load");
        }
    }


    //Make a random class and test procedurally with it
    class Octopus {
        private double[] legSize = new double[8];

        public Octopus (double[] legs) {
            this.legSize = legs;
        }

        public double getLegSize(int legNum) {
            return this.legSize[legNum];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Octopus octopus = (Octopus) o;
            return Arrays.equals(legSize, octopus.legSize);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(legSize);
        }

        @Override
        public String toString() {
            return "Octopus{" +
                "legSize=" + Arrays.toString(legSize) +
                '}';
        }
    }

    private Octopus randomOctopus() {
        Random rnd = new Random();
        double[] legs = {
            (rnd.nextDouble() * 100), (rnd.nextDouble() * 100),
            (rnd.nextDouble() * 100), (rnd.nextDouble() * 100),
            (rnd.nextDouble() * 100), (rnd.nextDouble() * 100),
            (rnd.nextDouble() * 100), (rnd.nextDouble() * 100)
        }; //Eight legs
        return new Octopus(legs);
    }

    private List<Octopus> randomOctopusLIst(int n) {
        List<Octopus> octopusList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            octopusList.add(randomOctopus());
        }
        return octopusList;
    }

    private double distance(Octopus o1, Octopus o2) {
        double dist = 0;
        for (int leg = 0; leg < 8; leg++) {
            dist += Math.pow(o1.getLegSize(leg) - o2.getLegSize(leg), 2);
        }
        return dist;
    }

    private List<Octopus> naiveOctopusNeighbors(int n, List<Octopus> octopods, Octopus center) {
        List<Pair<Octopus, Double>> octoDistance = new ArrayList<>(octopods.size());
        for (Octopus currOct : octopods) {
            double dist = distance(currOct, center);
            octoDistance.add(new Pair<Octopus, Double>(currOct, dist));
        }
        //Sort all the pairs by distance
        octoDistance.sort(new Comparator<Pair<Octopus, Double>>() {
            @Override
            public int compare(Pair<Octopus, Double> o1, Pair<Octopus, Double> o2) {
                if (o1.second < o2.second) return -1;
                if (o1.second > o2.second) return 1;
                return 0;
            }
        });
        List<Octopus> output = new ArrayList<>(octopods.size());
        for (Pair<Octopus, Double> p : octoDistance) {
            output.add(p.first);
        }
        if (n > output.size()) {
            n = output.size();
        }
        return output.subList(0, n);
    }

    List<kdGetter<Octopus>> octoGetters = List.of(
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(0);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(1);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(2);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(3);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(4);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(5);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(6);
            }
        },
        new kdGetter<Octopus>() {
            @Override
            public double getValue(Octopus elm) {
                return elm.getLegSize(7);
            }
        }
    );

    //Now procedurally test many lists versus the naive implementation

    final int NUM_TESTS = 1000;
    final int MAX_LEN = 1000;
    Random rnd = new Random();

    @Test
    public void proceduralTestNoNormalization() {
        kdTree<Octopus> octoTree = new kdTree<>();
        for (int i = 0; i < NUM_TESTS; i++) {
            int listLen = rnd.nextInt(MAX_LEN) + 1; //Make sure there's at least one thing in list
            int numNeighbors = rnd.nextInt(MAX_LEN); //Even if numNeighbors > listLen, it should handle it.
            Octopus center = randomOctopus();
            List<Octopus> octoList = randomOctopusLIst(listLen);
            octoTree.loadData(octoList, octoGetters, false);

            List<Octopus> naiveNeighbors = naiveOctopusNeighbors(numNeighbors, octoList, center);
            List<Octopus> kdtreeNeighbors = octoTree.nearestNeighbors(numNeighbors, center);
            assertEquals(naiveNeighbors.size(), kdtreeNeighbors.size());
            for (int k = 0; k < naiveNeighbors.size(); k++) {
                if (!(naiveNeighbors.get(k).equals(kdtreeNeighbors.get(k)))) {
                    if (!(DoubleMath.fuzzyEquals(distance(naiveNeighbors.get(k), center), distance(kdtreeNeighbors.get(k), center), 0.00001))) {
                        //Distance isn't the same
                        fail("Octopus test failed: Expected " + naiveNeighbors.get(k) + ", Actual: " + kdtreeNeighbors.get(k));
                    }
                }
            }
        }
        assertTrue(true); //Pass the test if we haven't failed
    }

    @Test
    public void proceduralTestWithNormalization() {
        kdTree<Octopus> octoTree = new kdTree<>();
        for (int i = 0; i < NUM_TESTS; i++) {
            int listLen = rnd.nextInt(MAX_LEN) + 1; //Make sure there's at least one thing in list
            int numNeighbors = rnd.nextInt(MAX_LEN); //Even if numNeighbors > listLen, it should handle it.
            Octopus center = randomOctopus();
            List<Octopus> octoList = randomOctopusLIst(listLen);
            double[] longestLegs = {100,100,100,100,100,100,100,100};
            double[] shortestLegs = {0,0,0,0,0,0,0,0};
            octoList.add(new Octopus(longestLegs)); //Make sure that normalization doesn't affect results
            octoList.add(new Octopus(shortestLegs));
            octoTree.loadData(octoList, octoGetters, true);

            List<Octopus> naiveNeighbors = naiveOctopusNeighbors(numNeighbors, octoList, center);
            List<Octopus> kdtreeNeighbors = octoTree.nearestNeighbors(numNeighbors, center);
            assertEquals(naiveNeighbors.size(), kdtreeNeighbors.size());
            for (int k = 0; k < naiveNeighbors.size(); k++) {
                if (!(naiveNeighbors.get(k).equals(kdtreeNeighbors.get(k)))) {
                    if (!(DoubleMath.fuzzyEquals(distance(naiveNeighbors.get(k), center), distance(kdtreeNeighbors.get(k), center), 0.00001))) {
                        //Distance isn't the same
                        fail("Octopus test failed: Expected " + naiveNeighbors.get(k) + ", Actual: " + kdtreeNeighbors.get(k));
                    }
                }
            }
        }
        assertTrue(true); //Pass the test if we haven't failed
    }
}
