package Tutorials.Ep3;

import processing.core.PApplet;

import java.awt.*;

public class version1 extends PApplet {
    Color[] colArray = {
            new Color(255, 255, 255),
            new Color(200, 5, 20),
            new Color(55, 188, 25),
            new Color(15, 25, 250),
            new Color(125, 235, 250),
            new Color(240, 245, 15),
            new Color(160, 60, 235)
    };
    int grid = 100;
    int margin = 100;

    public void settings() {
        size(800, 800);
        //noLoop();
    }

    public void draw() {
        background(15, 20, 30);

        noFill();
        stroke(255);

        float size = (float)(grid * 0.6);

        for(int i = 2 *margin; i < width - margin; i += grid) {
            for (int j = 2 * margin; j < height - margin; j += grid) {
                int colArrayNum = (int)random(7);
                stroke(colArray[colArrayNum].getRGB());
                strokeWeight(3);
                generateBlock(7, size, i, j);
            }
        }
        delay((int)random(200, 400));
    }

    public void generateBlock(int overLay, float size, int i, int j) {
        for(int num = 0; num < overLay; num++) {
            float x1 = -random(size);
            float y1 = -random(size);
            float x2 = random(size);
            float y2 = -random(size);
            float x3 = random(size);
            float y3 = random(size);
            float x4 = -random(size);
            float y4 = random(size);

            strokeWeight(3);
            pushMatrix();
            translate(i, j);
            quad(x1,y1,x2, y2, x3, y3, x4, y4);
            popMatrix();
        }
    }

    public void keyPressed() {
        if (key == ' ') {
            redraw();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep3.version1");
    }
}
