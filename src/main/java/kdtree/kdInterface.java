package kdtree;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

interface kdInterface<ArbitraryType>{
  //Data is normalized with the highest value in any group being 1.0, and the lowest being 0.0
  //This normalized data is used for distance comparison

  /*
  user: H: 1.7m W: 80kg
  user: H: 1.5m W: 50kg
  User: H: 1.6m W: 110kg
  user: H: 1.69m W: 109kg
  -->
  user: H: 1.0 W: 0.5
  user: H: 0.0 W: 0.0
  user: H: 0.5 W: 1.0
  user: H: 1.0 - (1/20) W: 1.0 - (1/60)
   */

  /** Loads data into the KD-tree. Replaces any existing data.
   *
   * @param dataToLoad data to load into the KD-tree.
   * @param getters A list of kdGetter to get the fields to sort by in the KDtree
   */
  public void loadData(Collection<ArbitraryType> dataToLoad, List<kdGetter<ArbitraryType>> getters);


  /** Finds several nearest neighbors given a data point to start from
   *
   * @param n number of nearest neighbors to return
   * @param center data point to find nearest neighbors around
   * @return A list of the n nearest neighbors
   */
  public List<ArbitraryType> nearestNeighbors(int n, ArbitraryType center);

  public List<ArbitraryType> nearestNeighborsExcludingCenter(int n, ArbitraryType center);

  /**
   *
   * @return A list of all the items in the KD-tree in no particular order
   */
  public List<ArbitraryType> getAll();


  /**
   *
   * @return number of items in the KD-Tree
   */
  public int size();
}
