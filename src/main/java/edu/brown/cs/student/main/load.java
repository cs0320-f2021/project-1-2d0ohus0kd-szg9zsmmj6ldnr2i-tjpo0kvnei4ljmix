package edu.brown.cs.student.main;

import kdtree.kdGetter;
import kdtree.kdTree;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

public class load extends Command{
  public load() {
    super(new HashSet<String>(Arrays.asList("loadtmp")));
  }

  @Override
  public String run(String argString) {
    kdTree kd = new kdTree();
    ArrayList<dummyPerson> people = new ArrayList<>();
    people.add(new dummyPerson(100, 100));
    people.add(new dummyPerson(100, 100));
    people.add(new dummyPerson(75, 100));
    people.add(new dummyPerson(100, 75));
    people.add(new dummyPerson(250, 200));
    people.add(new dummyPerson(50, 23));

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
    public dummyPerson(int weight, int height) {
      this.weight = weight;
      this.height = height;
    }
  }
}
