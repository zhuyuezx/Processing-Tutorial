float xStep = 20;
float xx;
float angle = 5;

public void setup() {
  fullScreen();
  stroke(255);
  strokeCap(CORNER);
  strokeWeight(xStep / 2);
}

public void draw() {
  background(255);
  blendMode(DIFFERENCE);
  push();

  xx = map(mouseX, 0, width, (float) -width / 2, (float) width / 2);
  //angle = map(mouseY, 0, height, -90, 90);
  xStep = map(mouseY, 0, height, 10, height);

  strokeWeight(xStep / 2);
  translate((float) width / 2, (float) height / 2);
  for (int x = -width / 2; x < width / 2; x += xStep) {
    line(x, (float) -height / 2, x, (float) height / 2);
  }

  rotate(radians(angle));
  for (int x = -width / 2; x < width / 2; x += xStep) {
    line(x + xx, (float) -height / 2, x + xx, (float) height / 2);
  }
  angle++;
  
  pop();
  rec();
}
