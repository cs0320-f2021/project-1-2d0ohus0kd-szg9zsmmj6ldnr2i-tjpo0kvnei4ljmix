package edu.brown.cs.student.main.commands;

import api.core.Aggregator;
import api.core.JsonParser;
import api.core.Justrent;
import api.core.Justreview;
import api.core.Justuser;
import edu.brown.cs.student.main.Command;
import kdtree.kdGetter;
import kdtree.kdTree;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersCommand extends Command {
  private final Aggregator aggregator;
  private final JsonParser jp;
  private final kdTree<Justrent> rentTree;
  private final kdTree<Justuser> userTree;
  private final kdTree<Justreview> reviewTree;

  private List<kdGetter<Justuser>> userGetters = List.of(
      new kdGetter<Justuser>() {
        @Override
        public double getValue(Justuser elm) {
          String wight=elm.getWeight();
          return Double.parseDouble(wight.substring(0,wight.length()-4)); // changes string to int/double
        }
      },
      new kdGetter<Justuser>() {
        @Override
        public double getValue(Justuser elm) {
            String wight =elm.getHeight();
            return Double.parseDouble(String.valueOf(wight.charAt(0)))*12+Double.parseDouble(String.valueOf(wight.charAt(3)));// changes string to int/double
        }
      },
      new kdGetter<Justuser>() {
        @Override
        public double getValue(Justuser elm) {
          return elm.getAge();
        }
      }
  );

  public UsersCommand() {
    this.aggregator = new Aggregator();
    this.jp = new JsonParser();
    this.rentTree = new kdTree<>();
    this.userTree = new kdTree<>();
    this.reviewTree = new kdTree<>();
  }

  @Override
  public String run(String argString) {
    //Test if argString is a path or not
    if (argString.equalsIgnoreCase("online")) {
      //get data from API
      //Load only users for now
      long startTime = System.currentTimeMillis();
      Collection<Justuser> users = aggregator.justuserAggregator();
      long endTime = System.currentTimeMillis();
      long duration = (endTime - startTime);
      userTree.loadData(users, userGetters, false);
      return "Loaded " + users.size() + " Users from online API in " + duration + "ms"
          + " (" + new DecimalFormat("#.##").format(duration / 1000.0d) + " seconds)";
    } else {
      //Not an online operation, so argString must be a filepath
      Collection<Justuser> users;
      try {
        users = jp.justuserListFromJson(argString);
      } catch (Exception e){
        //Seems like it wasn't a path
        return "Path invalid. Either provide a valid path or type 'online' to load from API";
      }
      userTree.loadData(users, userGetters, false);
      return "Loaded " + users.size() + " Users from json file";
    }
  }
}
