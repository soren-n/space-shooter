package com.oaken.rockit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Asset {
  public static BitmapFont  system_font;
  public static Shape       ship_shape;
  public static Shape       ship_bullet_shape;
  public static Shape       ship_shield_shape;
  public static Shape       enemy_shape;
  public static Shape       enemy_bullet_shape;
  public static Shape       coin_shape;
  public static Shape       start_button_shape;
  public static Shape       powerup_shape;

  public static void load_all() {
    system_font = new BitmapFont(
      Gdx.files.internal("assets/fonts/inconsolata.fnt"),
      Gdx.files.internal("assets/fonts/inconsolata.png"),
      false
    );

    ship_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/ship.png"))
    );

    ship_bullet_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/ship_bullet.png"))
    );

    ship_shield_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/ship_shield.png"))
    );

    enemy_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/enemy.png"))
    );

    enemy_bullet_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/enemy_bullet.png"))
    );

    coin_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/coin.png"))
    );

    start_button_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/start_button.png"))
    );

    powerup_shape = new TextureShape(
      new Texture(Gdx.files.internal("assets/images/powerup.png"))
    );
  }
}
