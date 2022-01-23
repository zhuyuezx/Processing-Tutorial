float angle;
float angle2 = 0;

public void settings() {
  fullScreen();
  //size(960, 720);
}

public void draw() {
  push();
  background(0, 15, 30);
  //surface.setLocation(957, 0);
  rectMode(CENTER);
  stroke(0, 15, 30);
  strokeWeight(25);

  translate((float) width / 2, (float) height / 2);
  float scaleVar = map(sin(radians(angle2)), -1, 1, 0.75, 5);
  //scaleVar = map(mouseX, 0, width, (float) 0.5, 5);
  scale(scaleVar);

  for (int i = 0; i < 150; i++) {
    fill(i * 10, 255 - i * 25, 255 - i * 10);
    scale((float) 0.95);
    rotate(radians(angle));
    rect(0, 0, 600, 600);
  }
  angle += 0.1;
  angle2 += 1;
  pop();
  rec();
}

void keyPressed() {
   if (key == ' ')
     saveFrame("frame-####.jpg");
}
