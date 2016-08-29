package com.oaken.rockit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class StartScreen implements Screen {

  final RockItGame game;

  public GUI    scene;

  public StartScreen(final RockItGame game) {
    this.game = game;
    this.scene = new GUI();
    Button button = new Button(new Button.Handler() {
      public void on_click(Vec2 location) {
        game.transition(new GameScreen(game));
      }
    });
    button.position.set(
      Gdx.graphics.getWidth() / 2,
      Gdx.graphics.getHeight() / 2
    );
    this.scene.add_element(button);
  }

  @Override
  public void render(float delta) {
    scene.update(delta);
    scene.render(game.batch);
  }

  @Override
  public void resize(int width, int height) {
    //
  }

  @Override
  public void show() {
    //
  }

  @Override
  public void hide() {
    //
  }

  @Override
  public void pause() {
    //
  }

  @Override
  public void resume() {
    //
  }

  @Override
  public void dispose() {
    scene.dispose();
  }
}
