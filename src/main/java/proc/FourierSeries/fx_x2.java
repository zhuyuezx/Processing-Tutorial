package proc.FourierSeries;

import processing.core.PApplet;

public class fx_x2 extends PApplet {
    int level = 1;
    float l = TWO_PI;
    boolean initialized = false;
    float[] wave;
    float[] after;
    int count;

    public void settings() {
        fullScreen();
        //size(960, 640);
        count = 0;
    }

    public void draw() {
        if (!initialized) {
            background(0);
            stroke(200);
            strokeWeight(3);
            wave = new float[height];
            after = new float[height];
            for (int i = 0; i < height; i++) {
                int constant = -height / 3;
                wave[i] = constant;
                after[i] = constant;
            }
            initialized = true;
        }
        pushMatrix();
        background(0);
        line(width - height, 0, width - height, height);
        line(width - height, height, width, height);
        translate(width - height, height);
        if (count == 50) {
            wave = after.clone();
            calculate_after(level);
            count = 0;
            level++;
        }
        calculate_one_term();
//        calculate_wave();
        beginShape();
        strokeWeight(1);
        noFill();
        for (int i = 0; i < height; i++) {
            vertex(i, wave[i]);
        }
        endShape();
        popMatrix();
        count++;

        textSize(32);
        String msg = "Current Layer: " + level;
        fill(238);
        text(msg, 40, 40, 400, 40);
    }

    public void calculate_after(int level) {
        int sign = (level % 2 == 0) ? 1 : -1;
        float radius = (height / l / l) * (4 * l * l / (level * level * PI * PI));
        for (int curr = 0; curr < height; curr++) {
            //after[curr] = wave[curr];
            after[curr] -= sign * radius * cos(level * PI * (0.0058f * curr) / l);
        }
    }

    public void calculate_one_term() {
        for (int curr = 0; curr < height; curr++) {
            wave[curr] = lerp(wave[curr], after[curr], 0.05f);
        }
    }

    public static void main(String[] args) {
        PApplet.main(fx_x2.class.getName());
    }
}
