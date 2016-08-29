package com.oaken.rockit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureShape implements Shape {
  private Texture _texture;
  private float   _width;
  private float   _height;

  public TextureShape(Texture texture) {
    _texture = texture;
    _width = Global.map_width(texture.getWidth());
    _height = Global.map_height(texture.getHeight());
  }

  public void draw(SpriteBatch batch, Vec2 pos, float rot) {
    float x0 = _width / 2;
    float y0 = _height / 2;
    batch.draw(
      _texture,
      pos.x - x0,
      pos.y - y0,
      x0, y0,
      _width, _height,
      1.0f, 1.0f,
      rot,
      0, 0,
      _texture.getWidth(),
      _texture.getHeight(),
      false, false
    );
  }

  public float get_width() {
    return _width;
  }

  public float get_height() {
    return _height;
  }

  public void dispose() {
    _texture.dispose();
  }
}
