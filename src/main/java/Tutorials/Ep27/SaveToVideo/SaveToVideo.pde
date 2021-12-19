float x;
ArrayList<Float> xVals;
float sideSpace;
float sw = 4;
float angleVar = 360;
boolean initialize = false;

public void settings() {
  fullScreen();
  //size(900, 900);
  //noLoop();

  mouseX = 10;
}

public void draw() {
  if (!initialize) {
    strokeCap(CORNER);
    initialize = true;
  }
  push();
  
  if (frameCount % 30 == 0) xVals = new ArrayList<Float>();

  pushMatrix();
  background(238);
  translate((float) width / 2, (float) height / 2);

  for (int angle = 0; angle < 360; angle += angleVar) {
    if (angleVar <= 11 && frameCount % 30 == 0) {
      x = random(300, 350);
      xVals.add(x);
    } else if (angleVar > 11) {
      x = 350;
    } else {
      try {
        x = xVals.get((int) (angle / angleVar));
      } 
      catch (IndexOutOfBoundsException e) {
        x = 350;
      }
    }

    pushMatrix();
    rotate(radians(angle));

    stroke(222, 0, 0);
    strokeWeight(sw);
    line(x, 0, (float) (width / 1.5), 0);
    popMatrix();
  }
  popMatrix();

  if (angleVar > 1) {
    angleVar -= 1;
  } else {
    //frameRate(3);
    if (sideSpace < (float) width / 4) sideSpace++;
    noStroke();
    fill(238);
    rect(0, 0, sideSpace, height);
    rect(width - sideSpace, 0, sideSpace, height);
  }
  //angleVar = map(mouseX, 0, width, 1, 360);
  sw = map(angleVar, 1, 360, 4, 128);
  
  pop();
  rec();
}
