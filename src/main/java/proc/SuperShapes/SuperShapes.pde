import peasy.*;

PeasyCam cam;
PVector[][] globe;
int total = 75;

float m = 0;
float mChange = 0;
float offSet = 0;

public void setup() {
  size(600, 600, P3D);
  noStroke();
  cam = new PeasyCam(this, 500);
  colorMode(HSB);
  globe = new PVector[total + 1][total + 1];
}

float a = 1;
float b = 1;

public float superShape(float theta, float m, float n1, float n2, float n3){
  float r;
  float t1 = abs((1 / a) * cos(m * theta / 4));
  t1 = pow(t1, n2);
  float t2 = abs((1 / b) * sin(m * theta / 4));
  t2 = pow(t2, n3);
  float t3 = t1 + t2;
  r = pow(t3, -1 / n1);
  return r;
}

public void draw() {
  m = map(sin(mChange), -1, 1, 0, 7);
  mChange += 0.01;
  
  background(0);
  lights();

  float r = 200;
  for (int i = 0; i < total + 1; i++) {
    float lat = map(i, 0, total, -HALF_PI, HALF_PI);
    float r2 = superShape(lat, m, 0.2, 1.7, 1.7);
    for (int j = 0; j < total + 1; j++) {
      float lon = map(j, 0, total, -PI, PI);
      float r1 = superShape(lon, m, 0.2, 1.7, 1.7);
      float x = r * r1 * cos(lon) * r2 * cos(lat);
      float y = r * r1 * sin(lon) * r2 * cos(lat);
      float z = r * r2 * sin(lat);
      globe[i][j] = new PVector(x, y, z);
    }
  }

  //stroke(255);
  //fill(255);
  offSet += 5;
  for (int i = 0; i < total; i++) {
    float hu = map(i, 0, total, 0, 255 * 6);
    fill((hu + offSet) % 255, 255, 255);
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
