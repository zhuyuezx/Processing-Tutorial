package Tutorials.Ep27;

import processing.core.PApplet;

public class version1 extends PApplet {

    float x;
    float sw = 4;
    float angleVar = 360;
    boolean initialize = false;

    public void settings() {
        fullScreen();
        //size(900, 900);
        //noLoop();

        mouseX = 10;
    }

    public void draw() {
        if (!initialize) {
            strokeCap(CORNER);
            initialize = true;
        }

        background(0, 15, 35);
        translate((float) width / 2, (float) height / 2);

        for (int angle = 0; angle < 360; angle += angleVar) {
            if (angleVar <= 11) x = random(300, 350);
            else x = 350;

            pushMatrix();
            rotate(radians(angle));

            stroke(238);
            strokeWeight(sw);
            line(x, 0, width, 0);
            popMatrix();
        }

        if (angleVar > 1) {
            angleVar -= 1;
        }
        else {
            angleVar = 1;
            frameRate(1);
        }
        //angleVar = map(mouseX, 0, width, 1, 360);
        sw = map(angleVar, 1, 360, 4, 128);
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep27.version1");
    }
}
