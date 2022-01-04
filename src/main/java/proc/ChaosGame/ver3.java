package proc.ChaosGame;

import processing.core.PApplet;
import processing.core.PVector;

public class ver3 extends PApplet {

    boolean initialize = false;
    PVector[] points;

    int numberOfNodes = 15;
    float percent = 0.75F;
    int resetCount = 0;
    PVector current;
    PVector previous;

    public void settings() {
        size(1000, 1000);
        points = new PVector[numberOfNodes];
        //size(600, 600);
    }

    public void draw() {
        translate(width / 2, height / 2);
        if (!initialize) {
            reset();
            initialize = true;
        }
        if (frameCount % 150 == 0)
            reset();

        for (int i = 0; i < 1000; i++) {
            strokeWeight(1);

            int r = (int) random(points.length);
            PVector next = points[r];
            stroke(238);
            if (next != previous) {
                current.x = lerp(current.x, next.x, percent);
                current.y = lerp(current.y, next.y, percent);
                point(current.x, current.y);
            }
            previous = next;
        }
    }

    public void reset() {
        background(0);
        stroke(238);
        percent = (float) (0.8 + 0.15 * sin(TWO_PI / 20 * resetCount));
        resetCount++;

        for (int i = 0; i < numberOfNodes; i++) {
            float angle = i * TWO_PI / numberOfNodes;
            points[i] = PVector.fromAngle(angle);
            points[i].mult(400);
        }

        current = new PVector(random(width), random(height));

        strokeWeight(4);
        for (PVector p : points) {
            point(p.x, p.y);
        }
    }

    public static void main(String[] args) {
        PApplet.main(ver3.class.getName());
    }
}
