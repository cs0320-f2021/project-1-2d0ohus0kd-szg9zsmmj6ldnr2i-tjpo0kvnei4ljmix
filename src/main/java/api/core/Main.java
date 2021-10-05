package api.core;

import com.google.gson.Gson;
import api.client.ApiClient;
import api.client.ClientRequestGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

    /**
     * The initial method called when execution begins.
     *
     * @param args An array of command line arguments
     */
    public static void main(String[] args) {
        Repl repl = new Repl();
        repl.run();
    }
}
