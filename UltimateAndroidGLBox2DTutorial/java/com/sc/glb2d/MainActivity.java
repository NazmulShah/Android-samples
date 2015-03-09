package com.sc.glb2d;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    GLSurfaceView view = new GLSurfaceView(this);
    Renderer renderer = new Renderer();
    view.setRenderer(renderer);

    setContentView(view);
  }
}
