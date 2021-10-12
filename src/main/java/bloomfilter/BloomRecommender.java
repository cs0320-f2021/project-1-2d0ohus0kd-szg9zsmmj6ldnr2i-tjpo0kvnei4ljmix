package bloomfilter;

import java.util.List;

public interface BloomRecommender<T extends Item> {
  List<T> getTopKRecommendations(T item, int k);
}
