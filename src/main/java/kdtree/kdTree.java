package kdtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class kdTree<T> implements kdInterface<T> {
  private int size = 0;
  private ArrayList listData;

  public kdTree() {
  }  //Empty constructor for now

  /**
   * Normalizes the provided data, moves it into an ArrayList.
   * Also updates the this.size variable
   * @return
   */
  private ArrayList<kdItem> normalizeData(Collection<T> data, List<kdGetter<T>> getters) {
    final int numDimensions = getters.size();
    //Create defensive copy into ArrayList while normalizing data
    ArrayList<kdItem> normalizedData = new ArrayList();
    ArrayList<Double[]> dataScale = new ArrayList<>(numDimensions);
    //Fill the dataScale array with the first data value
    for (Double[] maxMin : dataScale) {
      maxMin = new Double[2];
      maxMin[0] = Double.MAX_VALUE; //Set min to highest value, so it's immediately overwritten
      maxMin[1] = Double.MIN_VALUE; //set max to lowest value, so it's immediately overwritten
    }

    //First pass to load all the data into the kdItem objects
    for (T currObject : data) {
      ArrayList dimValues = new ArrayList(numDimensions);
      for (int dim = 0; dim < numDimensions; dim++) {
        Double dimValue = getters.get(dim).getValue(currObject);
        dimValues.add(dim, dimValue);
        Double minDimVal = dataScale.get(dim)[0];
        Double maxDimVal = dataScale.get(dim)[1];
        if (dimValue < minDimVal) {
          dataScale.get(dim)[0] = dimValue; //TODO: Make sure this style of accessing works
        }
        if (dimValue > maxDimVal) {
          dataScale.get(dim)[1] = dimValue;
        }
      }
      normalizedData.add(new kdItem(currObject, dimValues));
    }

    //Now, normalize the data using the max and min provided

    //Second pass to normalize fields within kdItem objects
    for (int i = 0; i < normalizedData.size(); i++) {
      ArrayList<Double> dims = normalizedData.get(i).getDimensions();
      for (int dim = 0; dim < numDimensions; dim++) {
        double distanceFromMax = dataScale.get(dim)[1] - dims.get(dim);
        double dataSpread = dataScale.get(dim)[1] - dataScale.get(dim)[0];
        double newValue = 1.0 - (distanceFromMax / dataSpread);
        dims.set(dim, newValue);
        assert newValue > 0;
      }
      //Make a new kdItem and replace the old one
      normalizedData.set(i, new kdItem(normalizedData.get(i).getOriginalItem(), dims));
    }

    return normalizedData;
  }


  @Override
  public void loadData(Collection<T> dataToLoad, List<kdGetter<T>> getters) {
    this.listData = normalizeData(dataToLoad, getters);
    //listData is an ArrayList of kdItem objects, which all contain normalized fields
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

  private class kdItem<kdObject> { //Just a dumb data-holding class
    private final kdObject originalItem;
    private final ArrayList<Double> dimensions;
    kdItem(kdObject originalItem, ArrayList<Double> dimensions) {
      this.originalItem = originalItem;
      this.dimensions = dimensions;
    }

    public kdObject getOriginalItem() {
      return this.originalItem;
    }

    public ArrayList<Double> getDimensions(){
      return this.dimensions;
    }
  }
}
