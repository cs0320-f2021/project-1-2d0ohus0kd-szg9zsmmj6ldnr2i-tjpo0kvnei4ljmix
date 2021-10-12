package recommender;

import java.util.HashMap;
import java.util.Map;

public enum Grade {
  FIRSTYEAR(0), SECONDYEAR(1), THIRDYEAR(2), FOURTHYEAR(3),
  MORETHANFOURTHYEAR(4), GRAD(5), ABOVEGRAD(6);

  //ENUM enhancements from: https://codingexplained.com/coding/java/enum-to-integer-and-integer-to-enum

  private static Map gradeMap = new HashMap<>();
  private int value;

  Grade(int value) {
    this.value = value;
  }

  static {
    for (Grade g : Grade.values()) {
      gradeMap.put(g.value, g);
    }
  }

  public static Grade valueOf(int gradeNum) {
    return (Grade) gradeMap.get(gradeNum);
  }


  public int getValue() {
    return this.value;
  }
}
