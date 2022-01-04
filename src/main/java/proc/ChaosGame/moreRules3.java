package proc.ChaosGame;

import processing.core.PApplet;
import processing.core.PVector;

public class moreRules3 extends PApplet {

    boolean initialize = false;
    PVector[] points;

    int numberOfNodes = 4;
    float percent = (float) 2 / 3;
    PVector current;
    int previous1;
    int previous2;

    public void settings() {
        size(1000, 1000);
        points = new PVector[numberOfNodes + 1];
        //size(600, 600);
    }

    public void draw() {
        translate(width / 2, height / 2);
        if (!initialize) {
            colorMode(HSB);
            background(0);
            stroke(238);

            for (int i = 0; i < numberOfNodes; i++) {
                float angle = i * TWO_PI / numberOfNodes;
                points[i] = PVector.fromAngle(angle - PI / 4);
                points[i].mult(600);
                if (i > 0) {
                    stroke(0, 0, 238);
                    line(points[i].x, points[i].y, points[i - 1].x, points[i - 1].y);
                }
            }
            points[points.length - 1] = new PVector();
            line(points[0].x, points[0].y, points[points.length - 2].x, points[points.length - 2].y);

            current = new PVector(random(width), random(height));

            strokeWeight(4);
//            for (PVector p : points) {
//                point(p.x, p.y);
//            }
            initialize = true;
        }
        for (int i = 0; i < 2000; i++) {
            strokeWeight(1);

            int r = (int) random(points.length);
            stroke(255 / points.length * r, 200, 255);
            PVector next = points[r];
            if (checkValid(r)) {
                current.x = lerp(current.x, next.x, percent);
                current.y = lerp(current.y, next.y, percent);
                point(current.x, current.y);
                previous2 = previous1;
                previous1 = r;
            }
        }
    }

    public boolean checkValid(int r) {
        return true;
    }

    public static void main(String[] args) {
        PApplet.main(moreRules3.class.getName());
    }
}
