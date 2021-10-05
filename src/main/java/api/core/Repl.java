package api.core;

import api.client.ApiClient;
import api.client.ClientRequestGenerator;
import api.project1.Aggregator;
import api.project1.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * The REPL class for this lab.
 */
public class Repl {

    /**
     * instantiates a REPL.
     */
    public Repl() {
    }

    /**
     * This run method for the REPL requires an ApiClient object.
     *
     * @param client
     */
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Aggregator aggregator = new Aggregator();
        JsonParser jsonParser = new JsonParser();
        while (true) { // parsing input loop
            try {
                String s = reader.readLine();
                if (s == null) { // ctrl-D (exit) would result in null input
                    break;
                }
                // initialize a StringTokenizer to help parse the input, broken by space or tabs
                StringTokenizer st = new StringTokenizer(s, " \t", false);

                if (st.hasMoreTokens()) { // if the input is not blank, get the first token (the command)
                    String command = st.nextToken();
                    if (command.equals("getUser")) {
                        System.out.println(aggregator.justuserAggregator());
                    } else if (command.equals("getRent")) {
                        System.out.println(aggregator.justrentAggregator());
                    } else if (command.equals("getReview")) {
                        System.out.println(aggregator.justreviewAggregator());
                    } else if (command.equals("loadFiles")) {
                        jsonParser.parserFromFile("data/project-1/justrentSMALL.json");
                        jsonParser.parserFromFile("data/project-1/justreviewsSMALL.json");
                        jsonParser.parserFromFile("data/project-1/justusersSMALL.json");
                    } else { // command unrecognized
                        System.out.println("ERROR: Unrecognized command.");
                    }
                }
            } catch (IOException e) { // some kind of read error, so the repl exits
                System.out.println("ERROR: Failed parsing input.");
                break;
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("ERROR: Failed to close reader.");
        }
    }
}
