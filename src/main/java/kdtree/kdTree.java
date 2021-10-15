package kdtree;

import com.google.common.math.DoubleMath;
import org.checkerframework.checker.units.qual.A;

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
    this.loadData(dataToLoad, getters, true);
  }

  private void constructTree(kdItem<T> rootNode, List<kdItem<T>> remainingItems, int dim) {
    assert (rootNode.getChildrenSortDim() == dim);
    double rootVal = rootNode.getDimension(dim);
    //Partition the space into left and right sides with the rootVal being the separator
    List<kdItem<T>> leftSide = new ArrayList<>();
    List<kdItem<T>> rightSide = new ArrayList<>();
    for (kdItem<T> currItem : remainingItems) {
      if (rootVal > currItem.getDimension(dim)) {
        leftSide.add(currItem);
      } else {
        rightSide.add(currItem);
      }
    }

    int nextdim = (dim + 1) % this.numDims;
    if (!leftSide.isEmpty()){
      sortByDim(leftSide, dim);
      kdItem<T> leftChild = leftSide.get(leftSide.size() / 2);
      leftChild.setChildrenSortDim(nextdim);
      rootNode.getChildren()[0] = leftChild;
      leftSide.remove(leftChild);
      constructTree(leftChild, leftSide, nextdim);
    } else {
      //side is empty, child should be null
      rootNode.getChildren()[0] = null;
    }

    if (!rightSide.isEmpty()) {
      sortByDim(rightSide, dim);
      kdItem<T> rightChild = rightSide.get(rightSide.size() / 2);
      rightChild.setChildrenSortDim(nextdim);
      rootNode.getChildren()[1] = rightChild;
      rightSide.remove(rightChild);
      constructTree(rightChild, rightSide, nextdim);
    } else {
      rootNode.getChildren()[1] = null;
    }
  }

  public void loadData(Collection<T> dataToLoad, List<kdGetter<T>> getters, boolean normalize) {
    this.size = dataToLoad.size();
    this.normalize = normalize;
    this.kdGetters = new ArrayList<>(getters);
    this.listData = normalizeData(dataToLoad, getters);
    //listData is an ArrayList of kdItem objects, which all contain normalized fields

    //Set up the root, then call constructTree recursively.
    //dimension 0, first layer.
    ArrayList<kdItem<T>> items = new ArrayList<>(this.listData); //make copy, list will be mutated.
    sortByDim(items, 0);
    this.root = items.get(items.size() / 2);
    this.root.setChildrenSortDim(0);
    items.remove(this.root);
    constructTree(this.root, items, 0);
  }

  /** Basically a wrapper for list.sort with a dimension comparator
   */
  private void sortByDim(List<kdItem<T>> data, int dim) {
    data.sort(new Comparator<kdItem<T>>() {
      @Override
      public int compare(kdItem<T> o1, kdItem<T> o2) {
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

  @Override
  public List<T> nearestNeighbors(int n, T targetRaw) {
    if (n > this.size) {
      n = this.size;
    }
    if (n == 0) {
      return new ArrayList<T>(); //return empty list if asking for 0 neighbors
    }
    //convert target to a kdItem object
    double[] dims = new double[this.numDims];
    for (int i = 0; i < this.numDims; i++) {
      dims[i] = this.normalizeSingleValue(this.kdGetters.get(i).getValue(targetRaw), i);
      //In this case, it's actually okay that if the normalized value is above 1 (or negative)
      //Because the target value might be more extreme than any of the kdtree data
    }
    kdItem<T> target = new kdItem<>(targetRaw, dims);
    kdItem<T>[] best = new kdItem[n];

    //Setup stack and related variables
    double distanceThreshold = Double.MAX_VALUE;
    Stack<Pair<kdItem<T>, Double>> toCheck = new Stack<>();
    assert (this.root != null);
    toCheck.push(new Pair<>(this.root, Double.MAX_VALUE));

    //Start searching
    while (!(toCheck.empty())) {
      Pair<kdItem<T>, Double> currPair = toCheck.pop();
      kdItem<T> currItem = currPair.first;
      double bestPossibleDistCurrItem = currPair.second;
      if (bestPossibleDistCurrItem > distanceThreshold) {
        //This may have been a worthy branch of the tree to check out when it was originally pushed into the stack,
        //Now, it's no longer viable because the best it can get is higher than our threshold
        continue;
      }
      int currDim = currItem.getChildrenSortDim();
      int idealSide;
      if (target.getDimension(currDim) < currItem.getDimension(currDim)) {
        //Go left if the target is less than current item
        idealSide = 0;
      } else {
        //Go right
        idealSide = 1;
      }

      int nonIdealSide = (idealSide == 0) ? 1 : 0; //opposite of the ideal side
      currItem.calcDistance(target.getDimensionArray());
      double currItemDist = currItem.getDistance();
      if (currItemDist < distanceThreshold) {
        distanceThreshold = bubbleIntoArray(best, currItem); //Insert into array of best values
        //bubbleIntoArray returns the new distanceThreshold
      }

      //Now calculate which of the branches we have to go down
      //Pushing the non-ideal child first is important, since the stack is FIFO
      //This means the ideal child will get checked first
      double bestNonIdealDist = Math.pow(target.getDimension(currDim) - currItem.getDimension(currDim), 2);
      if (bestNonIdealDist < distanceThreshold) {
        //This means the other child could have a good value, we need to add it if it's there.
        if (currItem.getChildren()[nonIdealSide] != null) {
          toCheck.push(new Pair<>(currItem.getChildren()[nonIdealSide], bestNonIdealDist));
        }
      }

      //Always push the 'ideal' child into the stack if it's there
      if (currItem.getChildren()[idealSide] != null) {
        toCheck.push(new Pair<>(currItem.getChildren()[idealSide], distanceThreshold));
      }
    }
    //best array now contains the best (closest to target) values in the KDtree
    ArrayList<T> bestList = new ArrayList<>();
    for (kdItem<T> elm : best) {
      if (elm != null) {
        //get the original object back from the kdItem class
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
  private double bubbleIntoArray(kdItem<T>[] best, kdItem<T> toInsert) {
    kdItem<T> bubble = toInsert;
    for (int idx = 0; idx < best.length; idx++) {
      if (best[idx] == null) {
        //array is not yet fully filled
        best[idx] = bubble;
        return Double.MAX_VALUE;
      }
      if (best[idx].getDistance() > bubble.getDistance()) {
        kdItem<T> tmp = best[idx];
        best[idx] = bubble;
        bubble = tmp;
      }
    }
    //Getting here means array is fully filled
    return best[best.length - 1].getDistance();
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

  @SuppressWarnings("InnerClassMayBeStatic") //It can't be Static, IntelliJ is just being dumb
  class kdItem<kdObject> {
    private final kdObject originalItem;
    private final double[] dimensions;
    private final kdItem<kdObject>[] children = new kdItem[2];
    private Integer childrenSortDim = null; //dimension which the object will sort its children by
    private Double distance = null;

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

    public void calcDistance(double[] otherDims) {
      double dist = 0;
      for (int dim = 0; dim < this.dimensions.length; dim++) {
        dist += Math.pow((this.dimensions[dim] - otherDims[dim]), 2);
      }
      this.distance = dist;
    }

    public double getDistance() {
      return this.distance;
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
