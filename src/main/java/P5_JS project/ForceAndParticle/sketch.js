var attractors = [];
var particles = [];

function setup() {
  //frameRate(1);
  createCanvas(400, 400);
  for (var i = 0; i < 100; i++) {
    particles.push(new Particle(200, 200));
  }
  for (var i = 0; i < 10; i++) {
    attractors.push(createVector(random(width), random(height)));
  }
  background(51);
}

function draw() {
  //background(51);
  stroke(255);
  strokeWeight(4);
  for (var i = 0; i < attractors.length; i++) {
    //point(attractors[i].x, attractors[i].y);
  }
  for (var i = 0; i < particles.length; i++) {
    for (var j = 0; j < attractors.length; j++) {
      var particle = particles[i];
      particle.attracted(attractors[j]);
    }
    particle.update();
    particle.show();
  }
}
