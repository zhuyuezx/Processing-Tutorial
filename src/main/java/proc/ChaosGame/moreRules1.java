package proc.ChaosGame;

import processing.core.PApplet;
import processing.core.PVector;

public class moreRules1 extends PApplet {

    boolean initialize = false;
    PVector[] points;

    int numberOfNodes = 5;
    float percent = 0.5F;
    PVector current;
    int previous;

    public void settings() {
        size(1000, 1000);
        points = new PVector[numberOfNodes];
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
            line(points[0].x, points[0].y, points[points.length - 1].x, points[points.length - 1].y);

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
            stroke(255 / numberOfNodes * r, 200, 255);
            PVector next = points[r];
            if (r != (previous + 1) % numberOfNodes) {
                current.x = lerp(current.x, next.x, percent);
                current.y = lerp(current.y, next.y, percent);
                point(current.x, current.y);
                previous = r;
            }
        }
    }

    public void keyPressed() {
        if (key == ' ')
            saveFrame("/src/main/java/proc/ChaosGame/frame-####.jpg");
    }

    public static void main(String[] args) {
        PApplet.main(moreRules1.class.getName());
    }
}
