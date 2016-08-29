package com.oaken.rockit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class PowerUp extends Actor {
  public static final int POOL_INIT_COUNT = 10;
  public static final float SPEED = 150.0f;

  public Transform transform;

  private PowerUp() {
    transform = new Transform();
    transform.dir.set(Global.DOWN).mul(SPEED).map();
    bounds.set_size(
      Asset.powerup_shape.get_width(),
      Asset.powerup_shape.get_height()
    );
  }

  public void reset() {
    super.reset();
    transform.reset();
    transform.dir.set(Global.DOWN).mul(SPEED).map();
    bounds.set_size(
      Asset.powerup_shape.get_width(),
      Asset.powerup_shape.get_height()
    );
  }

  public void draw(SpriteBatch batch) {
    Asset.powerup_shape.draw(batch, transform.pos, transform.rot);
  }

  public void update(float delta) {
    Vec2 _delta = Vec2.mul(transform.dir, delta);
    transform.pos.add(_delta);
    bounds.set_position(
      transform.pos.x - (bounds.w / 2),
      transform.pos.y - (bounds.h / 2)
    );
    if( !Predicate.overlap(Global.simulation_bounds, bounds))
      dead = true;
    Vec2.release(_delta);
  }

  public void collide(Actor other) {
    Ship ship = (Ship)other;
    if( ship.weapon instanceof Pistol) {
      int pick = MathUtils.random(10);
      if( pick == 0) {
        ship.weapon = ship.bomb;
        ship.weapon.reload(5);
      } else if( pick <= 2) {
        ship.weapon = ship.assult;
        ship.weapon.reload(30);
      } else if( pick <= 4) {
        ship.shield = Ship.SHIELD_TIME;
      } else if( pick <= 6) {
        ship.health += 5;
      } else {
        ship.weapon = ship.shotgun;
        ship.weapon.reload(10);
      }
    }
    dead = true;
  }

  public void collect() {
    reset();
    PowerUp.release(this);
  }

  public void dispose() {
    // Do nothing
  }

  protected void finalize() throws Throwable {
    super.dispose();
    transform.dispose();
    transform = null;
    --_capacity;
  }

  // ------------------------------ Object Pool ------------------------------

  private static final Actor _free = new Actor();
  private static int _capacity = 0;

  private static void _remove(PowerUp powerup) {
    powerup.prev.next = powerup.next;
    if( powerup.next != null)
      powerup.next.prev = powerup.prev;
    powerup.prev = null;
    powerup.next = null;
  }

  public static void reserve(int count) {
    for(; _capacity < count; ++_capacity)
      release(new PowerUp());
  }

  public static void release(PowerUp powerup) {
    powerup.prev = _free;
    powerup.next = _free.next;
    if( _free.next != null)
      _free.next.prev = powerup;
    _free.next = powerup;
  }

  public static PowerUp request() {
    PowerUp result = null;
    if( _free.next == null) {
      result = new PowerUp();
    } else {
      result = (PowerUp)_free.next;
      _remove(result);
    }
    return result;
  }
}
