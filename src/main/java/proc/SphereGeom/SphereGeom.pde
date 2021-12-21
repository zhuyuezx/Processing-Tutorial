import peasy.*;

PeasyCam cam;
PVector[][] globe;
int total = 75;

public void setup() {
  size(600, 600, P3D);
  globe = new PVector[total + 1][total + 1];
  noStroke();
  cam = new PeasyCam(this, 500);
  colorMode(HSB);
}

public void draw() {
  background(0);
  lights();

  float r = 200;
  for (int i = 0; i < total + 1; i++) {
    float lat = map(i, 0, total, 0, PI);
    for (int j = 0; j < total + 1; j++) {
      float lon = map(j, 0, total, 0, TWO_PI);
      float x = r * sin(lat) * cos(lon);
      float y = r * sin(lat) * sin(lon);
      float z = r * cos(lat);
      globe[i][j] = new PVector(x, y, z);
    }
  }

  for (int i = 0; i < total; i++) {
    float hu = map(i, 0, total, 0, 255 * 6);
    fill(hu % 255, 255, 255);
    beginShape(TRIANGLE_STRIP);
    for (int j = 0; j < total + 1; j++) {
      PVector v = globe[i][j];
      vertex(v.x, v.y, v.z);
      PVector v2 = globe[i + 1][j];
      vertex(v2.x, v2.y, v2.z);
    }
    endShape();
  }
}
