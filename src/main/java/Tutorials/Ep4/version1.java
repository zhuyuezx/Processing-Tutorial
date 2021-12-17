package Tutorials.Ep4;

import processing.core.PApplet;

public class version1 extends PApplet {
    float x, y;
    float xStep = 10;
    float yStep = 100;
    float a, a_;

    int num, numLim;
    boolean speedSign = true;

    public void settings() {
        size(1600, 800);
        num = 0;
        numLim = (int) ((width / xStep) * (height / yStep));
    }

    public void draw() {
        background(15, 20, 30);
        strokeCap(CORNER);
        strokeWeight(xStep);

        int n = 0;
        if (num < numLim && speedSign) {
            num++;
            if (num == numLim) {
                speedSign = false;
            }
        } else if (num > 0 && !speedSign) {
            num--;
            if (num == 0) {
                speedSign = true;
            }
        }
        while (n < num) {
            stroke(255 - 255 * cos(radians(a)), 255 * cos(radians(a)),
                    255 - 255 * sin(radians(a)), 255 - 255 * sin(radians(a)));
            line(x, y, x, y + yStep);
            x += xStep;
            if (x > width) {
                x = xStep / 2;
                y += yStep;
            }
            if (y >= height) {
                y = 0;
                a = 0;
            }
            n++;
            a += a_;
        }
        a_ += 1;
        delay(10);
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep4.version1");
    }
}
