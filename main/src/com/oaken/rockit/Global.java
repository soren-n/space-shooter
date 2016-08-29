package com.oaken.rockit;

import com.badlogic.gdx.Gdx;

public class Global {
  public static final Vec2 UP = new Vec2().set(0, 1);
  public static final Vec2 DOWN = new Vec2().set(0, -1);
  public static final Vec2 LEFT = new Vec2().set(-1, 0);
  public static final Vec2 RIGHT = new Vec2().set(1, 0);

  public static final float PI = 3.14159265f;
  public static final float TO_DEG = 180.0f / 3.14159265f;
  public static final float TO_RAD = 3.14159265f / 180.0f;
  public static final float SCALAR_TO_RAD = PI * 2;

  public static final float IDEAL_WIDTH = 1080;
  public static final float IDEAL_HEIGHT = 1920;
  public static final float RATIO_WIDTH =
    Gdx.graphics.getWidth() / IDEAL_WIDTH;
  public static final float RATIO_HEIGHT =
    Gdx.graphics.getHeight() / IDEAL_HEIGHT;

  public static float map_width(float x) {
    return RATIO_WIDTH * x;
  }

  public static float map_height(float y) {
    return RATIO_HEIGHT * y;
  }

  public static float unmap_width(float x) {
    return x / RATIO_WIDTH;
  }

  public static float unmap_height(float y) {
    return y / RATIO_HEIGHT;
  }

  public static final Rectangle simulation_bounds = new Rectangle(
    0, 0,
    Gdx.graphics.getWidth(),
    Gdx.graphics.getHeight()
  ).add_padding(200);
}
