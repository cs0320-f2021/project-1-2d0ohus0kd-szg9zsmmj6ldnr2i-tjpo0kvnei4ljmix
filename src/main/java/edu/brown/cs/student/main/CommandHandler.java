package edu.brown.cs.student.main;

import edu.brown.cs.student.main.commands.Add;
import edu.brown.cs.student.main.commands.KDTest;
import edu.brown.cs.student.main.commands.LoadStars;
import edu.brown.cs.student.main.commands.NearestNeighborKD;
import edu.brown.cs.student.main.commands.NearestNeighborNaive;
import edu.brown.cs.student.main.commands.RecsysLoad;
import edu.brown.cs.student.main.commands.RecsysRec;
import edu.brown.cs.student.main.commands.RecsysGenGroups;
import edu.brown.cs.student.main.commands.Subtract;
import edu.brown.cs.student.main.commands.TestCommand;
import edu.brown.cs.student.main.commands.UsersCommand;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
  private static Map<String, Command> commands = new HashMap<String, Command>(Map.ofEntries(
      Map.entry("test", new TestCommand()),
      Map.entry("add", new Add()),
      Map.entry("subtract", new Subtract()),
      Map.entry("stars", new LoadStars()),
      Map.entry("neighbors", new NearestNeighborKD()),
      Map.entry("naive_neighbors", new NearestNeighborNaive()),
      Map.entry("users", new UsersCommand()),
      Map.entry("kdtest", new KDTest()),
      Map.entry("recsys_load", new RecsysLoad()),
      Map.entry("recsys_rec", new RecsysRec()),
      Map.entry("recsys_gen_groups", new RecsysGenGroups())
  ));

  /**
   *
   * @param commandName Name for the command
   * @param commandInstance Instance of the class (extending the Command interface)
   * @return true if the command was added successfully, false if a command with that name already exists
   */
  public static boolean addCommand(String commandName, Command commandInstance) {
    if (commands.containsKey(commandName)) {
      return false;
    }
    commands.put(commandName, commandInstance);
    return true;
  }

  /**
   *
   * @return hashmap of the command names along with the commands themselves
   */
  public static Map<String, Command> getCommands() {
    return commands;
  }

  /** Essentially a wrapper for the Map.remove method
   *
   * @param commandName name of the command to remove.
   * @return the value of the command that was removed, or null if no command of that name exists.
   */
  public static Command removeCommand(String commandName) {
    return commands.remove(commandName);
  }
}
