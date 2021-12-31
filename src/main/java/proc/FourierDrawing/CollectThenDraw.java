package proc.FourierDrawing;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class CollectThenDraw extends PApplet {

    Phasor[] fourierX;
    Phasor[] fourierY;
    ArrayList<PVector> path = new ArrayList<>();
    float[] pointX;
    float[] pointY;
    float dt;
    float time = 0;

    int size = 1000;
    boolean recording = true;
    boolean initialize = false;
    int pointCount = 0;
    boolean lastMousePress = false;
    boolean[] drawOrNot;

    public void settings() {
        fullScreen();
        //size(1000, 800);
        pointX = new float[size];
        pointY = new float[size];
        drawOrNot = new boolean[size];
    }

    public void draw() {
        strokeWeight(2);
        if (recording) {
            if (frameCount == 1) background(0);
            fill(0);
            noStroke();
            rect(0, 0, 1000, 100);

            fill(238);
            textSize(24);
            String msg = "Remaining Point to Draw: " + (size - pointCount) + " Points";
            text(msg, 40, 40, 1000, 40);
            recordData();

            stroke(255);
            if (pointCount > 3 && mousePressed) {
                if (lastMousePress)
                    line(pointX[pointCount - 2] + width / 2, pointY[pointCount - 2] + height / 2,
                        mouseX, mouseY);
            }
            lastMousePress = mousePressed;
            return;
        }

        if (!recording && !initialize) {
            fourierX = dft(pointX);
            fourierY = dft(pointY);

            Sort(fourierX);
            Sort(fourierY);

            dt = TWO_PI / fourierX.length;
            initialize = true;
        }
        background(0);
        float[] vx;
        float[] vy;
        if (time > TWO_PI) {
            time = 0;
            path = new ArrayList<>();
        }
        time += dt;

        vx = epiCycles(width / 2, 100, fourierX, 0);
        vy = epiCycles(100, height / 2, fourierY, HALF_PI);

        line(vx[0], 100, vx[0], vy[1]);
        line(100, vy[1], vx[0], vy[1]);

        ellipse(vx[0], vy[1], 5, 5);
        path.add(new PVector(vx[0], vy[1]));

        noFill();
        beginShape();
        for (int i = 1; i < path.size(); i++) {
            if (i < size - 1 && drawOrNot[i + 1])
                line(path.get(i - 1).x, path.get(i - 1).y, path.get(i).x, path.get(i).y);
                //vertex(vertex.x, vertex.y);
        }
        endShape();
    }

    public Phasor[] dft(float[] x) { // discrete Fourier Transform
        int N = x.length;
        Phasor[] X = new Phasor[N];
        for (int k = 0; k < N; k++) {
            float re = 0;
            float im = 0;
            for (int n = 0; n < N; n++) {
                float phi = (TWO_PI * k * n) / N;
                re += x[n] * cos(phi);
                im -= x[n] * sin(phi);
            }
            re = re / N;
            im = im / N;

            float freq = k;
            float amp = sqrt(re * re + im * im);
            float phase = atan2(im, re);
            X[k] = new Phasor(amp, freq, phase);
        }

        return X;
    }

    public float[] epiCycles(float x, float y, Phasor[] Phasors, float rotation) {
        float oldx;
        float oldy;
        float[] point = new float[2];
        for (int i = 0; i < Phasors.length; i++) {
            oldx = x;
            oldy = y;

            Phasor Phasor = Phasors[i];
            PVector vec = Phasor.state(time, rotation);

            x += vec.x;
            y += vec.y;

            noFill();
            stroke(52);
            ellipse(oldx, oldy, Phasor.amplitude * 2, Phasor.amplitude * 2);

            fill(255);
            stroke(255);
            line(oldx, oldy, x, y);
        }

        ellipse(x, y, 5, 5);
        point[0] = x;
        point[1] = y;
        return point;
    }

    public void Sort(Phasor[] Phasors) {
        int n = Phasors.length;

        for (int i = 0; i < n - 1; i++) {
            int mindex = i;

            for (int j = i + 1; j < n; j++) {
                if (Phasors[j].amplitude > Phasors[mindex].amplitude)
                    mindex = j;
            }
            swap(Phasors, mindex, i);
        }
    }

    public void swap(Phasor[] Phasors, int i, int j) {
        Phasor temp = Phasors[i];
        Phasors[i] = Phasors[j];
        Phasors[j] = temp;
    }

    public void recordData() {
        if (!mousePressed) return;
        if (pointX[pointX.length - 1] == 0) {
            pointX[pointCount] = mouseX - width / 2;
            pointY[pointCount] = mouseY - height / 2;
            drawOrNot[pointCount] = lastMousePress;
            pointCount++;
        } else
            recording = false;
    }

    public static void main(String[] args) {
        PApplet.main(CollectThenDraw.class.getName());
    }
}
