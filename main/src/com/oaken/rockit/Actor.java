package com.oaken.rockit;

import java.lang.Iterable;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Actor {
  public boolean    dead;
  public Actor      prev;
  public Actor      next;
  public Rectangle  bounds;

  public Actor() {
    dead = false;
    prev = null;
    next = null;
    bounds = new Rectangle();
  }

  public void reset() {
    dead = false;
    prev = null;
    next = null;
    bounds.reset();
  }

  public void draw(SpriteBatch batch) {
    // Do nothing
  }

  public void update(float delta) {
    // Do nothing
  }

  public void collide(Actor other) {
    // Do nothing
  }

  public void collect() {
    // Do nothing
  }

  public void dispose() {
    bounds.dispose();
    bounds = null;
  }

  public static class ActorIterator implements Iterator<Actor> {
    private Actor _actor;

    public ActorIterator(Actor actor) {
      _actor = actor;
    }

    public boolean hasNext() {
      return _actor != null;
    }

    public Actor next() {
      if( _actor == null) return null;
      Actor result = _actor;
      _actor = _actor.next;
      return result;
    }
  }

  public static class Group implements Iterable<Actor> {
    private Actor _members;

    public Group() {
      _members = new Actor();
    }

    private void _remove(Actor actor) {
      actor.prev.next = actor.next;
      if( actor.next != null)
        actor.next.prev = actor.prev;
      actor.prev = null;
      actor.next = null;
    }

    private void _insert(Actor actor) {
      actor.prev = _members;
      actor.next = _members.next;
      if( _members.next != null)
        _members.next.prev = actor;
      _members.next = actor;
    }

    public void add(Actor actor) {
      _insert(actor);
    }

    public void reset() {
      Actor next = null;
      Actor actor = _members.next;
      while( actor != null) {
        next = actor.next;
        actor.collect();
        actor = next;
      }
      _members.reset();
    }

    public void draw(SpriteBatch batch) {
      Actor actor = _members.next;
      while( actor != null) {
        actor.draw(batch);
        actor = actor.next;
      }
    }

    public void update(float delta) {
      Actor actor = _members.next;
      while( actor != null) {
        actor.update(delta);
        actor = actor.next;
      }
    }

    public void collide(Actor target) {
      Actor actor = _members.next;
      while( actor != null) {
        if( Predicate.overlap(target.bounds, actor.bounds)) {
          target.collide(actor);
          actor.collide(target);
        }
        actor = actor.next;
      }
    }

    public boolean collect() {
      boolean result = false;
      Actor next = null;
      Actor actor = _members.next;
      while( actor != null) {
        next = actor.next;
        if( actor.dead) {
          _remove(actor);
          actor.collect();
          result = true;
        }
        actor = next;
      }
      return result;
    }

    public void dispose() {
      Actor next = null;
      Actor actor = _members.next;
      while( actor != null) {
        next = actor.next;
        actor.dispose();
        actor.collect();
        actor = next;
      }
      _members.reset();
    }

    public Iterator<Actor> iterator() {
      return new ActorIterator(_members.next);
    }
  }
}
