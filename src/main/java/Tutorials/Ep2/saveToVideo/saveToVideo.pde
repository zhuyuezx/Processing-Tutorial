float sw, alpha;
float yStep = 10; 
float arcSize = 450;

void setup() {
  size(960, 720);
}

void draw() {
  background(238);
  push();

  mouseX = constrain(mouseX, 10, width);
  mouseY = constrain(mouseY, 10, height);

  //        yStep = mouseY;
  //        arcSize = mouseX;

  noFill();
  stroke(20);

  for (float y = arcSize / 3; y < height - arcSize / 3; y += yStep) {

    sw = map(sin(radians(y + alpha)), -1, 1, 2, yStep);
    strokeWeight(sw);
    for (float x1 = arcSize / 2; x1 < width + arcSize; x1 += arcSize) {
      arc(x1, y, arcSize / 2, arcSize / 2, 0, PI);
    }

    sw = map(sin(radians(y - alpha)), -1, 1, 2, yStep);
    strokeWeight(sw);
    for (float x2 = 0; x2 < width + arcSize; x2 += arcSize) {
      arc(x2, y, arcSize / 2, arcSize / 2, PI, TWO_PI);
    }
  }
  alpha++;
  pop();
  rec();
}
