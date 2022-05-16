void rotateVectors() {
  for (int n = 0; n < numVectors; n++) {
    vectors.get(n).rotate(n * rotateSpeed);
    nVectors.get(n).rotate(-n * rotateSpeed);
  }
}

void drawVectors() {
  noFill();

  PVector lastPoint = new PVector(width / 2 / scale, height / 2 / scale);
  for (int n = 1; n < numVectors; n++) {
    lastPoint.add(vectors.get(n));
    lastPoint.add(nVectors.get(n));
  }
  pen.set(lastPoint);

  pushMatrix();
  PVector locate = PVector.mult(pen, scale);
  translate(width / 2 - locate.x, height / 2 - locate.y);
  lastPoint = new PVector(width / 2 / scale, height / 2 / scale);
  for (int n = 1; n < numVectors; n++) {
    float sw = map(n, 1, numVectors, 5, -15);
    if (sw <= 0.5) sw = 0.5;
    strokeWeight(sw);
    stroke(150, 150);
    line(lastPoint.x * scale, lastPoint.y * scale, 
      (lastPoint.x + vectors.get(n).x) * scale, 
      (lastPoint.y + vectors.get(n).y) * scale);

    float r = 2 * sqrt(vectors.get(n).x * scale * vectors.get(n).x * scale +
      vectors.get(n).y * scale * vectors.get(n).y * scale);
    strokeWeight(2);
    stroke(173,216,230, 60);
    ellipse(lastPoint.x * scale, lastPoint.y * scale, 
      r, r);

    lastPoint.add(vectors.get(n));

    strokeWeight(sw);
    stroke(150, 150);
    line(lastPoint.x * scale, lastPoint.y * scale, 
      (lastPoint.x + nVectors.get(n).x) * scale, 
      (lastPoint.y + nVectors.get(n).y) * scale);

    r = 2 * sqrt(nVectors.get(n).x * scale * nVectors.get(n).x * scale +
      nVectors.get(n).y * scale * nVectors.get(n).y * scale);
    strokeWeight(2);
    stroke(173,216,230, 60);
    ellipse(lastPoint.x * scale, lastPoint.y * scale, 
      r, r);

    lastPoint.add(nVectors.get(n));
  }
}

void drawPath() {
  if (route.size() <= TWO_PI / rotateSpeed + 1) 
    route.add(pen.copy());
  else {
    if (scale > 1) {
      scale -= changeRate;
      for (PVector v : route) {
        v.x += width / 2 / scale - width / 2 / (scale + changeRate);
        v.y += height / 2 / scale - height / 2 / (scale + changeRate);
      }
    }
  }
  stroke(255, 255, 0);
  strokeWeight(10);
  beginShape();
  for (PVector p : route) {
    vertex(p.x * scale, p.y * scale);
  }
  endShape();
  popMatrix();
}
