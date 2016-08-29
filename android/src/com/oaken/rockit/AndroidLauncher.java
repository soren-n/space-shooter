package com.oaken.rockit;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
  @Override
  protected void onCreate(Bundle state) {
    super.onCreate(state);
    AndroidApplicationConfiguration cnf = new AndroidApplicationConfiguration();
    cnf.useAccelerometer = true;
    cnf.useCompass = false;
    initialize(new RockItGame(), cnf);
  }
}
