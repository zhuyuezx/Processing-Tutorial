package Tutorials.Ep1;

import processing.core.PApplet;

public class version1 extends PApplet {
    public void settings() {
        size(800, 800);
        noLoop();
    }

    public void draw() {
        background(255);
        //translate((float)width / 2, (float)height / 2);
        for (int i = -100; i < width + 100; i += 200) {
            for (int j = -100; j < height + 100; j += 200) {
                noStroke();
                fill(random(255), random(255), random(255));
                rectMode(CENTER);
                rect(i, j, 200, 200);
                pushMatrix();
                translate(i, j);
                scale((float) 0.28);
                generateSingle();
                popMatrix();
            }
        }
        saveFrame("src/main/java/Tutorials/Ep1/frames/A_####.jpg");
    }

    private void generateSingle() {
        for (int n = 0; n < 30; n++) {
            stroke(random(255), random(255), random(255));
            for (int a = 0; a < 360; a += 1) {
                float s = random(50, 150);
                float e = random(150, 350);
                pushMatrix();
                rotate(radians(a));
                strokeCap(CORNER);
                strokeWeight(4);
                line(s, 0, e, 0);
                popMatrix();
            }
        }
    }

    public void keyPressed() {
        redraw();
    }

    public static void main(String... args) {
        PApplet.main("Tutorials.Ep1.version1");
    }
}
