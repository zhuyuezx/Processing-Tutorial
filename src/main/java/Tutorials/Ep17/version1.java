package Tutorials.Ep17;

import processing.core.PApplet;

public class version1 extends PApplet {

    int num = 30;
    float[] y = new float[num];
    float sw;
    boolean initialize = false;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        if (!initialize) {
            for (int n = 0; n < num; n++) {
                y[n] = (float) height / num * n;
            }
            sw = (float) height / num / 2;
            initialize = true;
        }
        background(6, 19, 36);

        for (int n = 0; n < num; n++) {
            float alpha = map(y[n], 0, height, 0, 255);
            stroke(241, 0, 0, alpha);
            strokeWeight(sw);
            line(0, y[n], width, y[n]);

            y[n] += 0.5;
            if (y[n] > height) y[n] = 0;
        }

        stroke(6, 19, 36);
        strokeWeight( (float) (height / 1.4));
        noFill();
        ellipse((float) width / 2, (float) height / 2,
                (float) height * 10 / 6, (float) height * 10 / 6);
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep17.version1");
    }
}
