package com.oaken.rockit;

public interface Weapon {
  public void reset();
  public boolean ready();
  public boolean empty();
  public void update(float delta);
  public void fire(Actor.Group group, Vec2 pos, Vec2 dir);
  public void reload(int ammo);
}
