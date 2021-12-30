function Particle(x, y) {
    this.pos = createVector(x, y);
    this.prev = createVector(x, y);
    this.vel = p5.Vector.random2D();
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
      stroke(255, 15);
      strokeWeight(1);
      line(this.pos.x, this.pos.y, this.prev.x, this.prev.y);
    }

    this.attracted = function(target) {
        var force = p5.Vector.sub(target, this.pos);
        var dsquared = force.magSq();
        dsquared = constrain(dsquared, 5, 50);
        var G = 50;
        var strength = G / dsquared;
        force.setMag(strength);
        this.acc.add(force);
    }
  }