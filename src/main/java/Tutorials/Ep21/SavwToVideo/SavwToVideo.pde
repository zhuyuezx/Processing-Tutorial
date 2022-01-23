float xStep = 20;
float xStepCounter = 1;
float angle = 10;

public void setup() {
  size(960, 720);
  stroke(0);
  strokeCap(CORNER);
  strokeWeight(xStep / 2);
}

public void draw() {
  push();
  background(255);
  xStep = map(xStepCounter, 0, width, width, 15);
  if (xStepCounter < width) xStepCounter+=2;

  strokeWeight(xStep / 2);
  translate((float) width / 2, (float) height / 2);
  for (int x = -width / 2; x < width / 2; x += xStep) {
    line(x, (float) -height / 2, x, (float) height / 2);
  }

  rotate(radians(angle));
  for (int x = -width / 2; x < width / 2; x += xStep) {
    line(x, (float) -height / 2, x, (float) height / 2);
  }
  if (xStepCounter >= width) 
    angle += 0.1;

  pop();
  rec();
}
