package api;

import java.util.Collection;

public interface apiInterface<T> {

  //set is a collection
  //list is a collection
  //

  /** Loads data from a JSON file according to the assignment spec.
   *
   * @param path path to JSON file
   * @return a collection of all the data from the JSON file
   */
  public Collection<T> getFromJson(String path);

  /** reads from API endpoints to get a reliable and complete collection of data
   *
   * @return collection of reliable data from those API endpoints
   */
  public Collection<T> getFromAPI();


  /** Gets all data from API endpoints for a specific field (e.g. 'Star Sign', 'weight')
   *
   * @return all data matching a specific field
   */
  public Collection<T> getField();

}
