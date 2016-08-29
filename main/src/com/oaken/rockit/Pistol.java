package com.oaken.rockit;

public class Pistol implements Weapon {
  public static float BULLET_SPEED = 800.0f;
  public static final float FIRE_RATE = 0.5f;

  private float   _fire_delta;
  private Shape   _bullet_shape;

  public Pistol(Shape bullet_shape) {
    _fire_delta = FIRE_RATE;
    _bullet_shape = bullet_shape;
  }

  public void reset() {
    _fire_delta = FIRE_RATE;
  }

  public boolean ready() {
    return FIRE_RATE <= _fire_delta;
  }

  public boolean empty() {
    return false;
  }

  public void update(float delta) {
    _fire_delta += delta;
  }

  public void fire(Actor.Group group, Vec2 pos, Vec2 dir) {
    Bullet bullet = Bullet.request();
    bullet.set_shape(_bullet_shape);
    bullet.transform.pos.set(pos);
    bullet.transform.dir.set(dir);
    bullet.transform.dir.unit().mul(BULLET_SPEED).map();
    group.add(bullet);
    _fire_delta = 0;
  }

  public void reload(int ammo) {
    //
  }
}
