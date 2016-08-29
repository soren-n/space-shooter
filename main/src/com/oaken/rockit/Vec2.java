package com.oaken.rockit;

public class Vec2 {
  public float x;
  public float y;

  public Vec2() {
    _prev = null;
    _next = null;
    this.x = 0;
    this.y = 0;
  }

  public Vec2 reset() {
    this.x = 0;
    this.y = 0;
    return this;
  }

  public Vec2 set(Vec2 other) {
    this.x = other.x;
    this.y = other.y;
    return this;
  }

  public Vec2 set(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vec2 map() {
    this.x = Global.map_width(this.x);
    this.y = Global.map_width(this.y);
    return this;
  }

  public Vec2 unmap() {
    this.x = Global.unmap_width(this.x);
    this.y = Global.unmap_width(this.y);
    return this;
  }

  public Vec2 add(Vec2 other) {
    this.x += other.x;
    this.y += other.y;
    return this;
  }

  public Vec2 sub(Vec2 other) {
    this.x -= other.x;
    this.y -= other.y;
    return this;
  }

  public Vec2 mul(float a) {
    this.x *= a;
    this.y *= a;
    return this;
  }

  public Vec2 unit() {
    return this.mul(1.0f / this.mag());
  }

  public Vec2 rotate(float angle) {
    float theta = angle * Global.TO_RAD;
    float cos = (float)Math.cos(theta);
    float sin = (float)Math.sin(theta);
    return set(
      x * cos - y * sin,
      x * sin + y * cos
    );
  }

  public float mag() {
    return (float)Math.sqrt(x * x + y * y);
  }

  public boolean compare(Vec2 other) {
    if( this.x != other.x) return false;
    if( this.y != other.y) return false;
    return true;
  }

  public boolean equals(Object obj) {
    if( this == obj) return true;
    if( !(obj instanceof Vec2)) return false;
    return compare((Vec2)obj);
  }

  public String toString() {
    return "(" + Float.toString(x) + ", " + Float.toString(y) + ")";
  }

  public static Vec2 unit(Vec2 p) {
    return Vec2.request().set(p).unit();
  }

  public static Vec2 add(Vec2 p, Vec2 q) {
    return Vec2.request().set(p).add(q);
  }

  public static Vec2 sub(Vec2 p, Vec2 q) {
    return Vec2.request().set(p).sub(q);
  }

  public static Vec2 mul(Vec2 p, float a) {
    return Vec2.request().set(p).mul(a);
  }

  public static Vec2 projection(Vec2 p, Vec2 q) {
    return Vec2.mul(q, dot(p, q) / dot(q, q));
  }

  public static Vec2 rotate(Vec2 p, float angle) {
    return Vec2.request().set(p).rotate(angle);
  }

  public static float angle(Vec2 p, Vec2 q) {
    Vec2 _p = Vec2.unit(p);
    Vec2 _q = Vec2.unit(q);
    float result = (float)Math.acos(Vec2.dot(_p, _q)) * Global.TO_DEG;
    release(_p);
    release(_q);
    return result;
  }

  public static float dot(Vec2 p, Vec2 q) {
    return p.x * q.x + p.y * q.y;
  }

  public static float mag(Vec2 p) {
    return p.mag();
  }

  // ------------------------- Object Pool -----------------------------

  private Vec2 _prev;
  private Vec2 _next;
  private static final Vec2 _free = new Vec2();

  private static int _count = 0;

  private static void _remove(Vec2 p) {
    p._prev._next = p._next;
    if( p._next != null)
      p._next._prev = p._prev;
    p._prev = null;
    p._next = null;
  }

  public static void release(Vec2 p) {
    p._prev = _free;
    p._next = _free._next;
    if( _free._next != null)
      _free._next._prev = p;
    _free._next = p;
    // System.out.println("#Vec2 = " + --_count);
  }

  public static Vec2 request() {
    Vec2 result = null;
    if( _free._next == null) {
      result = new Vec2();
    } else {
      result = _free._next;
      _remove(result);
      result.reset();
    }
    // System.out.println("#Vec2 = " + ++_count);
    return result;
  }
}
