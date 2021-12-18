package Tutorials.Ep16;

import processing.core.PApplet;

public class version1 extends PApplet {

    float dia = 400;
    float angle;
    float swMax;
    boolean initialize = false;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        if (!initialize) {
            strokeCap(CORNER);
            stroke(255);
            initialize = true;
            //mouseX = 10;
        }
        background(0, 40, 65);

        dia = lerp(dia, mouseX, (float) 0.1);

        translate((float) width / 2, (float) height / 2);
        rotate(-angle);

        for (int a = 0; a < 360; a += 30) {
            pushMatrix();
            rotate(radians(a));
            for (int r = 0; r < 180; r += 8) {
                float sw = map(cos(radians(r)), -1, 1, swMax, 1);
                strokeWeight(sw);
                line(sin(radians(r)) * dia, cos(radians(r))* dia,
                        sin(radians(-r)) * dia, cos(radians(-r))* dia);
            }
            popMatrix();
        }
        angle += TWO_PI / 720;

        float sw_angle = map(dia, 0, width, 0 ,360);
        sw_angle = constrain(sw_angle, 180, 360);
        swMax = map(cos(radians(sw_angle)), -1, 1, 1, 225);
        noFill();
        stroke(255);
        ellipse(0, 0, dia * 2, dia * 2);
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep16.version1");
    }
}
