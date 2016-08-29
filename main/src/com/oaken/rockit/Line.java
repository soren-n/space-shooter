package com.oaken.rockit;

public class Line {
  public Vec2 u;
  public Vec2 v;

  public Line(Vec2 u, Vec2 v) {
    this.u = u;
    this.v = v;
  }

  public Line(Line other) {
    this.u = Vec2.request().set(other.u);
    this.v = Vec2.request().set(other.v);
  }

  public Vec2 projection(Vec2 p) {
    Vec2 a = Vec2.sub(p, u);
    Vec2 b = Vec2.sub(v, u);
    Vec2 result = Vec2.projection(a, b).add(u);
    Vec2.release(a);
    Vec2.release(b);
    return result;
  }

  public float distance(Vec2 p) {
    Vec2 temp1 = projection(p);
    Vec2 temp2 = Vec2.sub(p, temp1);
    float result = Vec2.mag(temp2);
    Vec2.release(temp1);
    Vec2.release(temp2);
    return result;
  }

  public void dispose() {
    Vec2.release(u);
    Vec2.release(v);
    u = null;
    v = null;
  }

  public boolean compare(Line other) {
    if( !u.equals(other.u)) return false;
    if( !v.equals(other.v)) return false;
    return true;
  }

  public boolean equals(Object obj) {
    if( this == obj) return true;
    if( !(obj instanceof Line)) return false;
    return compare((Line)obj);
  }

  public String toString() {
    return "[" + u.toString() + ", " + v.toString() + "]";
  }
}
