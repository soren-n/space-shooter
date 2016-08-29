package com.oaken.rockit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship extends Actor {
  public static final float DRAG = 0.95f;
  public static final float SPEED = 10.0f;
  public static final float SHIELD_TIME = 10.0f;

  public int          health;
  public int          score;
  public float        shield;
  public Transform    transform;
  public Weapon       weapon;
  public Weapon       pistol;
  public Weapon       shotgun;
  public Weapon       assult;
  public Weapon       bomb;
  public Actor.Group  bullets;

  public Ship() {
    health = 20;
    score = 0;
    shield = 0.0f;
    transform = new Transform();
    pistol = new Pistol(Asset.ship_bullet_shape);
    shotgun = new Shotgun(Asset.ship_bullet_shape);
    assult = new AssultRifle(Asset.ship_bullet_shape);
    bomb = new ScatterBomb(Asset.ship_bullet_shape);
    bullets = new Actor.Group();
    weapon = pistol;
    bounds.set_size(
      Asset.ship_shape.get_width(),
      Asset.ship_shape.get_height()
    );
  }

  public void draw(SpriteBatch batch) {
    bullets.draw(batch);
    Asset.ship_shape.draw(batch, transform.pos, 0);
    if( shield > 0.0f)
      Asset.ship_shield_shape.draw(batch, transform.pos, 0);
  }

  public void update(float delta) {
    transform.dir.mul(DRAG);
    transform.pos.add(transform.dir);
    if( weapon.ready()) weapon.fire(bullets, transform.pos, Global.UP);
    if( weapon.empty()) weapon = pistol;
    weapon.update(delta);
    bounds.set_position(
      transform.pos.x - (bounds.w / 2),
      transform.pos.y - (bounds.h / 2)
    );
    bullets.update(delta);
    shield -= delta;
    if( shield < 0.0f)
      shield = 0.0f;
  }

  public void collide(Actor other) {
    if( other instanceof Coin) return;
    if( other instanceof PowerUp) return;
    if( shield > 0.0f) return;
    health -= 1;
    if( health <= 0)
      dead = true;
  }

  public void dispose() {
    transform.dispose();
    bullets.dispose();
  }
}
