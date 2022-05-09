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
    float sw = 1;
    if (drawPath) {
      strokeWeight(sw);
      stroke(150, 150);
      line(lastPoint.x * scale, lastPoint.y * scale, 
        (lastPoint.x + vectors.get(n).x) * scale, 
        (lastPoint.y + vectors.get(n).y) * scale);

      float r = 2 * sqrt(vectors.get(n).x * scale * vectors.get(n).x * scale +
        vectors.get(n).y * scale * vectors.get(n).y * scale);
      strokeWeight(2);
      stroke(173, 216, 230, 60);
      ellipse(lastPoint.x * scale, lastPoint.y * scale, 
        r, r);
    }
    lastPoint.add(vectors.get(n));
    
    if (drawPath) {
      strokeWeight(sw);
      stroke(150, 150);
      line(lastPoint.x * scale, lastPoint.y * scale, 
        (lastPoint.x + nVectors.get(n).x) * scale, 
        (lastPoint.y + nVectors.get(n).y) * scale);

      float r = 2 * sqrt(nVectors.get(n).x * scale * nVectors.get(n).x * scale +
        nVectors.get(n).y * scale * nVectors.get(n).y * scale);
      strokeWeight(2);
      stroke(173, 216, 230, 60);
      ellipse(lastPoint.x * scale, lastPoint.y * scale, 
        r, r);
    }
    lastPoint.add(nVectors.get(n));
  }
  pen.set(lastPoint);
}

void drawPath() {
  if (route.size() <= TWO_PI / rotateSpeed + 1) 
    route.add(pen.copy());
  stroke(255, 255, 0);
  strokeWeight(3);
  beginShape();
  for (PVector p : route) {
    vertex(p.x * scale, p.y * scale);
  }
  endShape();
}
