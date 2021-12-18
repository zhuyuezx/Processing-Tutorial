package Tutorials.Ep9;

import processing.core.PApplet;

public class version2 extends PApplet {

    int grid = 400;
    float angle;
    int mx = 80, my = 135;
    float scaleVar = 1;
    boolean initialize = false;

    public void settings() {
        size(1920, 1080, P3D);
    }

    public void draw() {
        if (!initialize) {
            rectMode(CENTER);
            fill(255, 0, 0);
            noStroke();

            mx = (width - floor((float) width / (grid * 2)) * grid * 2) / 2;
            //my = (height - floor((float) width / (grid * 2)) * grid * 2) / 2;
            my = (height - floor((float) height / (grid * 2)) * grid * 2) / 2;

            initialize = true;
        }
        background(15, 20, 30);

        translate((float) width / 2, (float) height / 2);
        scaleVar = lerp(scaleVar, map(mouseX, 0, width, (float) 0.1, 5), (float) 0.1);
        scale(scaleVar);
        for (int i = mx + grid - width / 2; i < width / 2 - mx; i += grid * 2) {
            for (int j = my + grid - height / 2; j < height / 2 - my; j += grid * 2) {
                pushMatrix();
                translate(i, j);

                // top left
                pushMatrix();
                translate((float) -grid / 2, (float) -grid / 2);
                rotateX(radians(angle));
                rotateY(-radians(angle));
                rect(0, 0, grid, grid);
                popMatrix();

                // top right
                pushMatrix();
                translate((float) grid / 2, (float) -grid / 2);
                rotateY(radians(angle));
                rotateX(-radians(angle));
                rect(0, 0, grid, grid);
                popMatrix();

                // bottom left
                pushMatrix();
                translate((float) -grid / 2, (float) grid / 2);
                rotateY(radians(angle));
                rotateX(radians(angle));
                rect(0, 0, grid, grid);
                popMatrix();

                // bottom right
                pushMatrix();
                translate((float) grid / 2, (float) grid / 2);
                rotateX(-radians(angle));
                rotateY(-radians(angle));
                rect(0, 0, grid, grid);
                popMatrix();

                popMatrix();
            }
        }
        angle++;

        if (angle >= 180) {
            grid /= 2;
            if (grid <= 10) {
                grid = 400;
            }
            angle = 0;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep9.version2");
    }
}
