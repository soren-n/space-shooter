package com.oaken.rockit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GUI {
  public static abstract class Element {
    public Element    next;
    public Rectangle  bounds;

    public Element() {
      next = null;
      bounds = new Rectangle();
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update(float delta);
    public abstract void on_click(Vec2 location);

    public void dispose() {
      bounds.dispose();
      bounds = null;
    }
  }

  private OrthographicCamera  _camera;
  private Vector3             _touch;
  private Vec2                _location;

  public Element              elements;

  public GUI() {
    _camera = new OrthographicCamera();
    _camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    _touch = new Vector3();
    _location = Vec2.request();
    elements = null;
  }

  public void add_element(Element element) {
    element.next = elements;
    elements = element;
  }

  public void render(SpriteBatch batch) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    _camera.update();
    batch.setProjectionMatrix(_camera.combined);
    batch.begin();
    Element current = elements;
    while( current != null) {
      current.render(batch);
      current = current.next;
    }
    batch.end();
  }

  public void update(float delta) {
    Element current = null;
    if( Gdx.input.isTouched()) {
      _touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      _camera.unproject(_touch);
      _location.set(
        Math.max(0, Math.min(Gdx.graphics.getWidth(), _touch.x)),
        Math.max(0, Math.min(Gdx.graphics.getHeight(), _touch.y))
      );
      current = elements;
      while( current != null) {
        if( Predicate.envelope(current.bounds, _location))
          current.on_click(_location);
        current = current.next;
      }
    }
    current = elements;
    while( current != null) {
      current.update(delta);
      current = current.next;
    }
  }

  public void dispose() {
    Element current = elements;
    while( current != null) {
      current.dispose();
      current = current.next;
    }
    elements = null;
    Vec2.release(_location);
    _location = null;
  }
}
