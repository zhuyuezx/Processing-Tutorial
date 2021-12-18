package Tutorials.Ep7;

import processing.core.PApplet;

public class version1 extends PApplet {

    float x, y;
    float dia = 700;
    float sw = 10;

    public void settings() {
        size(800, 800);
        x = (float) width / 2;
        y = (float) height / 2;
    }

    public void draw() {
        fill(40, 40, 40);
        stroke(40, 40, 40);
        rectMode(CENTER);
        strokeWeight(sw);

        background(238, 238, 238);
        rect(x, y, dia, dia);

        x += random(-10, 10);
        y += random(-10, 10);

        x = constrain(x, dia / 2 + sw / 2, width - dia / 2 - sw / 2);
        y = constrain(y, dia / 2 + sw / 2, height - dia / 2 - sw / 2);
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep7.version1");
    }
}
