function Particle(x, y) {
    this.pos = createVector(x, y);
    this.prev = createVector(x, y);
    this.vel = createVector();
    this.vel.setMag(random(2, 5));
    this.acc = createVector();
  
    this.update = function() {
      this.prev.x = this.pos.x;
      this.prev.y = this.pos.y;  
      this.vel.add(this.acc);
      this.pos.add(this.vel);
      this.acc.mult(0);
    }

    this.show = function() {
      stroke(255, 150);
      strokeWeight(4);
      line(this.pos.x, this.pos.y, this.prev.x, this.prev.y);
    }

    this.attracted = function(target) {
        var force = p5.Vector.sub(target, this.pos);
        var d = force.mag();
        d = constrain(d, 1, 25);
        var G = 50;
        var strength = G / (d * d);
        force.setMag(strength);
        if (d < 20) {
            force.mult(-10);
        }
        this.acc.add(force);
    }
  }