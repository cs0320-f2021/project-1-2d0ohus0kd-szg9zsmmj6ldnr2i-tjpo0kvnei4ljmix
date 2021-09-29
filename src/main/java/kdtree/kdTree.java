package kdtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class kdTree<T> implements kdInterface<T>{
  int size = 0;
  ArrayList list_data;

  public kdTree(){} //Empty constructor for now

  /**
   * Normalizes the provided data, moves it into an ArrayList.
   * Also updates the this.size variable
   */
  private ArrayList<kdItem<T>> normalizeData(Collection<T> data, List<kdGetter> getters){
    //Create defensive copy into ArrayList while normalizing data
    ArrayList<kdItem> normalizedData = new ArrayList();
    ArrayList<Integer[]> dataScale = new ArrayList<>();
    //First pass to load all the data into the kdItem objects
    for (T kdElement : data) {
      for (kdGetter g : getters) {
        g.getValue(kdElement);
      }
    }
    //Second pass to normalize fields within kdItem objects

    return null;
  }


  @Override
  public void loadData(Collection<T> dataToLoad, List<kdGetter> getters) {
    this.list_data = normalizeData(dataToLoad, getters);
  }

  @Override
  public List nearestNeighbors(int n, Object center) {
    return null;
  }

  @Override
  public List nearestNeighborsExcludingCenter(int n, Object center) {
    return null;
  }

  @Override
  public List getAll() {
    return null;
  }

  @Override
  public boolean contains(Object target) {
    return false;
  }

  @Override
  public int size() {
    return this.size;
  }

  private class kdItem<kdObject>{
    public kdObject originalItem;
    public ArrayList<Integer> dimensions;
    public kdItem(kdObject originalItem, ArrayList<Integer> dimensions){
      this.originalItem = originalItem;
      this.dimensions = dimensions;
    }

  }
}
