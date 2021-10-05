package api.project1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import api.core.FileParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        JsonParser jsonParser=new JsonParser();
//        jsonParser.parserFromFile("data/project-1/justrentSMALL.json");
//        jsonParser.parserFromFile("data/project-1/justreviewsSMALL.json");
//        jsonParser.parserFromFile("data/project-1/justusersSMALL.json");
        String s = "[{\"user_id\": \"151944\", \"weight\": \"145lbs\", \"bust_size\": \"34b\", \"height\": \"5' 9\\\"\", \"age\": \"27\", \"body_type\": \"athletic\", \"horoscope\": \"Libra\"}, " +
                "{\"user_id\": \"154309\", \"weight\": \"114lbs\", \"bust_size\": \"32b\", \"height\": \"5' 3\\\"\", \"age\": \"33\", \"body_type\": \"petite\", \"horoscope\": \"Scorpio\"}]";

        System.out.println(jsonParser.justuserListFromJson(s));
    }
}
