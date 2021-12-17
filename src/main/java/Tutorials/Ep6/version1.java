package Tutorials.Ep6;

import processing.core.PApplet;

import java.awt.*;

public class version1 extends PApplet {

    float angle;

    public void settings() {
        size(1920, 1080);
    }

    public void draw() {
        noStroke();
        Color base = new Color(0, 15, 30);
        fill(base.getRGB());

        background(255);
        float x = width;
        float dia = 200;
        int num = 150;

        translate((float) width / 2, (float) height / 2);
        for (float a = 0; a < 360; a += 22.5) {
            rotate(radians(a));
            pushMatrix();
            for (int i = 0; i < num; i++) {
                //fill(base.getRGB(), map(num - i, 0, num, 0, 100));
                scale((float) 0.95);
                rotate(radians(angle));
                ellipse(x, 0, dia, dia);
            }
            popMatrix();

            pushMatrix();
            for (int i = 0; i < num; i++) {
                //fill(base.getRGB(), map(num - i, 0, num, 0, 100));
                scale((float) 0.95);
                rotate(-radians(angle));
                ellipse(x, 0, dia, dia);
            }
            popMatrix();
        }
        angle += 0.05;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep6.version1");
    }
}
