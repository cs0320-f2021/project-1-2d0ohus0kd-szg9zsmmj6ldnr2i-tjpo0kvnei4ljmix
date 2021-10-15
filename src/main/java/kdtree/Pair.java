package kdtree;

//Simple dumb immutable pair class
public class Pair<A, B> {
  public final A first;
  public final B second;

  public Pair(A a, B b) {
    this.first = a;
    this.second = b;
  }
}