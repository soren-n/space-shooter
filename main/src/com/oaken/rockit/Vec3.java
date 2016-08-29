package com.oaken.rockit;

public class Vec3 {
  public float x;
  public float y;
  public float z;

  private Vec3() {
    _prev = null;
    _next = null;
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  public Vec3 reset() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    return this;
  }

  public Vec3 set(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }

  public Vec3 set(Vec2 other) {
    this.x = other.x;
    this.y = other.y;
    this.z = 0;
    return this;
  }

  public Vec3 set(Vec3 other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    return this;
  }

  public Vec3 add(Vec2 other) {
    this.x += other.x;
    this.y += other.y;
    return this;
  }

  public Vec3 add(Vec3 other) {
    this.x += other.x;
    this.y += other.y;
    this.z += other.z;
    return this;
  }

  public Vec3 sub(Vec2 other) {
    this.x -= other.x;
    this.y -= other.y;
    return this;
  }

  public Vec3 sub(Vec3 other) {
    this.x -= other.x;
    this.y -= other.y;
    this.z -= other.z;
    return this;
  }

  public Vec3 mul(float a) {
    this.x *= a;
    this.y *= a;
    this.z *= a;
    return this;
  }

  public boolean compare(Vec3 other) {
    if( this.x != other.x) return false;
    if( this.y != other.y) return false;
    if( this.z != other.z) return false;
    return true;
  }

  public boolean equals(Object obj) {
    if( this == obj) return true;
    if( !(obj instanceof Vec3)) return false;
    return compare((Vec3)obj);
  }

  public String toString() {
    return "(" + Float.toString(x) +
           ", " + Float.toString(y) +
           ", " + Float.toString(z) + ")";
  }

  public static Vec3 add(Vec3 p, Vec3 q) {
    return Vec3.request().set(p).add(q);
  }

  public static Vec3 sub(Vec3 p, Vec3 q) {
    return Vec3.request().set(p).sub(q);
  }

  public static Vec3 mul(Vec3 p, float a) {
    return Vec3.request().set(p).mul(a);
  }

  public static Vec3 cross(Vec3 u, Vec3 v) {
    return Vec3.request().set(
      u.y * v.z - u.z * v.y,
      u.z * v.x - u.x * v.z,
      u.x * v.y - u.y * v.x
    );
  }

  public static float dot(Vec3 p, Vec3 q) {
    return p.x * q.x + p.y * q.y + p.z * q.z;
  }

  public static float mag(Vec3 p) {
    return (float)Math.sqrt(dot(p, p));
  }

  public static Vec3 projection(Vec3 p, Vec3 q) {
    return Vec3.mul(q, dot(p, q) / dot(q, q));
  }

  // ------------------------- Object Pool -----------------------------

  private Vec3 _prev;
  private Vec3 _next;
  private static final Vec3 _free = new Vec3();

  private static void _remove(Vec3 p) {
    p._prev._next = p._next;
    if( p._next != null)
      p._next._prev = p._prev;
    p._prev = null;
    p._next = null;
  }

  public static void release(Vec3 p) {
    p._prev = _free;
    p._next = _free._next;
    if( _free._next != null)
      _free._next._prev = p;
    _free._next = p;
  }

  public static Vec3 request() {
    Vec3 result = null;
    if( _free._next == null) {
      result = new Vec3();
    } else {
      result = _free._next;
      _remove(result);
      result.reset();
    }
    return result;
  }
}
