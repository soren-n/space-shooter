package com.oaken.rockit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends GUI.Element {

  public interface Handler {
    public void on_click(Vec2 location);
  }

  public Handler    handler;
  public Vec2       position;

  public Button(Handler handler) {
    super();
    this.handler = handler;
    this.position = Vec2.request();
    bounds.set_size(
      Asset.start_button_shape.get_width(),
      Asset.start_button_shape.get_height()
    );
  }

  public void render(SpriteBatch batch) {
    Asset.start_button_shape.draw(batch, position, 0);
  }

  public void update(float delta) {
    bounds.set_position(
      position.x - (bounds.w / 2),
      position.y - (bounds.h / 2)
    );
  }

  public void on_click(Vec2 location) {
    handler.on_click(location);
  }

  public void dispose() {
    super.dispose();
    Vec2.release(position);
    position = null;
    handler = null;
  }
}
