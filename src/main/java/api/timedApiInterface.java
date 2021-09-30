package api;

public interface timedApiInterface extends apiInterface {

  /** Returns the time taken to access the API
   *
   * @return time in seconds taken to get all API data
   */
  public double accessTime();

}
