import peasy.*;

Planet sun;
PImage sunTexture;
PImage[] textures = new PImage[5];

PeasyCam cam;

void setup() {
  fullScreen(P3D);
  sunTexture = loadImage("sun.png");
  textures[0] = loadImage("mars.jpg");
  textures[1] = loadImage("earth.jpg");
  textures[2] = loadImage("mercury.jpg");
  textures[3] = loadImage("venus.jpg");
  textures[4] = loadImage("jupiter.jpg");
  cam = new PeasyCam(this, 500);
  sun = new Planet(50, 0, 0, sunTexture);
  sun.spawnMoons(4, 1);
}

void draw() {
  background(0);
  //lights();
  //pointLight(255, 255, 255, 0, 0, 0);
  sun.show();
  sun.orbit();
}
