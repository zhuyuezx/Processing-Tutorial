package proc.ChaosGame;

import processing.core.PApplet;

public class ver1 extends PApplet {

    boolean initialize = false;

    float ax,ay;
    float bx, by;
    float cx, cy;
    float x, y;

    public void settings() {
        fullScreen();
        //size(600, 600);
    }

    public void draw() {
        if (!initialize) {
            background(0);
            stroke(238);
//            ax = (float) width / 6; ay = (float) height * 7 / 8;
//            bx = (float) width / 2; by = (float) height / 8;
//            cx = (float) width * 5 / 6; cy = (float) height * 7 / 8;
            ax = 0; ay = height;
            bx = (float) width / 2; by = 0;
            cx = width; cy = height;
//            ax = random(width); ay = random(height);
//            bx = random(width); by = random(height);
//            cx = random(width); cy = random(height);

            x = random(width); y = random(height);
            point(ax, ay); point(bx, by); point(cx, cy);
            initialize = true;
        }
        for (int i = 0; i < 100; i++) {
            point(x, y);

            int r = (int)random(3);
            if (r == 0) {
                stroke(238, 238, 0);
                x = lerp(x, ax, (float) 0.5);
                y = lerp(y, ay, (float) 0.5);
            } else if (r == 1) {
                stroke(0, 238, 238);
                x = lerp(x, bx, (float) 0.5);
                y = lerp(y, by, (float) 0.5);
            } else {
                stroke(238, 0, 238);
                x = lerp(x, cx, (float) 0.5);
                y = lerp(y, cy, (float) 0.5);
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main(ver1.class.getName());
    }
}
