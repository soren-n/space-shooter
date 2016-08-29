package com.oaken.rockit;

public class AssultRifle implements Weapon {
  public static float BULLET_SPEED = 800.0f;
  public static float FIRE_RATE = 0.1f;
  public static int MAX_AMMO = 30;

  private float   _fire_delta;
  private int     _ammo_count;
  private Shape   _bullet_shape;

  public AssultRifle(Shape bullet_shape) {
    _fire_delta = FIRE_RATE;
    _ammo_count = MAX_AMMO;
    _bullet_shape = bullet_shape;
  }

  public void reset() {
    _fire_delta = FIRE_RATE;
    _ammo_count = MAX_AMMO;
  }

  public boolean ready() {
    return FIRE_RATE <= _fire_delta;
  }

  public boolean empty() {
    return _ammo_count == 0;
  }

  public void update(float delta) {
    _fire_delta += delta;
  }

  public void fire(Actor.Group group, Vec2 pos, Vec2 dir) {
    if( _ammo_count == 0) return;
    Bullet bullet = Bullet.request();
    bullet.set_shape(_bullet_shape);
    bullet.transform.pos.set(pos);
    bullet.transform.dir.set(dir);
    bullet.transform.dir.unit().mul(BULLET_SPEED).map();
    group.add(bullet);
    _ammo_count -= 1;
    _fire_delta = 0;
  }

  public void reload(int ammo) {
    _ammo_count = ammo;
  }
}
