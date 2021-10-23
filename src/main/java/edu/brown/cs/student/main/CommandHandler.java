package edu.brown.cs.student.main;

import edu.brown.cs.student.main.commands.Add;
import edu.brown.cs.student.main.commands.LoadStars;
import edu.brown.cs.student.main.commands.NearestNeighborKD;
import edu.brown.cs.student.main.commands.NearestNeighborNaive;
import edu.brown.cs.student.main.commands.Subtract;
import edu.brown.cs.student.main.commands.TestCommand;
import edu.brown.cs.student.main.commands.UsersCommand;
import edu.brown.cs.student.main.commands.APITest;
import edu.brown.cs.student.main.commands.ORMTest;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
  private static HashMap<String, Command> commands = new HashMap<String, Command>(Map.of(
          "test", new TestCommand(),
          "add", new Add(),
          "subtract", new Subtract(),
          "stars", new LoadStars(),
          "neighbors", new NearestNeighborKD(),
          "naive_neighbors", new NearestNeighborNaive(),
          "users", new UsersCommand(),
          "testAPI",new APITest(),
          "testORM",new ORMTest()
          //Add new commands here (Or remove old commands if needed)
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
  public static HashMap<String, Command> getCommands() {
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
