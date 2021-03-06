package edu.brown.cs.student.main;

import kdtree.kdGetter;
import kdtree.kdTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarFinderKD {
  private boolean invalid = true; //true until stars are loaded
  private ArrayList<Star> starData;
  private static StarFinderKD instance = null;

  //kdTree optimizations
  private ArrayList<kdGetter<Star>> starGetters = new ArrayList<>();

  private kdTree<Star> kdt = new kdTree();

  //from: https://stackoverflow.com/questions/4419810/how-to-share-data-between-separate-classes-in-java
  //Basically, this lets both Star and NearestNeighbor share the same instance of this class
  public static StarFinderKD getInstance() {
    if (instance == null) {
      instance = new StarFinderKD();
    }
    return instance;
  }

  /**
   * Constructor for the StarFinder class.
   */
  public StarFinderKD() {
    this.starGetters.add(new kdGetter<Star>() {
      @Override
      public double getValue(Star elm) {
        return elm.getX();
      }
    });

    this.starGetters.add(new kdGetter<Star>() {
      @Override
      public double getValue(Star elm) {
        return elm.getY();
      }
    });

    this.starGetters.add(new kdGetter<Star>() {
      @Override
      public double getValue(Star elm) {
        return elm.getZ();
      }
    });
  }

  public boolean isInvalid() {
    return this.invalid;
  }

  /**
   * Loads CSV file into StarFinder.
   *
   * @param path path to CSV file
   */

  public String loadStars(String path) {
    this.invalid = false; //valid until proven otherwise by a CSV read error
    BufferedReader starReader;
    String line;
    try {
      //Taken from here: https://www.baeldung.com/reading-file-in-java#file-with-buffered-reader
      starReader = new BufferedReader(new FileReader(path));
      line = starReader.readLine();
    } catch (Exception e) {
      this.invalid = true;
      return "ERROR: Could not find the file specified. Check for spelling errors.";
    }
    if (!(line.equals("StarID,ProperName,X,Y,Z"))) {
      //First line of the CSV is in the wrong format, notify the user and mark CSV as invalid.
      this.invalid = true;
      return "ERROR: Invalid CSV, make sure the CSV formatting is correct.";
    }
    starData = new ArrayList<>();
    while (true) {
      try {
        line = starReader.readLine();
        if (line == null) {
          break;
        }
      } catch (IOException e) {
        return "ERROR: There was an issue reading the file, check for corruption";
      }
      String[] rawStarData = line.split(",");
      if (rawStarData.length != 5) {
        this.invalid = true;
        return "ERROR: The CSV has an incorrect number of fields and/or is broken!";
      }
      //Convert id and x/y/z int
      int id;
      double x, y, z;
      try {
        id = Integer.parseInt(rawStarData[0]);
        x = Double.parseDouble(rawStarData[2]);
        y = Double.parseDouble(rawStarData[3]);
        z = Double.parseDouble(rawStarData[4]);
      } catch (Exception e) {
        this.invalid = true;
        return "ERROR: Could not parse CSV - Check for corruption";
      }
      String properName = rawStarData[1];
      starData.add(new Star(id, properName, x, y, z));
    }

    this.kdt.loadData(this.starData, this.starGetters);

    return "Read " + this.starData.size() + " stars from " + path;
    //System.out.println("The first star is named " + starData.get(0).getName());
  }

  /**
   *
   * @param k number of nearest neighbors to find
   * @param x x-coordinate to center search around
   * @param y y-coordinate to center search around
   * @param z z-coordinate to center search around
   * @return ArrayList of the k closest stars, sorted from closest to furthest.
   *
   * If there is a tie, picks randomly between the stars that are tied.
   *
   */
  public List<Star> knn(int k, double x, double y, double z) {
    if (this.invalid) {
      System.out.println("ERROR: Star CSV has not been loaded or is invalid");
      return new ArrayList<>();
    }
    //Just use the kd tree
    return this.kdt.nearestNeighbors(k, new Star(0, "", x, y, z));
  }

  /**
   *
   * @param k number of neighbors to find
   * @param name name of the star to be searched around
   * @return Arraylist of the k closest stars, sorted from closest to furthest
   */
  public List<Star> namedKnn(int k, String name) {
    if (this.invalid) {
      System.out.println("ERROR: Star CSV has not been loaded or is invalid");
      return new ArrayList<>();
    }
    if (name.isEmpty()) {
      System.out.println("ERROR: Star name cannot be empty");
      return new ArrayList<>();
    }
    //Find the x/y/z coordinates of the star with starName, then pass that info to knn

    for (Star s : this.starData) {
      if (s.getName().equals(name)) {
        List<Star> result = this.knn(k + 1, s.getX(), s.getY(), s.getZ());
        result.removeIf(str -> (str.getName().equals(name)));

        return result;
      }
    }
    //If we exit the loop without returning, this means none of the stars matched the name
    System.out.println("ERROR: Name did not match any known star");
    return new ArrayList<>();
  }




}
