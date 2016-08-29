package com.oaken.rockit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stats {
  private static final float SMOOTH = 0.8f;
  private static final float SMOOTH_INV = 1.0f - SMOOTH;

  private float _fps;
  private Vec2  _pos;

  public Stats(Vec2 pos) {
    _fps = 0;
    _pos = pos;
  }

  public void draw(SpriteBatch batch) {
    Asset.system_font.getData().setScale(0.5f);
    Asset.system_font.setColor(255, 255, 255, 255);
    Asset.system_font.draw(
      batch,
      String.format("%.2f fps", _fps),
      _pos.x,
      _pos.y
    );
  }

  public void update(float delta) {
    _fps = (_fps * SMOOTH) + ((1.0f / delta) * SMOOTH_INV);
  }

  public void dispose() {
    Vec2.release(_pos);
    _pos = null;
  }
}
