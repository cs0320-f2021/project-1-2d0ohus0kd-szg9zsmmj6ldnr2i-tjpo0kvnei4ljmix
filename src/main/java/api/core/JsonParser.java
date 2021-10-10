package api.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    Gson gson;

    public JsonParser() {
        gson = new Gson();
    }

    public Justrent justrentFromJson(String json) {
        return gson.fromJson(json, Justrent.class);
    }

    public Justreview justreviewFromJson(String json) {
        return gson.fromJson(json, Justreview.class);
    }

    public Justuser justuserFromJson(String json) {
        return gson.fromJson(json, Justuser.class);
    }

    public List<Justreview> justreviewListFromJson(String s) {
        Type type = new TypeToken<ArrayList<Justreview>>() {
        }.getType();
        return gson.fromJson(s, type);
    }

    public List<Justuser> justuserListFromJson(String s) {
        Type type = new TypeToken<ArrayList<Justuser>>() {
        }.getType();
        return gson.fromJson(s, type);
    }

    public List<Justrent> justrentListFromJson(String s) {
        Type type = new TypeToken<ArrayList<Justrent>>() {
        }.getType();
        return gson.fromJson(s, type);
    }

    public void parserFromFile(String path) {
        FileParser fileParser = new FileParser(path);
        String line = fileParser.readNewLine();
        int count = 0;
        while (line != null) {
            if (count == 0)
                line = line.substring(1, line.length() - 1);
            else
                line = line.substring(0, line.length() - 1);
            count++;
            if (path.endsWith("justusersSMALL.json")) {
                Justuser justuser = justuserFromJson(line);
                System.out.println(justuser);
            } else if (path.endsWith("justrentSMALL.json")) {
                Justrent justrent = justrentFromJson(line);
                System.out.println(justrent);
            } else if (path.endsWith("justreviewsSMALL.json")) {
                Justreview justreview = justreviewFromJson(line);
                System.out.println(justreview);
            } else
                throw new IllegalArgumentException("error file");
            line = fileParser.readNewLine();
        }
    }
}
