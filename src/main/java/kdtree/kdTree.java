package kdtree;

import com.google.common.math.DoubleMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class kdTree<T> implements kdInterface<T> {
  private int size = 0;
  private ArrayList<kdItem<T>> listData;
  private kdItem<T> root;
  private int numDims;
  private List<kdGetter<T>> kdGetters;
  private Double[][] dataScale;
  private Double[] dataRange;
  private boolean normalize = true;

  public kdTree() {
  }  //Empty constructor for now

  /**
   * Normalizes the provided data, moves it into an ArrayList.
   * Also updates the this.size variable
   * @return arrayList of kdItem objects with normalized data inside
   */
  private ArrayList<kdItem<T>> normalizeData(Collection<T> data, List<kdGetter<T>> getters)
      throws IllegalArgumentException {
    this.numDims = getters.size();
    if (this.numDims == 0) {
      throw new IllegalArgumentException("No Getters Provided");
    }
    if (data.size() == 0) {
      throw new IllegalArgumentException("Nothing to load into KD Tree");
    }
    //Create defensive copy into ArrayList while normalizing data
    ArrayList<kdItem<T>> normalizedData = new ArrayList<>();
    this.dataScale = new Double[this.numDims][2];
    //Fill the dataScale array with the first data value
    for (Double[] maxMin : dataScale) {
      maxMin[0] = Double.MAX_VALUE; //Set min to highest value, so it's immediately overwritten
      maxMin[1] = Double.MIN_VALUE; //set max to lowest value, so it's immediately overwritten
    }

    //First pass to load all the data into the kdItem objects
    for (T currObject : data) {
      double[] dimValues = new double[this.numDims];
      for (int dim = 0; dim < this.numDims; dim++) {
        double dimValue = getters.get(dim).getValue(currObject);
        dimValues[dim] = dimValue;
        double minDimVal = dataScale[dim][0];
        double maxDimVal = dataScale[dim][1];
        if (dimValue < minDimVal) {
          dataScale[dim][0] = dimValue;
        }
        if (dimValue > maxDimVal) {
          dataScale[dim][1] = dimValue;
        }
      }
      normalizedData.add(new kdItem<>(currObject, dimValues));
    }

    //Create dataRange
    assert this.numDims == this.dataScale.length;
    this.dataRange = new Double[this.numDims];
    for (int i = 0; i < this.numDims; i++) {
      dataRange[i] = this.dataScale[i][1] - this.dataScale[i][0];
    }


    //Now, normalize the data using the max and min provided
    //Second pass to normalize fields within kdItem objects
    for (int i = 0; i < normalizedData.size(); i++) {
      double[] dims = normalizedData.get(i).getDimensionArray();
      for (int dim = 0; dim < this.numDims; dim++) {
        dims[dim] = normalizeSingleValue(dims[dim], dim);
        //Sanity check to make sure normalization went well
        if (this.normalize){
          assert dims[dim] >= 0;
          assert dims[dim] <= 1;
        }
      }
      //Make a new kdItem and replace the old one
      normalizedData.set(i, new kdItem<>(normalizedData.get(i).getOriginalItem(), dims));
    }

    return normalizedData;
  }

  private double normalizeSingleValue(double dataPoint, int dim) {
    double newValue = dataPoint;
    if (this.normalize) {
      double distanceFromMax = dataScale[dim][1] - dataPoint;
      newValue = 0;
      if (!(DoubleMath.fuzzyEquals(dataRange[dim], 0, 0.000001))) {
        newValue = 1.0 - (distanceFromMax / dataRange[dim]);
      }
    }
    return newValue;
  }


  @Override
  public void loadData(Collection<T> dataToLoad, List<kdGetter<T>> getters) {
    this.kdGetters = new ArrayList<>(getters);
    this.listData = normalizeData(dataToLoad, getters);
    //listData is an ArrayList of kdItem objects, which all contain normalized fields

    //This is a bad implementation of inserting, we walk the tree each time we insert an element
    //Plus, the tree doesn't even end up being balanced

    //Make sure the root is at least somewhere near the center
    ArrayList<kdItem<T>> dataCopy = new ArrayList<>(this.listData); //for mutability
    sortByDim(dataCopy, 0);
    for (kdItem<T> elm : dataCopy) {
      this.insertItem(elm);
    }
  }

  public void loadData(Collection<T> dataToLoad, List<kdGetter<T>> getters, boolean normalize) {
    this.normalize = normalize;
    this.loadData(dataToLoad, getters);
  }

  private void sortByDim(ArrayList<kdItem<T>> data, int dim) {
    data.sort(new Comparator<kdItem>() {
      @Override
      public int compare(kdItem o1, kdItem o2) {
        if (o1.getDimension(dim) > o2.getDimension(dim)) {
          return 1;
        }
        if (o1.getDimension(dim) < o2.getDimension(dim)) {
          return -1;
        }
        return 0;
      }
    });
  }


  private void insertItem(kdItem<T> toInsert) {
    kdTree<T>.kdItem<T> prevNode = null;
    kdTree<T>.kdItem<T> currNode = this.root;
    int currDim = 0;
    int side = -1;
    while (currNode != null) {
      side = (toInsert.getDimension(currDim) < currNode.getDimension(currDim)) ? 0 : 1;
      //if true, side is 0 (left). if false, side is 1. (right)
      prevNode = currNode;
      currNode = currNode.getChildren()[side];
      //increment dim so that next time we loop, we're checking the next dimension
      currDim = (currDim + 1) % this.numDims;
    }
    //Past the while loop, this means that currNode is null.
    toInsert.setChildrenSortDim(currDim);
    if (prevNode == null) {
      //Empty tree -- insert node as the root
      this.root = toInsert;
    } else {
      prevNode.getChildren()[side] = toInsert;
    }
    this.size++;
  }

  @Override
  public List<T> nearestNeighbors(int n, T targetRaw) {
    if (n == 0) {
      return new ArrayList<T>(); //return empty list if asking for 0 neighbors
    }
    //convert target to a kdItem object
    double[] dims = new double[this.numDims];
    for (int i = 0; i < this.numDims; i++) {
      dims[i] = this.normalizeSingleValue(this.kdGetters.get(i).getValue(targetRaw), i);
      //Sanity check to make sure normalization went well
      if (this.normalize){
        assert dims[i] >= 0;
        assert dims[i] <= 1;
      }
    }
    kdItem<T> target = new kdItem<>(targetRaw, dims);
    kdItem<T>[] best = new kdItem[n];
    Stack<kdItem> toCheck = new Stack<>();
    assert (this.root != null);
    toCheck.push(this.root);
    double bestDist = Double.MAX_VALUE;
    while (!(toCheck.empty())) {
      kdItem currItem = toCheck.pop();
      int currDim = currItem.getChildrenSortDim();
      int idealSide = target.getDimension(currDim) < currItem.getDimension(currDim) ? 0 : 1;
      // If the target's value is less than current, go left (0), otherwise right (1)
      int nonIdealSide = (idealSide == 1) ? 0 : 1; //opposite of the ideal side
      currItem.calcDistance(target.getDimensionArray());
      if (bestDist > currItem.getDistance()) {
        bestDist = bubbleIntoArray(best, currItem); //Insert into array of best values
      }
      if (currItem.getChildren()[idealSide] != null) {
        //if not equal to null, we need to check the ideal child
        toCheck.push(currItem.getChildren()[idealSide]);
      }
      double best1dim = Math.abs(target.getDimension(currDim) - currItem.getDimension(currDim));
      if (best1dim < bestDist) {
        //This means the other child could have a good value, we need to check it if not null
        if (currItem.getChildren()[nonIdealSide] != null) {
          toCheck.push(currItem.getChildren()[nonIdealSide]);
        }
        //TODO: Optimize by storing best1dim in the toCheck, so it can be skipped sometimes
        // ^ also, push the ideal branch first, because it's more likely to contain the answer
        // ^ this way, we basically walk the whole tree which is bad
      }
    }
    ArrayList<T> bestList = new ArrayList<>();
    for (kdItem<T> elm : best) {
      if (elm != null) {
        bestList.add(elm.originalItem);
      }
    }
    return bestList;
  }

  /** Mutates the given array by putting the given kdItem in the correct place (maintains sort).
   *
   * @param best array to mutate
   * @return new best distance
   */
  private double bubbleIntoArray(kdItem[] best, kdItem toInsert) {
    kdItem bubble = toInsert;
    for (int idx = 0; idx < best.length; idx++) {
      if (best[idx] == null) {
        //array is not yet fully filled
        best[idx] = bubble;
        return Double.MAX_VALUE;
      }
      if (best[idx].getDistance() > bubble.getDistance()) {
        kdItem tmp = best[idx];
        best[idx] = bubble;
        bubble = tmp;
      }
    }
    //Getting here means array is fully filled
    return best[best.length - 1].getDistance();
  }

  @Override
  public List<T> nearestNeighborsExcludingCenter(int n, T center) {
    return this.nearestNeighbors(n + 1, center).subList(1, n + 1); // just take out first result
  }

  @Override
  public List<T> getAll() {
    //get original objects out of the kdItem classes, and return as a list
    return this.listData.stream().map(kdItem::getOriginalItem).collect(Collectors.toList());
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public String toString() {
    String output  = "kdTree with root: " + Arrays.toString(this.root.getDimensionArray())
        + " Left children: " + countChildren(this.root.getChildren()[0])
        + " Right children: " + countChildren(this.root.getChildren()[1]);

    if (this.size == 0) {
      output += "\nThe tree is empty";
      return output;
    }

    if (this.size < 32) {
      //Print the structure of the tree
      output += "\n" + this.root.toString();
    }

    return output;
  }

  /**
   *
   * @return array of length 2 with left and right node counts for given item
   */
  private int countChildren(kdItem<T> currNode) {
    if (currNode == null) {
      return 0;
    }
    kdItem<T>[] children = currNode.children;
    int count = 0;
    if (children[0] != null) {
      count += countChildren(children[0]);
    }
    if (children[1] != null) {
      count += countChildren(children[1]);
    }
    return count + 1;
  }

  private class kdItem<kdObject> { //Just a dumb data-holding class
    private final kdObject originalItem;
    private final double[] dimensions;
    private final kdItem<kdObject>[] children = new kdItem[2];
    private int childrenSortDim = -2; //dimension which the object will sort its children by
    private double distance = -2;

    kdItem(kdObject originalItem, double[] dimensions) {
      this.originalItem = originalItem;
      this.dimensions = dimensions;
    }

    public kdObject getOriginalItem() {
      return this.originalItem;
    }

    public double[] getDimensionArray() {
      return this.dimensions;
    }

    public double getDimension(int idx) {
      return this.dimensions[idx];
    }

    public kdItem<kdObject>[] getChildren() {
      return children;
    }

    public int getChildrenSortDim() {
      return childrenSortDim;
    }

    public void setChildrenSortDim(int childrenSortDim) {
      this.childrenSortDim = childrenSortDim;
    }

    public double getDistance() {
      if (this.distance < 0) {
        //Error, distance was gotten before it was set
        throw new RuntimeException("kdItem Distance was retrieved before being set!");
      }
      return distance;
    }

    public void calcDistance(double[] otherDims) {
      double dist = 0;
      for (int dim = 0; dim < this.dimensions.length; dim++) {
        dist += Math.pow((this.dimensions[dim] - otherDims[dim]), 2);
      }
      this.distance = dist;
    }

    @Override
    public String toString() {
      String output =  "Node containing: " + Arrays.toString(this.dimensions);
      if (this.children[0] != null) {
        output += "  { Left Child ->" + this.children[0].toString() + " }";
      } else {
        output += "  { No Left Child }";
      }
      if (this.children[1] != null) {
        output += "  { Right Child ->" + this.children[1].toString() + " }";
      } else {
        output += "  { No Right Child }";
      }
      return output;
    }

  }


}
