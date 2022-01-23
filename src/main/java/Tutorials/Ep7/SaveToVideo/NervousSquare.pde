public class NervousSquare {

  float x, y;
  int layer;
  private final NervousSquare inner;
  float dia;
  float sw;

  public NervousSquare(float x, float y, int layer, float dia, float sw) {
    this.x = x;
    this.y = y;
    this.layer = layer + 1;
    this.dia = (float) (dia * 0.92);
    this.sw = (float) (sw * 0.92);
    if (layer < 100) {
      this.inner = new NervousSquare(this.x, this.y, this.layer, this.dia, this.sw);
    } else {
      this.inner = null;
    }
  }

  public void display() {
    strokeWeight(sw);
    float colorIndex = (float) (layer * 1.2);
    fill(colorIndex * 8, 255 - colorIndex * 20, 255 - colorIndex * 8);
    rect(x, y, dia, dia);
    if (inner != null) inner.display();
  }

  public void update(float xpos, float ypos, float dia_) {
    x += random(-sw, sw);
    y += random(-sw, sw);

    if (layer == 1) {
      x = (float)width /2;
      y = (float)height /2;
    } else {
      x = PApplet.constrain(x, xpos - dia_ / 2 + dia / 2, xpos + dia_ / 2 - dia / 2);
      y = PApplet.constrain(y, ypos - dia_ / 2 + dia / 2, ypos + dia_ / 2 - dia / 2);
    }
    if (inner != null) inner.update(x, y, dia);
  }
}
