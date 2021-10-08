package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.main.Command;
import kdtree.kdGetter;
import kdtree.kdTree;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class loadtmp extends Command {

  public loadtmp() {
    super(new HashSet<String>(List.of("loadtmp")));
  }

  @Override
  public String run(String argString) {
    int numPeople = Integer.parseInt(argString);
    kdTree<dummyPerson> kd = new kdTree<dummyPerson>();
    ArrayList<dummyPerson> people = new ArrayList<>();
    Random rng = new Random();
    for (int i = 0; i < numPeople; i++) {
      people.add(
          new dummyPerson(rng.nextDouble() * 123, rng.nextDouble() * 456, rng.nextDouble() * 789));
    }

    ArrayList<kdGetter<dummyPerson>> getters = new ArrayList<>(3);

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
    getters.add(new kdGetter<dummyPerson>() {
      @Override
      public double getValue(dummyPerson elm) {
        return elm.age;
      }
    });

    kd.loadData(people, getters);
    System.out.println(kd.nearestNeighbors(5, new dummyPerson(100, 400, 700)));
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

    @Override
    public String toString() {
      return "dummyPerson{" +
          "weight=" + weight +
          ", height=" + height +
          ", age=" + age +
          '}';
    }
  }
}
