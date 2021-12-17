package Tutorials.Ep5;

import processing.core.PApplet;

public class version1 extends PApplet {
    float angle;

    public void settings() {
        size(960, 1000);
    }

    public void draw() {
        background(0, 15, 30);
        surface.setLocation(957, 0);
        rectMode(CENTER);
        stroke(0, 15, 30);
        strokeWeight(25);

        translate((float) width / 2, (float) height / 2);
        float scaleVar = map(mouseX, 0, width, (float) 0.5, 5);
        scale(scaleVar);

        for (int i = 0; i < 150; i++) {
            fill(i * 10, 255 - i * 25, 255 - i * 10);
            scale((float) 0.95);
            rotate(radians(angle));
            rect(0, 0, 600, 600);
        }
        angle += 0.1;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep5.version1");
    }
}
