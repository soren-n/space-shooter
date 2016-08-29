package com.oaken.rockit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RockItGame extends Game {
  public SpriteBatch batch;

  private Screen _current_screen;

  @Override
  public void create() {
    Asset.load_all();
    batch = new SpriteBatch();
    _current_screen = null;
    transition(new StartScreen(this));
  }

  public void transition(Screen screen) {
    if( _current_screen != null)
      _current_screen.dispose();
    _current_screen = screen;
    this.setScreen(screen);
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    batch.dispose();
  }
}
