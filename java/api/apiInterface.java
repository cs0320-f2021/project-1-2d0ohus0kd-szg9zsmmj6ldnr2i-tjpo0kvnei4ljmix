package api;

import java.util.Collection;

public interface apiInterface {

    //set is a collection
    //list is a collection
    //

    /**
     * Loads data from a JSON file according to the assignment spec.
     *
     * @param path path to JSON file
     * @return a collection of all the data from the JSON file
     */
    Collection getFromJson(APIenum field, String path);

    /**
     * reads from API endpoints to get a reliable and complete collection of data
     *
     * @return collection of reliable data from those API endpoints
     */
    Collection getFromApi(APIenum field);

}