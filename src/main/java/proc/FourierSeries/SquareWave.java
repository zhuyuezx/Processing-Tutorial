package proc.FourierSeries;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;

public class SquareWave extends PApplet {

    float radius;
    float time;
    ArrayList<Float> wave;
    int level = 1;

    public void settings() {
        fullScreen();
        //size(960, 640);
        wave = new ArrayList<>();
    }

    public void draw() {
        pushMatrix();
        background(0);
        translate((float) width / 3, (float) height / 2);
        //ellipse(0, 0, radius * 2, radius * 2);
        float x = 0;
        float y = 0;
        for (int i = 0; i < level + 1; i++) {
            float prevX = x;
            float prevY = y;
            int n = 2 * i + 1;
            radius = 300 * (4 / (n * PI));
            strokeWeight(2);
            noFill();
            if (i < level) {
                x += radius * cos(n * time);
                y += radius * sin(n * time);
                stroke(255, 100);
                ellipse(prevX, prevY, radius * 2, radius * 2);

                stroke(255);
                line(prevX, prevY, x, y);
            } else {
                pushMatrix();
                float localTime = time % 3;
                if (localTime < 2) {
                    float alpha = map(localTime, 0, 3, 0, 255);
                    stroke(200, 200, 0 , alpha);
                    translate(width / 4, -height / 3);
                    ellipse(0, 0, radius * 2, radius * 2);
                    line(0, 0,
                            radius * sin(n * time),
                            radius * cos(n * time));
                } else {
                    stroke(200, 200, 0);
                    float scaleX = map(localTime, 2, 3, width / 4, prevX);
                    float scaleY = map(localTime, 2, 3, -height / 3, prevY);
                    translate(scaleX, scaleY);
                    ellipse(0, 0, radius * 2, radius * 2);
                    line(0, 0,
                            radius * sin(n * time),
                            radius * cos(n * time));
                }
                popMatrix();
            }
        }
        wave.add(0, y);

        stroke(255);
        translate((float) width / 3, 0);
        line(x - (float) width / 3, y, 0, wave.get(0));
        beginShape();
        noFill();
        for (int i = 0; i < wave.size(); i++) {
            vertex(i, wave.get(i));
        }
        endShape();
        time += 0.01;

        if (wave.size() > 1000) {
            wave.remove(wave.size() - 1);
        }

        level = (int)(time / 3) + 1;
        level = constrain(level, 1, 100);

        popMatrix();
        textSize(32);
        String msg = "Current Level: " + level;
        fill(238);
        text(msg, 40, 40, 280, 40);
        textSize(22);
        String msg2 = "2A/("+ (level * 2 - 1) +"Pi)*sin("+ (level * 2 - 1) +"Pi/2)";
        text(msg2, 1020, 30, 1200, 30);
    }

    public static void main(String[] args) {
        PApplet.main(SquareWave.class.getName());
    }
}
