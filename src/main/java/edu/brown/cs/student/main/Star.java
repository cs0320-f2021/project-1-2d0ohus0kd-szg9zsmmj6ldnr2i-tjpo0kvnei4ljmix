package edu.brown.cs.student.main;

import java.util.Objects;

public class Star {

  private final int id;
  private final String name;
  private final double x;
  private final double y;
  private final double z;
  private double dist = -1;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Star star = (Star) o;
    return id == star.id && Double.compare(star.x, x) == 0 &&
        Double.compare(star.y, y) == 0 && Double.compare(star.z, z) == 0 &&
        name.equals(star.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, x, y, z);
  }

  /**
   * Constructor for the Star class, which simply holds all info about a star.
   * @param id the id of the star
   * @param name the name of the star
   * @param x the x coordinate of the star
   * @param y the y coordinate of the star
   * @param z the z coordinate of the star
   */
  public Star(int id, String name, double x, double y, double z) {
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public String toString() {
    return "Star{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }

  public int getId() {
    return this.id;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double getZ() {
    return this.z;
  }

  public String getName() {
    return this.name;
  }

  public double getDist(){
    return this.dist;
  }

  public void setDist(double d){
    this.dist = d;
  }

}
