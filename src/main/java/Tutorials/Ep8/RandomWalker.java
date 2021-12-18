package Tutorials.Ep8;

import processing.core.PApplet;

public class RandomWalker {

    float x1, y1, x2, y2;
    float xStep, yStep;
    float rOff, red;
    version1 operator;

    public RandomWalker(version1 operator, float xStep, float yStep) {
        x1 = (float)operator.width / 2;
        y1 = (float) operator.height / 2;
        x2 = (float)operator.width / 2;
        y2 = (float) operator.height / 2;

        this.xStep = xStep;
        this.yStep = yStep;
        this.operator = operator;
    }

    void update() {
        x1 += operator.random(-xStep, xStep);
        y1 += operator.random(-yStep, yStep);
        x2 += operator.random(-xStep, xStep);
        y2 += operator.random(-yStep, yStep);

        x1 = PApplet.constrain(x1, 0, operator.width);
        y1 = PApplet.constrain(y1, 0, operator.height);
        x2 = PApplet.constrain(x2, 0, operator.width);
        y2 = PApplet.constrain(y2, 0, operator.height);

        rOff += 0.01;
        red = operator.noise(rOff) * 200;
        operator.stroke(red, 0, 0);
        operator.line(x1, y1, x2, y2);
    }
}
