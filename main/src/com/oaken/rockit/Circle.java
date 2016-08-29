package com.oaken.rockit;

public class Circle {
  public Vec2   center;
  public float  radius;

  public Circle(Vec2 center, float radius) {
    this.center = center;
    this.radius = radius;
  }

  public Circle(Circle other) {
    this.center = Vec2.request().set(other.center);
    this.radius = radius;
  }

  public boolean equals(Object obj) {
    if( this == obj) return true;
    if( !(obj instanceof Circle)) return false;
    Circle other = (Circle)obj;
    if( !this.center.equals(other.center)) return false;
    if( this.radius != other.radius) return false;
    return true;
  }

  public String toString() {
    return "{" + center.toString() + ", " + Float.toString(radius) + "}";
  }
}
