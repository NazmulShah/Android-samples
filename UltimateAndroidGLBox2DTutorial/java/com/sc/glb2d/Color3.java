package com.sc.glb2d;

import org.jbox2d.common.Vec3;

public class Color3 {

  public int r;
  public int g;
  public int b;

  public Color3() {
    r = g = b = 0;
  }
  public Color3(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
  public Color3(float r, float g, float b) {
    this.r = (int)(r * 255.0f);
    this.g = (int)(g * 255.0f);
    this.b = (int)(b * 255.0f);
  }

  // Makes components GL-compatible, returns them in a vector
  public Vec3 toFloat() {
    return new Vec3((float)r / 255.0f, (float)g / 255.0f, (float)b / 255.0f);
  }
}
