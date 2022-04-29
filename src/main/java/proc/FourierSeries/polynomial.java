package proc.FourierSeries;

import processing.core.PApplet;

import java.util.ArrayList;

public class polynomial extends PApplet {
    int level = 0;
    float l = TWO_PI;
    boolean initialized = false;
    float[] wave;
    float[] after;
    int count;
    float coef1;
    float coef2;
    float coef3;
    float adjust;

    public void settings() {
        fullScreen();
        count = 50;
    }

    public void draw() {
        if (!initialized) {
            background(0);
            coef1 = 0f;
            coef2 = 1f;
            coef3 = 0f;
            wave = new float[height];
            after = new float[height];
            adjust = height / (coef3 * l * l * l + coef2 * l * l + coef1 * l);
            for (int i = 0; i < height; i++) {
                float cons = -coef2 * adjust * l * l / 3;
                wave[i] = cons;
                after[i] = cons;
            }
            stroke(200);
            strokeWeight(3);
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
        int n = level + 1;
        int sign1 = (n % 2 == 0) ? 1 : -1;
        int sign2 = -sign1;
        float radius1 = adjust * (2 * l / (n * PI));
        float radius2 = adjust * (4 * l * l / (level * level * PI * PI));
        float radius3 = adjust * (2 * l * l * l / (n * n * n * PI * PI * PI));
        radius3 *= (n * n * PI * PI - 6);
        for (int curr = 0; curr < height; curr++) {
            after[curr] += sign1 * coef1 * radius1 * sin(n * PI * (0.0058f * curr) / l);
            if (level != 0)
                after[curr] -= sign2 * coef2 * radius2 * cos(level * PI * (0.0058f * curr) / l);
            after[curr] += sign1 * coef3 * radius3 * sin(n * PI * (0.0058f * curr) / l);
        }
    }

    public void calculate_one_term() {
        for (int curr = 0; curr < height; curr++) {
            wave[curr] = lerp(wave[curr], after[curr], 0.05f);
        }
    }

//    ArrayList<Float> wave;
//    int level = 200;
//    float l = TWO_PI;
//    float time = 0;
//    float radius;
//
//    public void settings() {
//        fullScreen();
//        //size(960, 640);
//        wave = new ArrayList<>();
//    }
//
//    public void draw() {
//        pushMatrix();
//        background(0);
//        translate((float) width / 3, (float) height / 2);
//        float x = 0;
//        float y = 0;
//        for (int i = 0; i < level; i++) {
//            float prevX = x;
//            float prevY = y;
//            int n = i + 1;
//            int sign = (n % 2 == 0) ? -1: 1;
//            radius = 50 * (2 * l / (n * PI));
//            x += sign * radius * cos(n * PI * time / l);
//            y += sign * radius * sin(n * PI * time / l);
//            stroke(255, 100);
//            strokeWeight(2);
//            noFill();
//            ellipse(prevX, prevY, radius * 2, radius * 2);
//
//            stroke(255);
//            line(prevX, prevY, x, y);
//        }
//        wave.add(0, y);
//
//        translate((float) width / 3, 0);
//        line(x - (float) width / 3, y, 0, wave.get(0));
//        beginShape();
//        noFill();
//        for (int i = 0; i < wave.size(); i++) {
//            vertex(i, wave.get(i));
//        }
//        endShape();
//        time += 0.02;
//        if (wave.size() > 1000) {
//            wave.remove(wave.size() - 1);
//        }
//        popMatrix();
//        textSize(32);
//        String msg = "Current Layer: " + level;
//        fill(238);
//        text(msg, 40, 40, 400, 40);
//    }

    public static void main(String[] args) {
        PApplet.main(polynomial.class.getName());
    }
}
