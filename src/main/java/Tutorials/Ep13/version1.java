package Tutorials.Ep13;

import processing.core.PApplet;

public class version1 extends PApplet {

    float x = 400;
    float y = 400;
    float angle;
    float dia = 20;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        background(21);
        stroke(255);
        strokeWeight(3);

        translate((float) width / 2, (float) height / 2);
        rotate(radians((float)(14.3 + angle / 3)));

        for (float a = 0; a < 360; a += 10) {
            pushMatrix();
            if (angle < 360) rotate(radians(a) * cos(radians(angle)));
            else rotate(radians(a));

            line(x * sin(radians(angle)), 0, 0, y - dia / 2);
            noStroke();
            fill(255);
            ellipse(x*sin(radians(angle)), 0, dia / 2, dia /2);

            stroke(255);
            noFill();
            ellipse(0, y, dia, dia);
            popMatrix();
        }
        angle++;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep13.version1");
    }
}
