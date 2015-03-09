package com.sc.glb2d;

public class BoxObject extends BaseObject {

  private float width;
  private float height;

  public BoxObject(float _width, float _height) {
    super(); // Just assigns an ID

    // 4 points, 3 coords, 12 elements, 9000 problems
    vertices = new float[12];

    this.width = _width;
    this.height = _height;

    refreshVertices();
  }

  private void refreshVertices() {

    // Modify our own vertex array, and pass it to setVertices
    // We'll define our box centered around the origin
    // The z cord could potentially be used to specify a layer to render on. Food for thought.
    vertices[0] = width * -0.5f;
    vertices[1] = height * -0.5f;
    vertices[2] = 1.0f;

    vertices[3] = width * -0.5f;
    vertices[4] = height * 0.5f;
    vertices[5] = 1.0f;

    vertices[6] = width * 0.5f;
    vertices[7] = height * -0.5f;
    vertices[8] = 1.0f;

    vertices[9] = width * 0.5f;
    vertices[10] = height * 0.5f;
    vertices[11] = 1.0f;

    // Update!
    setVertices(vertices);
  }

  // Rebuild our vertices on modification
  public void setWidth(float _width) {
    this.width = _width;
    refreshVertices();
  }

  public void setHeight(float _height) {
    this.height = _height;
    refreshVertices();
  }

  public float getWidth() { return width; }
  public float getHeight() { return height; }
}
