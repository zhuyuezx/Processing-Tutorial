// Step1: what is perlin noise? apply in 2D ... Complete!
// Step2: illustrate flowfield by perlin noise ... Complete!
// Step3: create "Particle" class ... Complete!
// Step4: let particles follow the flowfield ... Complete!
// Step5: adding more particle generation ... Complete!
// Step6: changing color mode to HSB ... Complete!
// Step7: vectorize all the parameters to easier control ... Complete!
// Step8: Let's do some interesting modifications and enable saving ... Complete!

float angle = 0;
float strength = 0.005;
float noiseValue;
float speed = 100;
float angleMag = 1;
float colorFreq = 1.5;
float transparency = 150;

ArrayList<Particle> particles;

void setup() {
  fullScreen();
  background(0);
  colorMode(HSB);
  particles = new ArrayList<Particle>();
}

void draw() {
  //loadPixels();
  //for (int y = 0; y < height; y++) {
  //  for (int x = 0; x < width; x++) {
  //    float noiseValue = noise(x * strength, y * strength);
  //    color toFill = color(noiseValue * 255);
  //    pixels[x + y * width] = toFill;
  //  }
  //}
  //updatePixels();
  for (Particle p : particles) p.update();

  if (particles.size() < 10000) {
    for (int i = 0; i < 10; i++)
      particles.add(new Particle(random(width), random(height)));
  }
}

class Particle {

  PVector pos;
  PVector vel;

  Particle(float x, float y) {
    pos = new PVector(x, y);
    vel = new PVector(0, 0);
  }

  void update() {
    if (pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height) 
      return;

    noiseValue = noise(pos.x * strength, pos.y * strength);
    vel = PVector.fromAngle(noiseValue * angleMag * TWO_PI);
    vel.setMag(speed);
    pos.add(PVector.div(vel, frameRate));
    display();
  }

  void display() {
    fill((255 * noiseValue * colorFreq) % 255, 255, 255, transparency);
    noStroke();
    ellipse(pos.x, pos.y, 0.5, 0.5);
  }
}

void keyPressed() {
  if (key == ' ') {
   saveFrame("frame-####.jpg"); 
  }
}
