package com.oaken.rockit;

public class Transform {
  public Vec2   pos;
  public Vec2   dir;
  public float  rot;

  public Transform() {
    pos = Vec2.request();
    dir = Vec2.request();
    rot = 0;
  }

  public void reset() {
    pos.reset();
    dir.reset();
    rot = 0;
  }

  public void set(Transform other) {
    pos.set(other.pos);
    dir.set(other.dir);
    rot = other.rot;
  }

  public void dispose() {
    Vec2.release(pos);
    Vec2.release(dir);
    pos = null;
    dir = null;
  }
}
