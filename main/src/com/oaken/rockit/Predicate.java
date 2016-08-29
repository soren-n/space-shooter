package com.oaken.rockit;

/*
  NOTE:
    Could be optimized by inlining function calls, and re-using some of
    the data that is being generated. Also sometimes the same function
    calls are being made twice or more.

    I.e. turn the implementations into DAGs rather than trees!
*/

public class Predicate {
  public static boolean below(Line l, Vec2 p) {
    Vec3 a = Vec3.request().set(l.v).sub(l.u);
    Vec3 b = Vec3.request().set(p).sub(l.u);
    Vec3 c = Vec3.cross(a, b);
    boolean result = c.z < 0;
    Vec3.release(c);
    Vec3.release(b);
    Vec3.release(a);
    return result;
  }

  public static boolean below(Line l, Circle c) {
    return below(l, c.center) && !intersect(l, c);
  }

  public static boolean below(Line l, Rectangle r) {
    return below(l, r.bl) && below(l, r.br) &&
           below(l, r.tl) && below(l, r.tr);
  }

  public static boolean above(Line l, Vec2 p) {
    Vec3 a = Vec3.request().set(l.v).sub(l.u);
    Vec3 b = Vec3.request().set(p).sub(l.u);
    Vec3 c = Vec3.cross(a, b);
    boolean result = c.z > 0;
    Vec3.release(c);
    Vec3.release(b);
    Vec3.release(a);
    return result;
  }

  public static boolean above(Line l, Circle c) {
    return above(l, c.center) && !intersect(l, c);
  }

  public static boolean above(Line l, Rectangle r) {
    return above(l, r.bl) && above(l, r.br) &&
           above(l, r.tl) && above(l, r.tr);
  }

  public static boolean envelope(Circle c, Vec2 p) {
    Vec2 d = Vec2.sub(p, c.center);
    boolean result = (c.radius * c.radius) > Vec2.dot(d, d);
    Vec2.release(d);
    return result;
  }

  public static boolean envelope(Circle c, Line l) {
    return envelope(c, l.u) && envelope(c, l.v);
  }

  public static boolean envelope(Circle a, Circle b) {
    return envelope(a, b.center) && !intersect(a, b);
  }

  public static boolean envelope(Circle c, Rectangle r) {
    return envelope(c, r.bl) &&
           envelope(c, r.br) &&
           envelope(c, r.tl) &&
           envelope(c, r.tr);
  }

  public static boolean envelope(Rectangle r, Vec2 p) {
    return below(r.left, p) &&
           above(r.right, p) &&
           below(r.top, p) &&
           above(r.bottom, p);
  }

  public static boolean envelope(Rectangle r, Line l) {
    return envelope(r, l.u) && envelope(r, l.v);
  }

  public static boolean envelope(Rectangle r, Circle c) {
    return envelope(r, c.center) && !intersect(r, c);
  }

  public static boolean envelope(Rectangle a, Rectangle b) {
    return envelope(a, b.bl) &&
           envelope(a, b.br) &&
           envelope(a, b.tl) &&
           envelope(a, b.tr);
  }

  public static boolean intersect(Line a, Line b) {
    return
      ((above(a, b.u) && below(a, b.v)) || (below(a, b.u) && above(a, b.v))) &&
      ((above(b, a.u) && below(b, a.v)) || (below(b, a.u) && above(b, a.v)));
  }

  public static boolean intersect(Line l, Circle c) {
    return l.distance(c.center) >= c.radius;
  }

  public static boolean intersect(Circle c, Line l) {
    return intersect(l, c);
  }

  public static boolean intersect(Line l, Rectangle r) {
    return intersect(l, r.left) ||
           intersect(l, r.right) ||
           intersect(l, r.top) ||
           intersect(l, r.bottom);
  }

  public static boolean intersect(Rectangle r, Line l) {
    return intersect(l, r);
  }

  public static boolean intersect(Rectangle r, Circle c) {
    return intersect(r.left, c) ||
           intersect(r.right, c) ||
           intersect(r.top, c) ||
           intersect(r.bottom, c);
  }

  public static boolean intersect(Circle c, Rectangle r) {
    return intersect(r, c);
  }

  public static boolean intersect(Circle a, Circle b) {
    Vec2 diff = Vec2.sub(a.center, b.center);
    float dist = Vec2.mag(diff);
    Vec2.release(diff);
    return Math.abs(a.radius - b.radius) <= dist && dist <= a.radius + b.radius;
  }

  public static boolean intersect(Rectangle a, Rectangle b) {
    return envelope(a, b.bl) || envelope(a, b.br) ||
           envelope(a, b.tl) || envelope(a, b.tr) ||
           envelope(b, a.bl) || envelope(b, a.br) ||
           envelope(b, a.tl) || envelope(b, a.tr);
  }

  public static boolean overlap(Rectangle r, Circle c) {
    return envelope(r, c.center) || intersect(r, c);
  }

  public static boolean overlap(Circle c, Rectangle r) {
    return overlap(r, c);
  }

  public static boolean overlap(Circle a, Circle b) {
    return envelope(a, b.center) || intersect(a, b);
  }

  public static boolean overlap(Rectangle a, Rectangle b) {
    return intersect(a, b) || envelope(a, b) || envelope(b, a);
  }
}
