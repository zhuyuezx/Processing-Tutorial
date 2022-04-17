package proc.FourierSeries;

import processing.core.PApplet;

import java.util.ArrayList;

public class polynomial extends PApplet {

    ArrayList<Float> wave;
    int level = 200;
    float l = TWO_PI;
    float time = 0;
    float radius;

    public void settings() {
        fullScreen();
        //size(960, 640);
        wave = new ArrayList<>();
    }

    public void draw() {
        pushMatrix();
        background(0);
        translate((float) width / 3, (float) height / 2);
        float x = 0;
        float y = 0;
        for (int i = 0; i < level; i++) {
            float prevX = x;
            float prevY = y;
            int n = i + 1;
            int sign = (n % 2 == 0) ? -1: 1;
            radius = 50 * (2 * l / (n * PI));
            x += sign * radius * cos(n * PI * time / l);
            y += sign * radius * sin(n * PI * time / l);
            stroke(255, 100);
            strokeWeight(2);
            noFill();
            ellipse(prevX, prevY, radius * 2, radius * 2);

            stroke(255);
            line(prevX, prevY, x, y);
        }
        wave.add(0, y);

        translate((float) width / 3, 0);
        line(x - (float) width / 3, y, 0, wave.get(0));
        beginShape();
        noFill();
        for (int i = 0; i < wave.size(); i++) {
            vertex(i, wave.get(i));
        }
        endShape();
        time += 0.02;
        if (wave.size() > 1000) {
            wave.remove(wave.size() - 1);
        }
        popMatrix();
        textSize(32);
        String msg = "Current Layer: " + level;
        fill(238);
        text(msg, 40, 40, 400, 40);
    }

    public static void main(String[] args) {
        PApplet.main(polynomial.class.getName());
    }
}
