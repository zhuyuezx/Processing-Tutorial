package proc.FlowField;

import processing.core.PVector;

import static java.lang.Math.floor;

public class FlowField {

    PVector[] vectors;
    int cols, rows;
    float inc = (float) 0.1;
    float zoff = 0;
    int scl;

    Coordinator operator;

    public FlowField(int res, Coordinator operator) {
        scl = res;
        cols = operator.width / res + 1;
        rows = operator.height / res + 1;
        vectors = new PVector[cols * rows];
        this.operator = operator;
    }

    void update() {
        float yoff = 0;
        for (int y = 0; y < rows; y++) {
            float xoff = 0;
            for (int x = 0; x < cols; x++) {
                float angle = operator.noise(xoff, yoff, zoff) * operator.TWO_PI * 2;

                PVector v = PVector.fromAngle(angle);
                v.setMag(1);
                int index = x + y * cols;
                vectors[index] = v;

                xoff += inc;
            }
            yoff += inc;
        }
        zoff += 0.004;
    }

    void display() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int index = x + y * cols;
                PVector v = vectors[index];

                operator.stroke(0, 0, 0, 40);
                operator.strokeWeight((float) 0.1);
                operator.pushMatrix();
                operator.translate(x * scl, y * scl);
                operator.rotate(v.heading());
                operator.line(0, 0, scl, 0);
                operator.popMatrix();
            }
        }
    }
}
