package Tutorials.Ep8;

import processing.core.PApplet;

public class version1 extends PApplet {

    RandomWalker randomWalker1;
    RandomWalker randomWalker2;
    boolean initialize = false;

    public void settings() {
        size(960, 1000);
        randomWalker1 = new RandomWalker(this, 10, 10);
        randomWalker2 = new RandomWalker(this, 10, 10);
    }

    public void draw() {
        if (!initialize) {
            background(238);
            stroke(40);
            strokeWeight(3);
            initialize = true;
        }
        for (int n = 0; n < 100; n++) {
            randomWalker1.update();
            randomWalker2.update();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep8.version1");
    }
}
