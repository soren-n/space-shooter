package com.oaken.rockit;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends Actor {
  private static final int COIN_VALUE = 10;
  private static final float SPEED = 300.0f;
  private static final float SPEED_FACTOR = 0.8f;

  public Transform    transform;
  public Ship         target;
  public int          health;
  public float        time;
  public Weapon       weapon;

  public static final Actor.Group powerups = new Actor.Group();
  public static final Actor.Group bullets = new Actor.Group();
  public static final Actor.Group coins = new Actor.Group();

  private Enemy() {
    transform = new Transform();
    transform.dir.set(0, -SPEED).map();
    target = null;
    health = 5;
    time = 0.25f;
    weapon = new Pistol(Asset.enemy_bullet_shape);
    bounds.set_size(
      Asset.enemy_shape.get_width(),
      Asset.enemy_shape.get_height()
    );
  }

  public void reset() {
    super.reset();
    transform.reset();
    transform.dir.set(0, -SPEED).map();
    target = null;
    health = 5;
    time = 0.25f;
    weapon.reset();
    bounds.set_size(
      Asset.enemy_shape.get_width(),
      Asset.enemy_shape.get_height()
    );
  }

  public void draw(SpriteBatch batch) {
    Asset.enemy_shape.draw(
      batch,
      transform.pos,
      transform.rot
    );
  }

  public void update(float delta) {

    // Animation
    float angle = 45.0f * (float)Math.sin(time * Global.SCALAR_TO_RAD);
    Vec2 _pos = Vec2.rotate(transform.dir, angle).mul(delta).add(transform.pos);
    if( !transform.pos.compare(_pos)) {
      bounds.set_position(
        _pos.x - (bounds.w / 2),
        _pos.y - (bounds.h / 2)
      );
      transform.pos.set(_pos);
      if( !Predicate.overlap(Global.simulation_bounds, bounds)) {
        target.score -= 5;
        dead = true;
      }
    }
    Vec2.release(_pos);
    time += delta * SPEED_FACTOR;
    if( time > 1) time = 0;

    // Weapon
    if( weapon.ready())
      weapon.fire(bullets, transform.pos, Global.DOWN);
    weapon.update(delta);
  }

  public void spawn_coins() {
    float half_width = Asset.enemy_shape.get_width() / 2;
    float half_height = Asset.enemy_shape.get_height() / 2;
    for( int i = 0; i < COIN_VALUE; ++i) {
      Coin coin = Coin.request();
      coin.target = target;
      coin.transform.pos.set(
        transform.pos.x + MathUtils.random(-half_width, half_width),
        transform.pos.y + MathUtils.random(-half_height, half_height)
      );
      coin.transform.dir.set(coin.transform.pos)
                        .sub(transform.pos)
                        .unit().mul(4);
      coins.add(coin);
    }
  }

  public void spawn_powerup() {
    PowerUp powerup = PowerUp.request();
    powerup.transform.pos.set(transform.pos);
    powerups.add(powerup);
  }

  public void collide(Actor other) {
    health -= 1;
    if( health <= 0) {
      spawn_powerup();
      spawn_coins();
      dead = true;
    }
  }

  public void collect() {
    reset();
    Enemy.release(this);
  }

  public void dispose() {
    powerups.dispose();
    bullets.dispose();
    coins.dispose();
  }

  protected void finalize() throws Throwable {
    super.dispose();
    transform.dispose();
    transform = null;
  }

  // ------------------------------ Object Pool ------------------------------

  private static final Actor _free = new Actor();

  private static void _remove(Enemy enemy) {
    enemy.prev.next = enemy.next;
    if( enemy.next != null)
      enemy.next.prev = enemy.prev;
    enemy.prev = null;
    enemy.next = null;
  }

  public static void release(Enemy enemy) {
    enemy.prev = _free;
    enemy.next = _free.next;
    if( _free.next != null)
      _free.next.prev = enemy;
    _free.next = enemy;
  }

  public static Enemy request() {
    Enemy result = null;
    if( _free.next == null) {
      result = new Enemy();
    } else {
      result = (Enemy)_free.next;
      _remove(result);
    }
    return result;
  }
}
