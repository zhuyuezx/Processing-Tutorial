// Step1: create basic settings of verticies ... Complete!
// Step2: module more codes and include colorings ... Complete!
// Step3: try more interesting settings in 2D and save them ... Complete!
// Step4: let's try to move it into 3D space! ... Complete!
// Step5: let's illustrate verticies and differnet color settings ... Complete!
// Step6: enable camera's auto rotation and zooming ... Complete!
// Step7: let's try more settings in 3D ... Complete!
// Step8: try to record the rotation and graph ... Complete!

import peasy.*;
import peasy.org.apache.commons.math.geometry.*;

PVector[] points;
int num = 4;
float size = 400;
ArrayList<PVector> current;
ArrayList<Integer> colorInfo;
float percent = 0.5;
float rotateAngle = 0;
//int prev1;
//int prev2;

PeasyCam cam;

void setup() {
  fullScreen(P3D);
  cam = new PeasyCam(this, 600);
  //size(800, 800);
  colorMode(HSB);

  points = new PVector[num];
  points[0] = new PVector(size, size, size);
  points[1] = new PVector(size, -size, -size);
  points[2] = new PVector(-size, size, -size);
  points[3] = new PVector(-size, -size, size);
  //for (int a = 0; a < num; a++) {
  //  float angle = TWO_PI * a / num;
  //  points[a] = PVector.fromAngle(angle);
  //  points[a].mult(size);
  //  points[a].add(width / 2, height / 2);
  //}

  current = new ArrayList<PVector>();
  current.add(new PVector(size, size, size));
  colorInfo = new ArrayList<Integer>();
  colorInfo.add(0);
}

void draw() {
  push();
  background(0);
  stroke(0, 200, 255);
  strokeWeight(10);
  for (PVector p : points)
    point(p.x, p.y, p.z);

  strokeWeight(4);

  Rotation rot = new Rotation(RotationOrder.XYZ, 
  rotateAngle / 2, rotateAngle, rotateAngle / 3);
  Vector3D center = Vector3D.zero;
  double distance = map(rotateAngle % 3, 0, 3, 300, 1000);
  CameraState state = new CameraState(rot, center, distance);
  cam.setState(state, 1000);
  rotateAngle += 0.01;

  drawCurr();

  if (current.size() > 100000) return;

  for (int i = 0; i < 100; i++) {
    int r = (int)random(num);
    PVector next = points[r];
    int target = current.size() - 1;
    current.add(new PVector(
      lerp(current.get(target).x, next.x, percent), 
      lerp(current.get(target).y, next.y, percent), 
      lerp(current.get(target).z, next.z, percent)
      ));
    colorInfo.add(r);
  }
  
  pop();
  rec();
}

void drawCurr() {
  for (int i = 0; i < current.size(); i++) {
    stroke(colorInfo.get(i) * 255 / num, 200, 255);
    point(current.get(i).x, current.get(i).y, current.get(i).z);
  }
}

void keyPressed() {
  if (key == ' ')
    saveFrame("frame-###.jpg");
}