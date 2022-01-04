package proc.ChaosGame;

import processing.core.PApplet;
import processing.core.PVector;

public class ver2 extends PApplet {

    boolean initialize = false;
    PVector[] points;

    int numberOfNodes = 5;
    float percent = 0.5F;
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
            background(0);
            stroke(238);

            for (int i = 0; i < numberOfNodes; i++) {
                float angle = i * TWO_PI / numberOfNodes;
                points[i] = PVector.fromAngle(angle);
                points[i].mult(600);
            }

            current = new PVector(random(width), random(height));

            strokeWeight(4);
//            for (PVector p : points) {
//                point(p.x, p.y);
//            }
            initialize = true;
        }
        for (int i = 0; i < 1000; i++) {
            strokeWeight(1);

            int r = (int) random(points.length);
            PVector next = points[r];
            stroke(238, 0, 238);
            if (next != previous) {
                current.x = lerp(current.x, next.x, percent);
                current.y = lerp(current.y, next.y, percent);
                point(current.x, current.y);
            }
            previous = next;
        }
    }

    public static void main(String[] args) {
        PApplet.main(ver2.class.getName());
    }
}
