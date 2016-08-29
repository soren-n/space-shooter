package com.oaken.rockit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Shape {
  public void draw(SpriteBatch batch, Vec2 pos, float rot);
  public float get_width();
  public float get_height();
  public void dispose();

  public class Null implements Shape {
    public void draw(SpriteBatch batch, Vec2 pos, float rot) {
      // Do nothing
    }

    public float get_width() {
      return 0;
    }

    public float get_height() {
      return 0;
    }

    public void dispose() {
      // Do nothing
    }
  }

  public static final Null null_object = new Null();
}
