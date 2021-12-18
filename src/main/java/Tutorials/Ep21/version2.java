package Tutorials.Ep21;

import processing.core.PApplet;

public class version2 extends PApplet {

    float xStep = 20;
    float xStepCounter = 1;
    float angle = 10;
    boolean initialize = false;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        if (!initialize) {
            stroke(0);
            strokeCap(CORNER);
            strokeWeight(xStep / 2);
            initialize = true;
        }
        background(255);
        xStep = map(xStepCounter, 0, width, width, 15);
        if (xStepCounter < width) xStepCounter++;

        strokeWeight(xStep / 2);
        translate((float) width / 2 - 100, (float) height / 2);
        for (int x = -width / 2; x < width / 2; x += xStep) {
            line(x, (float) -height / 2, x, (float) height / 2);
        }

        rotate(radians(angle));
        for (int x = -width / 2; x < width / 2; x += xStep) {
            line(x, (float) -height / 2, x, (float) height / 2);
        }
        if (xStepCounter == width) angle += 0.1;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep21.version2");
    }
}
