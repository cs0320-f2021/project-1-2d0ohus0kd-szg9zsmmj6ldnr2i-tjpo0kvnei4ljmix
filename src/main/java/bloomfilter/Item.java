package bloomfilter;

import java.util.List;

public interface Item {
  List<String> getVectorRepresentation();
  String getId();
}
