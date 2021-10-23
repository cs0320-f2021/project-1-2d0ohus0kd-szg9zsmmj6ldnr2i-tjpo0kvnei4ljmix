package api.core;

import api.APIenum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
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

    public Collection parserFromFile(String path, APIenum field) {
        FileParser fileParser = new FileParser(path);
        String line = fileParser.readNewLine();
        String s = "";
        while (line != null) {
            s += line;
            line = fileParser.readNewLine();
        }
        switch (field) {
            case RENT:
                return justrentListFromJson(s);
            case USER:
                return justuserListFromJson(s);
            case REVIEW:
                return justreviewListFromJson(s);
            default:
                throw new IllegalArgumentException("Not a valid field");
        }
    }
}
