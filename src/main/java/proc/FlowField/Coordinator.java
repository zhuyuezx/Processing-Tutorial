package proc.FlowField;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Coordinator extends PApplet {

    FlowField flowField;
    ArrayList<Particle> particles;

    boolean debug = false;
    boolean initialize = false;

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        if (!initialize) {
            flowField = new FlowField(10, this);
            flowField.update();

            particles = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                PVector start = new PVector(random(width), random(height));
                particles.add(new Particle(start, random(2, 8),this));
            }

            background(255);
            initialize = true;
        }
        flowField.update();

        if (debug) {
            flowField.display();
            System.out.println(frameRate);
        }

        for (Particle p: particles) {
            p.follow(flowField);
            p.run();
        }
    }

    public static void main(String[] args) {
        PApplet.main("proc.FlowField.Coordinator");
    }
}
