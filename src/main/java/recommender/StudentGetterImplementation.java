package recommender;


import api.core.FileParser;
import api.core.Justreview;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class StudentGetterImplementation implements StudentGetter {
    @Override
    public Collection<Student> getStudents() {
        String data = "data/project-1/integration.json";
        FileParser fileParser = new FileParser(data);
        String line = fileParser.readNewLine();
        String s = "";
        while (line != null) {
            s += line;
            line = fileParser.readNewLine();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Student>>() {
        }.getType();
        return gson.fromJson(s, type);
    }
}
