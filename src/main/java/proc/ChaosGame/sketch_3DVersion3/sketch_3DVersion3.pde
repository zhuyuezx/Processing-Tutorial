import peasy.*;
import peasy.org.apache.commons.math.geometry.*;

PVector[] points;
int numOfPoint = 14;
float size = 200;
float percent = (float)2 / 3;
ArrayList<PVector> current;
ArrayList<Integer> colorInfo;
float rotateAngle = 0;
int previous = 0;

PeasyCam cam;

public void setup() {
  background(0);
  colorMode(HSB);
  fullScreen(P3D);
  //size(800, 800, P3D);
  cam = new PeasyCam(this, 500);

  points = new PVector[numOfPoint];
  points[0] = new PVector(size, size, size);
  points[1] = new PVector(size, size, -size);
  points[2] = new PVector(size, -size, size);
  points[3] = new PVector(size, -size, -size);
  points[4] = new PVector(-size, size, size);
  points[5] = new PVector(-size, size, -size);
  points[6] = new PVector(-size, -size, size);
  points[7] = new PVector(-size, -size, -size);
  points[8] = new PVector(size, 0, 0);
  points[9] = new PVector(-size, 0, 0);
  points[10] = new PVector(0, size, 0);
  points[11] = new PVector(0, -size, 0);
  points[12] = new PVector(0, 0, size);
  points[13] = new PVector(0, 0, -size);

  current = new ArrayList<PVector>();
  current.add(new PVector(size, size, size));
  colorInfo = new ArrayList<Integer>();
  colorInfo.add(0);
}

public void draw() {
  push();
  //println(current.get(current.size() - 1));
  background(0);
  for (int i = 0; i < numOfPoint; i++) {
    stroke(0, 0, 255);
    strokeWeight(10);
    point(points[i].x, points[i].y, points[i].z);
  }
  stroke(10, 150, 255);
  strokeWeight(4);

  Rotation rot = new Rotation(RotationOrder.XYZ, rotateAngle / 2, rotateAngle, rotateAngle / 3);
  Vector3D center = Vector3D.zero; // look at the origin (0,0,0)
  double distance = map(rotateAngle % 2, 0, 2, 100, 1000); // from this far away
  CameraState state = new CameraState(rot, center, distance);
  cam.setState(state, 1000);
  rotateAngle += 0.01;

  drawCurr();

  if (current.size() > 100000) return;

  for (int i = 0; i < 100; i++) {
    int index = int(random(points.length));
    PVector next = points[index];
    //println(next);

    int target = current.size() - 1;
    current.add(new PVector(
      lerp(current.get(target).x, next.x, percent), 
      lerp(current.get(target).y, next.y, percent), 
      lerp(current.get(target).z, next.z, percent)
      ));
    colorInfo.add(index);
    previous = index;
  }
  pop();
  rec();
}

void drawCurr() {
  beginShape(POINTS);
  for (int j = 0; j < current.size(); j++) {
    stroke(colorInfo.get(j) * 255 / points.length, 200, 255);
    vertex(current.get(j).x, current.get(j).y, current.get(j).z);
  }
  endShape();
}
