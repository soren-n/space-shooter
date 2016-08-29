package com.oaken.rockit;

public class Shotgun implements Weapon {
  public static float BULLET_SPEED = 800.0f;
  public static float FIRE_RATE = 0.5f;
  public static int MAX_AMMO = 10;

  private float   _fire_delta;
  private int     _ammo_count;
  private Shape   _bullet_shape;

  public Shotgun(Shape bullet_shape) {
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

  private void spawn_bullet(Actor.Group group, Vec2 pos, Vec2 dir, float rot) {
    Bullet bullet = Bullet.request();
    bullet.set_shape(_bullet_shape);
    bullet.transform.pos.set(pos);
    bullet.transform.dir.set(dir);
    bullet.transform.rot = rot;
    group.add(bullet);
  }

  public void fire(Actor.Group group, Vec2 pos, Vec2 dir) {
    if( _ammo_count == 0) return;
    Vec2 init = Vec2.request();
    Vec2 _dir = Vec2.request();
    init.set(dir).unit().mul(BULLET_SPEED).map();
    spawn_bullet(group, pos, _dir.set(init).rotate(15), 15);
    spawn_bullet(group, pos, _dir.set(init), 0);
    spawn_bullet(group, pos, _dir.set(init).rotate(-15), -15);
    Vec2.release(init);
    Vec2.release(_dir);
    _ammo_count -= 1;
    _fire_delta = 0;
  }

  public void reload(int ammo) {
    _ammo_count = ammo;
  }
}
