import peasy.*;

PVector[] points;
int numOfPoint = 20;
float size = 200;
float percent = (float)2 / 3;
ArrayList<PVector> current;
ArrayList<Integer> colorInfo;

PeasyCam cam;

public void setup() {
  background(0);
  colorMode(HSB);
  size(800, 800, P3D);
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
  points[8] = new PVector(size, size, 0);
  points[9] = new PVector(size, -size, 0);
  points[10] = new PVector(-size, size, 0);
  points[11] = new PVector(-size, -size, 0);
  points[12] = new PVector(0, size, size);
  points[13] = new PVector(0, size, -size);
  points[14] = new PVector(0, -size, size);
  points[15] = new PVector(0, -size, -size);
  points[16] = new PVector(size, 0, size);
  points[17] = new PVector(size, 0, -size);
  points[18] = new PVector(-size, 0, size);
  points[19] = new PVector(-size, 0, -size);

  current = new ArrayList<PVector>();
  current.add(new PVector(size, size, size));
  colorInfo = new ArrayList<Integer>();
  colorInfo.add(0);
}

public void draw() {
  //println(current.get(current.size() - 1));
  background(0);
  stroke(10, 150, 255);
  strokeWeight(4);
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
  }
}

void drawCurr() {
  beginShape(POINTS);
  for (int j = 0; j < current.size(); j++) {
    stroke(colorInfo.get(j) * 255 / points.length, 200, 255);
    vertex(current.get(j).x, current.get(j).y, current.get(j).z);
  }
  endShape();
}
