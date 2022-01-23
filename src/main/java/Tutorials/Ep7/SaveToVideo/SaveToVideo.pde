float x, y;
float dia = (float) (1000 / 0.92);
float sw = 5;
NervousSquare nervousSquare;

public void settings() {
  size(960, 720);
  x = (float) width / 2;
  y = (float) height / 2;
  nervousSquare = new NervousSquare(x, y, 0, dia, sw);
}

public void draw() {
  push();
  background(40);
  fill(40, 40, 40);
  noStroke();
  //stroke(40);
  rectMode(CENTER);
  strokeWeight(sw);

  background(238, 238, 238);
  nervousSquare.display();
  nervousSquare.update(width, height, 1000);
  pop();
  rec();
}
