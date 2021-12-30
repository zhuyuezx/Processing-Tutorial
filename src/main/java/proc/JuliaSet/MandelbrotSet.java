package proc.JuliaSet;

import processing.core.PApplet;

public class MandelbrotSet extends PApplet {

    public boolean initialize = false;

    public void settings() {
        fullScreen();
        noLoop();
    }

    public void draw() {
        if (!initialize) {
            colorMode(RGB, 1);
            initialize = true;
        }
        background(255);

        float w = 4;
        float h = (w * height) / width;

        float xMin = - w / 2;
        float yMin = - h / 2;

        loadPixels();

        int maxIterations = 256;

        float xMax = xMin + w;
        float yMax = yMin + h;

        float dx = (xMax - xMin) / width;
        float dy = (yMax - yMin) / height;

        float y = yMin;
        for (int j = 0; j < height; j++) {
            float x = xMin;
            for (int i = 0; i < width; i++) {
                float a = x;
                float b = y;
                int n = 0;
                while (n < maxIterations) {
                    float aa = a * a;
                    float bb = b * b;
                    float twoab = 2 * a * b;
                    a = aa - bb + x;
                    b = twoab + y;
                    if (a * a + b * b > 32) break;
                    n++;
                }

                if (n == maxIterations) {
                    pixels[i + j * width] = color(0);
                } else {
                    pixels[i + j * width] = color(sqrt((float)n / maxIterations));
                }
                x+= dx;
            }
            y += dy;
        }
        updatePixels();
        System.out.println(frameRate);
    }

    public static void main(String[] args) {
        PApplet.main(MandelbrotSet.class.getName());
    }

}

