package proc.FourierDrawing;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;

import java.util.ArrayList;

public class FourierDrawing extends PApplet {

    Phasor[] fourierX;
    Phasor[] fourierY;
    ArrayList<PVector> path = new ArrayList<>();
    float[] trainX;
    float[] trainY;
    float dt;
    float time = 0;
    int skip = 5;

    public void settings() {
        size(1000, 800);
        loadTrain();
        fourierX = dft(trainX);
        fourierY = dft(trainY);

        Sort(fourierX);
        Sort(fourierY);

        dt = TWO_PI / fourierX.length;
    }

    public void draw() {
        float[] vx;
        float[] vy;
        background(0);
        time += dt;
        if (time > TWO_PI){
            time = 0;
            path = new ArrayList<>();
        }

        vx = epiCycles(width/2,100, fourierX, 0);
        vy = epiCycles(100,height/2, fourierY, HALF_PI);

        line(vx[0], 100, vx[0], vy[1]);
        line(100, vy[1], vx[0], vy[1]);

        ellipse(vx[0], vy[1], 5, 5);
        path.add(new PVector(vx[0], vy[1]));

        noFill();
        beginShape();
        for (PVector vertex : path) {
            vertex(vertex.x, vertex.y);
        }
        endShape();
    }

    public Phasor[] dft (float[] x) { // discrete Fourier Transform
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

    public float[] epiCycles(float x, float y, Phasor[] Phasors, float rotation){
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

        ellipse(x,y,5, 5);
        point[0] = x;
        point[1] = y;
        return point;
    }

    public void Sort(Phasor[] Phasors) {
        int n = Phasors.length;

        for (int i = 0; i < n-1; i++)
        {
            int mindex = i;

            for (int j = i+1; j < n; j++)
            {
                if (Phasors[j].amplitude > Phasors[mindex].amplitude)
                    mindex = j;
            }
            swap(Phasors, mindex, i);
        }
    }



    public void swap(Phasor[] Phasors, int i, int j)
    {
        Phasor temp = Phasors[i];
        Phasors[i] = Phasors[j];
        Phasors[j] = temp;
    }

    void loadTrain() {
        JSONArray train = loadJSONObject("src\\main\\java\\proc\\FourierDrawing\\train.json").getJSONArray("drawing");
        trainX = new float[train.size()/skip];
        trainY = new float[train.size()/skip];

        for (int i = 0; i < train.size()/skip; i+= 1) {
            trainX[i] = train.getJSONObject(i*skip).getFloat("x");
            trainY[i] = train.getJSONObject(i*skip).getFloat("y");
        }
    }

    public static void main(String[] args) {
        PApplet.main(FourierDrawing.class.getName());
    }
}
