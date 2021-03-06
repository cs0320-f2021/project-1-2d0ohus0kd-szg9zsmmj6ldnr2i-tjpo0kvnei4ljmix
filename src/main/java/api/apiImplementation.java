package api;

import api.core.Aggregator;
import api.core.JsonParser;

import java.util.Collection;

public class apiImplementation implements apiInterface {
    private Aggregator aggregator;
    private JsonParser jp;

    public apiImplementation() {
        this.aggregator = new Aggregator();
        this.jp = new JsonParser();
    }

    @Override
    public Collection getFromJson(APIenum field, String path) {
        return jp.parserFromFile(path, field);
    }

    @Override
    public Collection getFromApi(APIenum field) {
        switch (field) {
            case RENT:
                return this.aggregator.justrentAggregator();
            case USER:
                return this.aggregator.justuserAggregator();
            case REVIEW:
                return this.aggregator.justreviewAggregator();
            default:
                throw new IllegalArgumentException("Not a valid field");
        }
    }
}
