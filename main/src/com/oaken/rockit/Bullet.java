package com.oaken.rockit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends Actor {
  public static final int POOL_INIT_COUNT = 75;

  private Shape      _shape;
  public Transform   transform;

  private Bullet() {
    _shape = Shape.null_object;
    transform = new Transform();
  }

  public void set_shape(Shape shape) {
    _shape = shape;
    bounds.set_size(
      _shape.get_width(),
      _shape.get_height()
    );
  }

  public void reset() {
    super.reset();
    transform.reset();
    _shape = Shape.null_object;
  }

  public void draw(SpriteBatch batch) {
    _shape.draw(batch, transform.pos, transform.rot);
  }

  public void update(float delta) {
    Vec2 _delta = Vec2.mul(transform.dir, delta);
    Vec2 _pos = Vec2.add(transform.pos, _delta);
    if( !transform.pos.compare(_pos)) {
      bounds.set_position(
        _pos.x - (bounds.w / 2),
        _pos.y - (bounds.h / 2)
      );
      transform.pos.set(_pos);
      if( !Predicate.overlap(Global.simulation_bounds, bounds))
        dead = true;
    }
    Vec2.release(_delta);
    Vec2.release(_pos);
  }

  public void collide(Actor other) {
    dead = true;
  }

  public void collect() {
    reset();
    Bullet.release(this);
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

  private static void _remove(Bullet bullet) {
    bullet.prev.next = bullet.next;
    if( bullet.next != null)
      bullet.next.prev = bullet.prev;
    bullet.prev = null;
    bullet.next = null;
  }

  public static void reserve(int count) {
    for(; _capacity < count; ++_capacity)
      release(new Bullet());
  }

  public static void release(Bullet bullet) {
    bullet.prev = _free;
    bullet.next = _free.next;
    if( _free.next != null)
      _free.next.prev = bullet;
    _free.next = bullet;
  }

  public static Bullet request() {
    Bullet result = null;
    if( _free.next == null) {
      result = new Bullet();
    } else {
      result = (Bullet)_free.next;
      _remove(result);
    }
    return result;
  }
}
