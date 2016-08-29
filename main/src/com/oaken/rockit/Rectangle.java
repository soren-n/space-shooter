package com.oaken.rockit;

public class Rectangle {
  public float  x;
  public float  y;
  public float  w;
  public float  h;
  public Vec2   bl;
  public Vec2   br;
  public Vec2   tl;
  public Vec2   tr;
  public final Line left;
  public final Line right;
  public final Line top;
  public final Line bottom;

  public Rectangle() {
    x = 0;
    y = 0;
    w = 0;
    h = 0;
    bl = Vec2.request();
    br = Vec2.request();
    tl = Vec2.request();
    tr = Vec2.request();
    left = new Line(bl, tl);
    right = new Line(br, tr);
    top = new Line(tl, tr);
    bottom = new Line(bl, br);
  }

  public Rectangle(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    bl = Vec2.request().set(x, y);
    br = Vec2.request().set(x + w, y);
    tl = Vec2.request().set(x, y + h);
    tr = Vec2.request().set(x + w, y + h);
    left = new Line(bl, tl);
    right = new Line(br, tr);
    top = new Line(tl, tr);
    bottom = new Line(bl, br);
  }

  public Rectangle(Rectangle other) {
    bl = Vec2.request().set(other.bl);
    br = Vec2.request().set(other.br);
    tl = Vec2.request().set(other.tl);
    tr = Vec2.request().set(other.tr);
    left = new Line(bl, tl);
    right = new Line(br, tr);
    top = new Line(tl, tr);
    bottom = new Line(bl, br);
  }

  public void reset() {
    x = 0;
    y = 0;
    w = 0;
    h = 0;
    bl.reset();
    br.reset();
    tl.reset();
    tr.reset();
  }

  private void _update() {
    bl.set(x, y);
    br.set(x + w, y);
    tl.set(x, y + h);
    tr.set(x + w, y + h);
  }

  public Rectangle add_padding(float a) {
    this.x -= a;
    this.y -= a;
    this.w += 2 * a;
    this.h += 2 * a;
    _update();
    return this;
  }

  public Rectangle set_size(float w, float h) {
    if( this.w == w && this.h == h) return this;
    this.w = w;
    this.h = h;
    _update();
    return this;
  }

  public Rectangle set_position(float x, float y) {
    if( this.x == x && this.y == y) return this;
    this.x = x;
    this.y = y;
    _update();
    return this;
  }

  public void dispose() {
    left.dispose();
    right.dispose();
    top.dispose();
    bottom.dispose();
  }

  public boolean compare(Rectangle other) {
    if( !bl.compare(other.bl)) return false;
    if( !br.compare(other.br)) return false;
    if( !tl.compare(other.tl)) return false;
    if( !tr.compare(other.tr)) return false;
    return true;
  }

  public boolean equals(Object obj) {
    if( this == obj) return true;
    if( !(obj instanceof Rectangle)) return false;
    return compare((Rectangle)obj);
  }

  public String toString() {
    return "[" + bl.toString() +
           ", " + br.toString() +
           ", " + tl.toString() +
           ", " + tr.toString() + "]";
  }
}
