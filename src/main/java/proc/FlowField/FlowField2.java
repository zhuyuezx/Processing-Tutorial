package proc.FlowField;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class FlowField2 extends PApplet {

    ArrayList<Particle> particles;
    float constant = (float) 0.005;
    float noiseValue;
    float speed = 100;
    boolean initialize = false;

    public void settings() {
        fullScreen();
        particles = new ArrayList<>();
    }

    public void draw() {
        if (!initialize) {
            colorMode(HSB);
            background(0);
            initialize = true;
        }
        for (Particle p : particles) {
            p.update();
        }
        if (particles.size() < 10000) {
            for (int w = 0; w < 10; w++) {
                particles.add(new Particle(random(width), random(height)));
            }
        }
    }

    public void keyPressed() {
        if (key == ' ')
        saveFrame("src/main/java/proc/FlowField/frames/####.jpg");
    }

    private class Particle {
        PVector position;
        PVector velocity;

        public Particle(float x, float y) {
            position = new PVector(x, y);
            velocity = new PVector(0, 0);
        }

        public void update() {
            if (position.x > 0 && position.x < width && position.y > 0 && position.y < height) {
                noiseValue = noise(position.x * constant, position.y * constant);
                velocity = PVector.fromAngle(noiseValue * 2 * PI);
                velocity.setMag(speed);
                position.add(PVector.div(velocity, frameRate));
                display();
            }
        }

        public void display() {
            fill((float) ((255 * noiseValue * 1.5) % 255), 255, 255);
            noStroke();
            ellipse(position.x, position.y, (float) 0.5, (float) 0.5);
        }
    }

    public static void main(String[] args) {
        PApplet.main("proc.FlowField.FlowField2");
    }
}
