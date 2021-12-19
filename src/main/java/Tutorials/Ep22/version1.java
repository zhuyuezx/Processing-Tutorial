package Tutorials.Ep22;

import processing.core.PApplet;

public class version1 extends PApplet {

    int diaMin = 3010;
    int diaMax = 3000;
    int diaStep = 50;

    float xx, yy, angle;
    boolean initialize = false;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        if (!initialize) {
            noFill();
            stroke(0);
            strokeWeight((float) diaStep / 4);
            initialize = true;
        }
        background(255);

        xx = sin(radians(angle + 90)) * 450;
        //yy = cos(radians(angle)) * 450;

        translate((float) width / 2, (float) height / 2);
        for (int dia = diaMin; dia < diaMax; dia += diaStep) {
            ellipse(-xx, 0, dia, dia);
            //ellipse((float) width / 2 - mouseX, 0, dia, dia);
            ellipse(xx, 0, dia, dia);
        }
        //angle++;

        if (diaMin > 10) diaMin -= 2;
        if (frameCount > 360) angle += 0.3;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep22.version1");
    }
}
