class Planet {
  float radius;
  float angle;
  float distance;
  Planet[] planets;
  float orbitSpeed;
  PVector v;

  PShape globe;

  Planet(float r, float d, float o, PImage img) {
    v = PVector.random3D();
    v.mult(d);

    radius = r;
    distance = d;
    angle = random(TWO_PI);
    orbitSpeed = o;

    noStroke();
    noFill();
    globe = createShape(SPHERE, radius);
    globe.setTexture(img);
  }

  void spawnMoons(int total, int level) {
    planets = new Planet[total];
    for (int i = 0; i < total; i++) {
      float r = radius / (level * 2);
      float d = random(radius + r, (radius + r) *2);
      float o = random(-0.05, 0.05);
      int index = int(random(0, textures.length));
      planets[i] = new Planet(r, d, o, textures[index]);
      if (level < 2) {
        int num = int(random(1, 3));
        planets[i].spawnMoons(num, level + 1);
      }
    }
  }

  void orbit() {
    angle += orbitSpeed; 
    if (planets == null) return;
    for (Planet moon : planets) {
      moon.orbit();
    }
  }

  void show() {
    pushMatrix();
    //rotate(angle);

    PVector v2 = new PVector(1, 0, 1);
    PVector p = v.cross(v2);
    rotate(angle, p.x, p.y, p.z);
    stroke(255);
    //line(0, 0, 0, v.x, v.y, v.z);
    //line(0, 0, 0, p.x, p.y, p.z);

    translate(v.x, v.y, v.z);
    noStroke();
    fill(255);
    shape(globe);
    if (distance==0) {
      pointLight(255, 255, 255, 0, 0, 0);
      ambientLight(70, 70, 70);
    }
    //sphere(radius);
    //ellipse(0, 0, radius * 2, radius * 2);

    if (planets == null) {
      popMatrix();
      return;
    }
    for (Planet moon : planets) {
      moon.show();
    }
    popMatrix();
  }
}
