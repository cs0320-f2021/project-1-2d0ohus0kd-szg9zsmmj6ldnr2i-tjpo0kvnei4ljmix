package api;

public interface timedApiInterface <T> extends apiInterface<T> {

  /** Returns the time taken to access the API
   *
   * @return time in seconds taken to get all API data
   */
  public double accessTime();

}
