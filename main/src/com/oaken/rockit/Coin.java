package com.oaken.rockit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coin extends Actor {
  public static final int POOL_INIT_COUNT = 50;
  public static final float ATTRACTION = 20.0f;
  public static final float DRAG = 0.975f;

  public Ship       target;
  public Transform  transform;

  private Coin() {
    target = null;
    transform = new Transform();
  }

  public void reset() {
    super.reset();
    target = null;
    transform.reset();
  }

  public void draw(SpriteBatch batch) {
    Asset.coin_shape.draw(batch, transform.pos, 0);
  }

  public void update(float delta) {
    Vec2 temp1 = Vec2.sub(target.transform.pos, transform.pos)
                     .unit()
                     .mul(ATTRACTION * delta)
                     .map();
    transform.dir.add(temp1).mul(DRAG);
    transform.pos.add(transform.dir);
    bounds.set_position(
      transform.pos.x - (bounds.w / 2),
      transform.pos.y - (bounds.h / 2)
    );
    Vec2.release(temp1);
  }

  public void collide(Actor other) {
    target.score += 1;
    dead = true;
  }

  public void collect() {
    reset();
    Coin.release(this);
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

  private static void _remove(Coin coin) {
    coin.prev.next = coin.next;
    if( coin.next != null)
      coin.next.prev = coin.prev;
    coin.prev = null;
    coin.next = null;
  }

  public static void reserve(int count) {
    for(; _capacity < count; ++_capacity)
      release(new Coin());
  }

  public static void release(Coin coin) {
    coin.prev = _free;
    coin.next = _free.next;
    if( _free.next != null)
      _free.next.prev = coin;
    _free.next = coin;
  }

  public static Coin request() {
    Coin result = null;
    if( _free.next == null) {
      result = new Coin();
    } else {
      result = (Coin)_free.next;
      _remove(result);
    }
    return result;
  }
}
