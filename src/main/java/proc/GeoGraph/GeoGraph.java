package proc.GeoGraph;

import processing.core.PApplet;

public class GeoGraph extends PApplet {

    int cols, rows;
    int scl = 20;
    int w = 3800;
    int h = 2000;

    float flying = 0;

    float[][] terrain;

    boolean initialize = false;

    public void settings() {
        fullScreen(P3D);
    }

    public void draw() {
        if (!initialize) {
            cols = w / scl;
            rows = h / scl;
            terrain = new float[cols][rows];
            initialize = true;
        }
        flying -= 0.1;

        float yOff = flying;
        for (int y = 0; y < rows; y++) {
            float xOff = 0;
            for (int x = 0; x < cols; x++) {
                terrain[x][y] = map(noise(xOff, yOff), 0, 1, -100, 100);
                xOff += 0.2;
            }
            yOff += 0.2;
        }
        background(0);
        stroke(255);
        noFill();

        translate((float) width / 2, (float) height / 2 + 50);
        rotateX(PI / 3);

        translate((float) -w / 2, (float) -h / 2);
        for (int y = 0; y < rows - 1; y++) {
            beginShape(TRIANGLE_STRIP);
            for (int x = 0; x < cols; x++) {
                vertex(x * scl, y * scl, terrain[x][y]);
                vertex(x * scl, (y + 1) * scl, terrain[x][y+1]);
                //rect(x * scl, y * scl, scl, scl);
            }
            endShape();
        }
    }

    public static void main(String[] args) {
        PApplet.main("proc.GeoGraph.GeoGraph");
    }
}
