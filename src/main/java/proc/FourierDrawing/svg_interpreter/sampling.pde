void samplePoints(ArrayList<String> curves) {
  PVector lastPoint = new PVector(0, 0);
  for (int i = 0; i < curves.size(); ) {
    String curr = curves.get(i);
    if (curr.equals("z")) {
      lastPoint = new PVector(0, 0);
      i++;
    } else if (curr.equals("v")) {
      lastPoint.y += Float.parseFloat(curves.get(i + 1));
      i += 2;
    } else if (curr.equals("V")) {
      lastPoint.y = Float.parseFloat(curves.get(i + 1));
      i += 2;
    } else if (curr.equals("h")) {
      lastPoint.x += Float.parseFloat(curves.get(i + 1));
      i += 2;
    } else if (curr.equals("H")) {
      lastPoint.x = Float.parseFloat(curves.get(i + 1));
      i += 2;
    } else if (curr.equals("M")) {
      lastPoint = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
      i += 3;
    } else if (curr.equals("m")) {
      lastPoint.add(new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
      i += 3;
    } else if (curr.equals("l")) {
      PVector p2 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));

      float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
      }
      lastPoint.set(p2);
      i += 3;
    } else if (curr.equals("L")) {
      PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));

      float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
      }
      lastPoint.set(p2);
      i += 3;
    } else if (curr.equals("q")) {
      PVector control = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
      PVector p2 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));

      float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
          add(PVector.mult(control, 2 * t * (1 - t))).
          add(PVector.mult(p2, t * t)));
      }
      lastPoint.set(p2);
      i += 5;
    } else if (curr.equals("Q")) {
      PVector control = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
      PVector p2 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));

      float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
          add(PVector.mult(control, 2 * t * (1 - t))).
          add(PVector.mult(p2, t * t)));
      }
      lastPoint.set(p2);
      i += 5;
    } else if (curr.equals("t")) {
      PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
      PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
      PVector control = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
      PVector p2 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
      float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
          add(PVector.mult(control, 2 * t * (1 - t))).
          add(PVector.mult(p2, t * t)));
      }
      lastPoint.set(p2);
      i += 3;
    } else if (curr.equals("T")) {
      PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
      PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
      PVector control = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
      PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
      float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
          add(PVector.mult(control, 2 * t * (1 - t))).
          add(PVector.mult(p2, t * t)));
      }
      lastPoint.set(p2);
      i += 3;
    } else if (curr.equals("c")) {
      PVector p2 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
      PVector p3 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));
      PVector p4 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 5)), Float.parseFloat(curves.get(i + 6))));

      float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
          add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
          add(PVector.mult(p3, 3 * t * t * (1 - t))).
          add(PVector.mult(p4, t * t * t)));
      }
      lastPoint.set(p4);
      i += 7;
    } else if (curr.equals("C")) {
      PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
      PVector p3 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));
      PVector p4 = new PVector(Float.parseFloat(curves.get(i + 5)), Float.parseFloat(curves.get(i + 6)));

      float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
          add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
          add(PVector.mult(p3, 3 * t * t * (1 - t))).
          add(PVector.mult(p4, t * t * t)));
      }
      lastPoint.set(p4);
      i += 7;
    } else if (curr.equals("s")) {
      PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
      PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
      PVector p2 = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
      PVector p3 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
      PVector p4 = PVector.add(lastPoint, 
        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));
      float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
          add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
          add(PVector.mult(p3, 3 * t * t * (1 - t))).
          add(PVector.mult(p4, t * t * t)));
      }
      lastPoint.set(p4);
      i += 5;
    } else if (curr.equals("S")) {
      PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
      PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
      PVector p2 = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
      PVector p3 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
      PVector p4 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));
      float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
      step = 1 / max(30, step);
      for (float t = 0; t < 1; t += step) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
          add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
          add(PVector.mult(p3, 3 * t * t * (1 - t))).
          add(PVector.mult(p4, t * t * t)));
      }
      lastPoint.set(p4);
      i += 5;
    } else {
      i++;
    }
  }
}

void setVectors() {
  for (int n = 0; n < numVectors; n++) {
    PVector total = new PVector(0, 0);
    for (float t = 0; t <= 1; t += 1.0 / points.size()) {
      PVector v = points.get(int(t * points.size()));
      float r = sqrt(v.x * v.x + v.y * v.y);
      float theta = atan(v.y / v.x) - n * TWO_PI * t;
      total.add(new PVector(r * cos(theta), r * sin(theta)));
    }
    total.div(points.size());
    vectors.add(total);
  }
  
  nVectors.add(new PVector(0, 0));
  for (int n = 1; n < numVectors; n++) {
    PVector total = new PVector(0, 0);
    for (float t = 0; t <= 1; t += 1.0 / points.size()) {
      PVector v = points.get(int(t * points.size()));
      float r = sqrt(v.x * v.x + v.y * v.y);
      float theta = atan(v.y / v.x) + n * TWO_PI * t;
      total.add(new PVector(r * cos(theta), r * sin(theta)));
    }
    total.div(points.size());
    nVectors.add(total);
  }
}
