float r;
float factor = 0;

public void setup() {
  size(1920, 1280);
}

public void draw() {
  colorMode(HSB);
  background(0);
  r = (float) height / 2 - 16;
  int total = 360;
  factor += 0.025;

  String timeMsg = "Time Table Rate: " + (int) factor;
  textSize(20);
  text(timeMsg, 40, 40);

  translate(width / 2, height / 2);
  stroke(255, 150);
  strokeWeight(2);
  noFill();
  ellipse(0, 0, r * 2, r * 2);

  strokeWeight(2);
  for (int i = 0; i < total; i++) {
    float rad = i * TWO_PI / total;
    PVector a = getVector(i, total);
    PVector b = getVector(i * factor, total);

    stroke(map(i, 0, total, 255, 0), 160, 250, 100);
    line(a.x, a.y, b.x, b.y);
  }
  //rec();
}

public PVector getVector(float index, float total) {
  float angle = map(index % total, 0, total, 0, TWO_PI);
  PVector v = PVector.fromAngle(angle + PI);
  v.mult(r);
  return v;
}

void keyPressed() {
  if (key == ' ') 
    saveFrame("screen-####.jpg");
}
