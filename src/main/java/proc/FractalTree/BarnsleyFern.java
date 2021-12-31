package proc.FractalTree;

import processing.core.PApplet;

public class BarnsleyFern extends PApplet {

    float x, y;
    boolean initialize = false;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        if (!initialize) {
            background(238);
            initialize = true;
        }
        for (int i = 0; i < 500; i++) {
            drawPoint();
            nextPoint();
        }
    }

    public void drawPoint() {
        stroke(34, 139, 34);
        strokeWeight(2);
        float px = map(x, (float) -2.1820, (float) 2.6558, 0, width);
        float py = map(y, 0, (float) 9.9983, height, 0);
        point(px, py);
    }

    public void nextPoint() {
        float nextX, nextY;
        float rand = random(1);
        // #1
        if (rand < 0.01) {
            nextX = 0;
            nextY = (float) (0.16 * y);
        }
        // #2
        else if (rand < 0.86) {
            nextX = (float) (0.85 * x + 0.04 * y);
            nextY = (float) (-0.04 * x + 0.85 * y + 1.6);
        }
        // #3
        else if (rand < 0.93) {
            nextX = (float) (0.2 * x - 0.26 * y);
            nextY = (float) (0.23 * x + 0.22 * y + 1.6);
        }
        // #4
        else {
            nextX = (float) (-0.15 * x + 0.28 * y);
            nextY = (float) (0.26 * x + 0.24 * y + 0.44);
        }
        x = nextX;
        y = nextY;
    }

    public static void main(String[] args) {
        PApplet.main(BarnsleyFern.class.getName());
    }
}
