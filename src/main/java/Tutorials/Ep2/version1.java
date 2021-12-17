package Tutorials.Ep2;

import processing.core.PApplet;

public class version1 extends PApplet {
    float arcSize = 400;

    public void settings() {
        size(800, 800);
    }

    public void draw() {
        background(238);
        noFill();
        stroke(20);
        strokeWeight(66);
        float arcLength = map(mouseX, 0, width, 0, TWO_PI);
        arc((float)width / 2, (float)height / 2, arcSize, arcSize, 0, arcLength);
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep2.version1");
    }
}
