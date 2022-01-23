int grid = 400;
float angle;
int mx = 80, my = 135;
float scaleVar = 1;
int resetCounter = 1;

public void setup() {
  size(1920, 1070, P3D);
  rectMode(CENTER);
  fill(255, 0, 0);
  noStroke();

  mx = (width - floor((float) width / (grid * 2)) * grid * 2) / 2;
  my = (height - floor((float) height / (grid * 2)) * grid * 2) / 2;
}

public void draw() {
  //push();
  background(15, 20, 30);

  translate((float) width / 2, (float) height / 2);
  scaleVar = lerp(scaleVar, map(angle, 0, 180, 
    (float) (resetCounter - 0.7), resetCounter), (float)0.1);
  scale(scaleVar);
  for (int i = mx + grid - width / 2; i < width / 2 - mx; i += grid * 2) {
    for (int j = my + grid - height / 2; j < height / 2 - my; j += grid * 2) {
      pushMatrix();
      translate(i, j);

      // top left
      pushMatrix();
      translate((float) -grid / 2, (float) -grid / 2);
      rotateX(radians(angle));
      rotateY(-radians(angle));
      rect(0, 0, grid, grid);
      popMatrix();

      // top right
      pushMatrix();
      translate((float) grid / 2, (float) -grid / 2);
      rotateY(radians(angle));
      rotateX(-radians(angle));
      rect(0, 0, grid, grid);
      popMatrix();

      // bottom left
      pushMatrix();
      translate((float) -grid / 2, (float) grid / 2);
      rotateY(radians(angle));
      rotateX(radians(angle));
      rect(0, 0, grid, grid);
      popMatrix();

      // bottom right
      pushMatrix();
      translate((float) grid / 2, (float) grid / 2);
      rotateX(-radians(angle));
      rotateY(-radians(angle));
      rect(0, 0, grid, grid);
      popMatrix();

      popMatrix();
    }
  }
  angle++;

  if (angle >= 180) {
    grid /= 2;
    if (grid <= 10) {
      grid = 400;
      resetCounter = 0;
    }
    angle = 0;
    resetCounter += 1;
  }
  //pop();
  //rec();
}

void keyPressed() {
   if (key == ' ') 
     saveFrame("frame-####.jpg");
}
