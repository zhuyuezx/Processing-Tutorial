package proc.JuliaSet;

import processing.core.PApplet;

public class JuliaSet extends PApplet {

    float angle = 0;
    public boolean initialize = false;

    public void settings() {
        size(960, 640);
    }

    public void draw() {
        if (!initialize) {
            colorMode(HSB, 1);
            initialize = true;
        }

        float ca = (float) -0.8;
        float cb = (float) 0.156;
//        float ca = cos((float) (angle * 3.213));
//        float cb = sin(angle);
        angle += 0.02;

        background(255);

        float w = (float) (2 * cos(radians(frameCount) / 5) + 2.0001);
        float h = (w * height) / width;

        float xMin = (float) (- w / 2 - 0.445);
        float yMin = - h / 2;

        loadPixels();

        int maxIterations = 100;

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
                    a = aa - bb + ca;
                    b = twoab + cb;
                    if (a * a + b * b > 32) break;
                    n++;
                }

                if (n == maxIterations) {
                    pixels[i + j * width] = color(0);
                } else {
                    float hu = sqrt((float) n / maxIterations);
                    pixels[i + j * width] = color(hu, 255, 255);
                }
                x+= dx;
            }
            y += dy;
        }
        updatePixels();
        System.out.println(frameRate);
    }

    public static void main(String[] args) {
        PApplet.main(JuliaSet.class.getName());
    }
}
