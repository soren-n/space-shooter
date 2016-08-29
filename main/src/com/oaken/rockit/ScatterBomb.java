package com.oaken.rockit;

public class ScatterBomb implements Weapon {
  public static float BULLET_SPEED = 800.0f;
  public static final float FIRE_RATE = 0.5f;
  public static final int MAX_AMMO = 5;

  private float   _fire_delta;
  private int     _ammo_count;
  private float   _offset;
  private Shape   _bullet_shape;

  public ScatterBomb(Shape bullet_shape) {
    _fire_delta = FIRE_RATE;
    _ammo_count = MAX_AMMO;
    _offset = 0;
    _bullet_shape = bullet_shape;
  }

  public void reset() {
    _fire_delta = FIRE_RATE;
    _ammo_count = MAX_AMMO;
    _offset = 0;
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
    Vec2 origin = Vec2.request().set(0, BULLET_SPEED).map();
    for( int i = 0; i < 36; ++i) {
      float angle = (i * 10) + _offset;
      Bullet bullet = Bullet.request();
      bullet.set_shape(_bullet_shape);
      bullet.transform.pos.set(pos);
      bullet.transform.dir.set(origin).rotate(angle);
      bullet.transform.rot = angle;
      group.add(bullet);
    }
    Vec2.release(origin);
    _offset = _offset > 0 ? 0 : 5;
    _ammo_count -= 1;
    _fire_delta = 0;
  }

  public void reload(int ammo) {
    _ammo_count = ammo;
  }
}
