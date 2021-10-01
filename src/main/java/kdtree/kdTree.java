package kdtree;

import java.util.Collection;
import java.util.List;

public class kdTree<T> implements kdInterface<T>{

  @Override
  public void loadData(Collection<T> dataToLoad, List<kdGetter<T>> getters) {

  }

  @Override
  public List<T> nearestNeighbors(int n, T center) {
    return null;
  }

  @Override
  public List<T> nearestNeighborsExcludingCenter(int n, T center) {
    return null;
  }

  @Override
  public List<T> getAll() {
    return null;
  }

  @Override
  public boolean contains(T target) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }
}
