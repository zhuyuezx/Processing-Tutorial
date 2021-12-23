package proc.SnowFall;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class SnowFlake {

    PImage img;
    PVector pos;
    PVector vel;
    PVector acc;

    float angle;
    float dir;
    float xOff;
    float r;
    SnowFall operator;

    public SnowFlake(float sx, float sy, PImage sImg, SnowFall operator) {
        img = sImg;
        pos = new PVector(sx, sy);
        vel = new PVector(0, 0);
        acc = new PVector();
        angle = operator.random((float) (Math.PI * 2));
        dir = (operator.random(1) > 0.5) ? 1 : -1;
        xOff = 0;
        this.operator = operator;
        r = getRandomSize();
    }

    public void applyForce(PVector force) {
        PVector f = force.copy();
        f.mult(r);

        acc.add(f);
    }

    public void randomize() {
        float x = operator.random(operator.width);
        float y = operator.random(-100, -10);
        pos = new PVector(x, y);
        vel = new PVector(0, 0);
        acc = new PVector();
        r = getRandomSize();
    }

    public void update() {
        xOff = PApplet.sin(angle * 2) * 2 * r;

        vel.add(acc);
        vel.limit((float) (r * 0.2));

        if (vel.mag() < 1)
            vel.normalize();

        pos.add(vel);
        acc.mult(0);

        if (pos.y > operator.height + r){
            randomize();
        }

        if (pos.x < -r)
            pos.x = operator.width + r;

        if (pos.x > operator.width + r) {
            pos.x = -r;
        }

        angle += dir * vel.mag() / 200;
    }

    public void render() {
        operator.pushMatrix();
        operator.translate(pos.x + xOff, pos.y);
        operator.rotate(angle);
        operator.imageMode(PConstants.CENTER);
        operator.image(img, 0, 0, r, r);
        operator.popMatrix();
    }

    public float getRandomSize() {
        float r = (float) Math.pow(operator.random(0, 1), 3);
        return PApplet.constrain(r * 32, 2, 32);
    }
}

