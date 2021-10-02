package edu.brown.cs.student.main;

import kdtree.kdGetter;
import kdtree.kdTree;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

public class load extends Command{
  private int NUM_PEOPLE = 10;

  public load() {
    super(new HashSet<String>(Arrays.asList("loadtmp")));
  }

  @Override
  public String run(String argString) {
    kdTree kd = new kdTree();
    ArrayList<dummyPerson> people = new ArrayList<>();
    Random rng = new Random();
    for (int i = 0; i < NUM_PEOPLE; i++) {
      people.add(new dummyPerson(rng.nextDouble(), rng.nextDouble(), rng.nextDouble()));
    }

    ArrayList<kdGetter<dummyPerson>> getters = new ArrayList<>(2);

    getters.add(new kdGetter<dummyPerson>() {
      @Override
      public double getValue(dummyPerson elm) {
        return elm.weight;
      }
    });
    getters.add(new kdGetter<dummyPerson>() {
      @Override
      public double getValue(dummyPerson elm) {
        return elm.height;
      }
    });

    kd.loadData(people, getters);
    return kd.toString();
  }

  class dummyPerson {
    public final double weight;
    public final double height;
    public final double age;
    public dummyPerson(double weight, double height, double age) {
      this.weight = weight;
      this.height = height;
      this.age = age;
    }
  }
}
