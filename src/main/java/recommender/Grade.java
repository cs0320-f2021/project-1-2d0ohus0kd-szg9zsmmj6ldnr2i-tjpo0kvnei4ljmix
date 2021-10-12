package recommender;

public enum Grade {
  FIRSTYEAR(1), SECONDYEAR(2), THIRDYEAR(3), FOURTHYEAR(4),
  MORETHANFOURTHYEAR(5), GRAD(6), ABOVEGRAD(7);

  private int value;
  Grade(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
