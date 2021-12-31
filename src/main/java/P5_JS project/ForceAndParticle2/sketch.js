var attractors = [];
var particles = [];

function setup() {
  //frameRate(1);
  createCanvas(
    window.innerWidth,
    window.innerHeight
    );
  // for (var i = 0; i < 10; i++) {
  //   attractors.push(createVector(random(width), random(height)));
  // }
  background(51);
}

function mousePressed() {
  if (mouseButton == LEFT)
  attractors.push(createVector(mouseX, mouseY));
  else if (mouseButton == RIGHT)
  attractors.splice(attractors.length - 1, 1);
}

function draw() {
  background(51);
  stroke(255);
  strokeWeight(4);
  particles.push(new Particle(random(width), random(height)));

  if (particles.length > 400) {
    particles.splice(0, 1);
  }

  for (var i = 0; i < attractors.length; i++) {
    stroke(0, 255, 0);
    point(attractors[i].x, attractors[i].y);
  }
  for (var i = 0; i < particles.length; i++) {
    var particle = particles[i];
    for (var j = 0; j < attractors.length; j++) {
      particle.attracted(attractors[j]);
    }
    particle.update();
    particle.show();
  }
}
