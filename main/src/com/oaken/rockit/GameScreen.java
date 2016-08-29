package com.oaken.rockit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen {
  public static final float SPAWN_CD = 3.0f;

  final RockItGame            game;

  private Stats               _stats;
  private OrthographicCamera  _camera;
  private Vector3             _touch;
  private Vec2                _dest;
  private Ship                _ship;

  private float               _spawn_timer;
  private Actor.Group         _enemies;

  public GameScreen(final RockItGame game) {
    this.game = game;
    _stats = new Stats(Vec2.request().set(
      10, Gdx.graphics.getHeight() - 10
    ));
    _camera = new OrthographicCamera();
    _camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    _touch = new Vector3();
    _dest = Vec2.request();
    _dest.set(Gdx.graphics.getWidth() / 2, 0);
    _ship = new Ship();
    _ship.transform.pos.set(_dest);
    _spawn_timer = 0;
    _enemies = new Actor.Group();
    Bullet.reserve(Bullet.POOL_INIT_COUNT);
    Coin.reserve(Coin.POOL_INIT_COUNT);
  }

  public void spawn_enemy() {
    Enemy enemy = Enemy.request();
    enemy.target = _ship;
    enemy.transform.pos.set(
      MathUtils.random(Gdx.graphics.getWidth()),
      Gdx.graphics.getHeight() + (Asset.enemy_shape.get_height() / 2)
    );
    _enemies.add(enemy);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    _camera.update();
    game.batch.setProjectionMatrix(_camera.combined);

    game.batch.begin();

    _ship.draw(game.batch);
    Enemy.powerups.draw(game.batch);
    Enemy.bullets.draw(game.batch);
    Enemy.coins.draw(game.batch);
    _enemies.draw(game.batch);
    _stats.draw(game.batch);

    Asset.system_font.draw(
      game.batch,
      String.format("Score: %d", _ship.score),
      50,
      Gdx.graphics.getHeight() - 50
    );
    Asset.system_font.draw(
      game.batch,
      String.format("Health: %d", _ship.health),
      50,
      Gdx.graphics.getHeight() - 75
    );

    game.batch.end();

    update(delta);
  }

  private float curve(float n) {
    return n * n;
  }

  public void update(float delta) {
    if( _ship.health <= 0) {
      game.transition(new StartScreen(game));
      return;
    }

    if( Gdx.input.isTouched()) {
      _touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      _camera.unproject(_touch);
      _dest.set(
        Math.max(0, Math.min(Gdx.graphics.getWidth(), _touch.x)),
        Math.max(0, Math.min(Gdx.graphics.getHeight(), _touch.y))
      );
      Vec2 temp = Vec2.sub(_dest, _ship.transform.pos);
      temp.mul(Ship.SPEED * delta).map();
      _ship.transform.dir.set(temp);
      Vec2.release(temp);
    }

    _spawn_timer += delta;
    if( _spawn_timer >= SPAWN_CD) {
      _spawn_timer = 0;
      spawn_enemy();
    }

    _ship.update(delta);
    _enemies.update(delta);
    Enemy.powerups.update(delta);
    Enemy.bullets.update(delta);
    Enemy.coins.update(delta);
    _stats.update(delta);

    Enemy.powerups.collide(_ship);
    Enemy.bullets.collide(_ship);
    Enemy.coins.collide(_ship);
    for( Actor enemy_actor : _enemies)
      _ship.bullets.collide(enemy_actor);

    Enemy.powerups.collect();
    Enemy.bullets.collect();
    Enemy.coins.collect();
    _ship.bullets.collect();
    _enemies.collect();
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
    Vec2.release(_dest);
    _stats.dispose();
    _enemies.dispose();
    _ship.dispose();
    _dest = null;
    _stats = null;
    _enemies = null;
    _ship = null;
  }
}
