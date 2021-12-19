package Tutorials.Ep30;

import processing.core.PApplet;

public class version1 extends PApplet {

    int xStep = 180;
    int yStep = 180;
    int xMargin, yMargin;
    float alpha;
    boolean initialize;

    public void settings() {
        fullScreen(P2D);
    }

    public void draw() {
        if (!initialize) {
            xMargin = (width - ((width / xStep) * xStep)) / 2;
            yMargin = (height - ((height / yStep) * yStep)) / 2;

            if (xMargin == 0) xMargin = xStep;
            if (yMargin == 0) yMargin = yStep;
            initialize = true;
        }
        background(238);

        float glowRadiusAlpha = sin(radians(alpha));
        float glowRadius = (float) xStep / 2 * glowRadiusAlpha;

        for (int x = xMargin; x < width; x += xStep) {
            for (float g = 0; g < glowRadius; g++) {
                stroke(0, 255 * (1 - (g / glowRadius)));
                line(x + g, 0, x + g, height);
                line(x - g, 0, x - g, height);
            }
        }
        glowRadiusAlpha = cos(radians(alpha + 90));
        glowRadius = (float) xStep / 2 * glowRadiusAlpha;
        for (int y = yMargin; y < height; y += yStep) {
            for (float g = 0; g < glowRadius; g++) {
                stroke(0, 255 * (1 - (g / glowRadius)));
                line(0, y + g, width, y + g);
                line(0, y - g, width, y - g);
            }
        }
        alpha += 4;
    }

    public static void main(String[] args) {
        PApplet.main("Tutorials.Ep30.version1");
    }
}
