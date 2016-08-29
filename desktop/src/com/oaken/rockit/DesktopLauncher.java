package com.oaken.rockit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
  public static void main(String[] args) {
    LwjglApplicationConfiguration cnf = new LwjglApplicationConfiguration();
    cnf.title = "RockIt";
    cnf.width = 450;
    cnf.height = 800;
    new LwjglApplication( new RockItGame(), cnf);
  }
}
