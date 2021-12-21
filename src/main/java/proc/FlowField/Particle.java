package proc.FlowField;

import processing.core.PVector;

public class Particle {

    PVector pos;
    PVector vel;
    PVector acc;
    PVector previousPos;
    float maxSpeed;

    Coordinator operator;

    public Particle(PVector start, float maxSpeed, Coordinator operator) {
        this.maxSpeed = maxSpeed;
        pos = start;
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        previousPos = pos.copy();
        this.operator = operator;
    }

    public void run() {
        update();
        edges();
        show();
    }

    public void update() {
        pos.add(vel);
        vel.limit(maxSpeed);
        vel.add(acc);
        acc.mult(0);
    }

    public void applyForce(PVector force) {
        acc.add(force);
    }

    public void edges() {
        if (pos.x > operator.width) {
            pos.x = 0;
            updatePreviousPos();
        }
        if (pos.x < 0) {
            pos.x = operator.width;
            updatePreviousPos();
        }
        if (pos.y > operator.height) {
            pos.y = 0;
            updatePreviousPos();
        }
        if (pos.y < 0) {
            pos.y = operator.height;
            updatePreviousPos();
        }
    }

    public void show() {
        operator.stroke(0, 5);
        operator.strokeWeight(1);
        operator.line(pos.x, pos.y, previousPos.x, previousPos.y);
        updatePreviousPos();
    }

    public void updatePreviousPos() {
        this.previousPos.x = pos.x;
        this.previousPos.y = pos.y;
    }

    public void follow(FlowField flowField) {
        int x = (int) Math.floor(pos.x / flowField.scl);
        int y = (int) Math.floor(pos.y / flowField.scl);
        int index = x + y * flowField.cols;

        PVector force = flowField.vectors[index];
        applyForce(force);
    }
}
