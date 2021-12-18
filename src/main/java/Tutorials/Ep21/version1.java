package Tutorials.Ep21;

import processing.core.PApplet;

public class version1 extends PApplet {

    float xStep = 20;
    float xx;
    float angle = 5;
    boolean initialize = false;

    public void settings() {
        size(900, 900);
    }

    public void draw() {
        if (!initialize) {
            stroke(0);
            strokeCap(CORNER);
            strokeWeight(xStep / 2);
            initialize = true;
        }
        background(255);

        xx = map(mouseX, 0, width, (float) -width / 2, (float) width / 2);
        //angle = map(mouseY, 0, height, -90, 90);
        xStep = map(mouseY, 0, height, 10, height);

        strokeWeight(xStep / 2);
        translate((float) width / 2, (float) height / 2);
        for (int x = -width / 2; x < width / 2; x += xStep) {
            line(x, (float) -height / 2, x, (float) height / 2);
        }

        rotate(radians(angle));
        for (int x = -width / 2; x < width / 2; x += xStep) {
            line(x + xx, (float) -height / 2, x + xx, (float) height / 2);
        }
        angle++;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep21.version1");
    }
}
