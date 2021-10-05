package api.project1;

import api.client.ApiClient;
import api.client.ClientRequestGenerator;

import java.util.ArrayList;
import java.util.List;

public class Aggregator {
    String user = "?auth=rhuang49&key=rapC1WL";
    int attempTimes = 3;
    ApiClient client = null;
    JsonParser jsonParser;

    public Aggregator() {
        client = new ApiClient();
        jsonParser = new JsonParser();
    }

    public List<Justreview> justreviewAggregator() {
        String[] endpoints = {"https://runwayapi.herokuapp.com/reviews-one",
                "https://runwayapi.herokuapp.com/reviews-two",
                "https://runwayapi.herokuapp.com/reviews-three",
                "https://runwayapi.herokuapp.com/reviews-four",
                "https://runwayapi.herokuapp.com/reviews-five"};
        List<Justreview> results = new ArrayList<Justreview>();
        for (String endpoint : endpoints) {
            String body = "";
            while (attempTimes-- > 0) {
                body = client.makeRequest(ClientRequestGenerator.getIntroGetRequest(endpoint + user));
                if (body.startsWith("[{\"review_text\"")) {
                    List<Justreview> justreviewList = jsonParser.justreviewListFromJson(body);
                    for (Justreview justreview : justreviewList) {
                        if (!results.contains(justreview)) {
                            results.add(justreview);
                        }
                    }
                }
            }
            attempTimes = 3;
        }
        return results;
    }

    public List<Justuser> justuserAggregator() {
        String[] endpoints = {"https://runwayapi.herokuapp.com/users-one",
                "https://runwayapi.herokuapp.com/users-two",
                "https://runwayapi.herokuapp.com/users-three",
                "https://runwayapi.herokuapp.com/users-four",
                "https://runwayapi.herokuapp.com/users-five"};
        List<Justuser> results = new ArrayList<Justuser>();
        for (String endpoint : endpoints) {
            String body = "";
            while (attempTimes-- > 0) {
                body = client.makeRequest(ClientRequestGenerator.getIntroGetRequest(endpoint + user));
//                System.out.println(body);
                if (body.startsWith("[{\"user_id\"")) {
                    List<Justuser> justuserList = jsonParser.justuserListFromJson(body);
                    for (Justuser justuser : justuserList) {
                        if (!results.contains(justuser)) {
                            results.add(justuser);
                        }
                    }
                }
            }
            attempTimes = 3;
        }
        return results;
    }

    public List<Justrent> justrentAggregator() {
        String[] endpoints = {"https://runwayapi.herokuapp.com/rent-one",
                "https://runwayapi.herokuapp.com/rent-two",
                "https://runwayapi.herokuapp.com/rent-three",
                "https://runwayapi.herokuapp.com/rent-four",
                "https://runwayapi.herokuapp.com/rent-five"};
        List<Justrent> results = new ArrayList<Justrent>();
        for (String endpoint : endpoints) {
            String body = "";
            while (attempTimes-- > 0) {
                body = client.makeRequest(ClientRequestGenerator.getIntroGetRequest(endpoint + user));
                if (body.startsWith("[{\"fit\"")) {
                    List<Justrent> justrentList = jsonParser.justrentListFromJson(body);
                    for (Justrent justrent : justrentList) {
                        if (!results.contains(justrent)) {
                            results.add(justrent);
                        }
                    }
                }
            }
            attempTimes = 3;
        }
        return results;
    }
}
