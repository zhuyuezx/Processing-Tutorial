package proc.JuliaSet;

import processing.core.PApplet;

import java.awt.*;

public class Mandelbrot2 extends PApplet {

    int maxIter = 128;
    double minRe = -2.5, maxRe = 1;
    double minIm = -1, maxIm = 1;
    boolean pressedLeft = false;
    boolean pressedRight = false;
    long zoom = (long)1.0;

    Color[] colors;

    public void settings() {
        fullScreen();
        //size(960, 540);
        colors = new Color[]{
                new Color(0, 7, 100),
                new Color(32, 107, 203),
                new Color(237, 255, 255),
                new Color(255, 170, 0),
                new Color(0, 2, 0)};
    }

    public void draw() {
        if (mousePressed && mouseButton == LEFT && !pressedLeft) {
            pressedLeft = true;
            zoomX(5);
            zoom *= 5;
        } else if (mousePressed && mouseButton == RIGHT && !pressedRight) {
            pressedRight = true;
            zoomX(1.0 / 5);
            zoom /= 5;
        }

        loadPixels();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double cr = minRe + (maxRe - minRe) * x / width;
                double ci = minIm + (maxIm - minIm) * y / height;
                double re = 0, im = 0;
                int iter;
                for (iter = 0; iter < maxIter; iter++) {
                    double tr = re * re - im * im + cr;
                    im = 2 * re * im + ci;
                    re = tr;
                    if (re * re + im * im > 2 * 2) break;
                }

                int maxColor = colors.length - 1;
                //if (iter == maxIter) iter = 0;
                double mu = 1.0 * iter / maxIter;
                mu *= maxColor;
                int iMu = (int)mu;
                Color color1 = colors[iMu];
                Color color2 = colors[min(iMu + 1, maxColor)];
                double[] c = linearInterpolation(color1, color2, mu - iMu);
                pixels[x + y * width] = color((float) c[0], (float) c[1], (float) c[2]);
            }
        }
        updatePixels();
        textSize(20);
        String msg1 = "max iter: " + maxIter;
        String msg2 = "zoom: " + zoom;
        fill(238);
        text(msg1, 40, 40, 280, 40);
        text(msg2, 40, 80, 280, 80);
        //println();
        //print(red(pixels[mouseX + mouseY * width]) + " ");
        //print(green(pixels[mouseX + mouseY * width]) + " ");
        //print(blue(pixels[mouseX + mouseY * width]) + " ");
    }

    public double[] linearInterpolation(Color c1, Color c2, double a) {
        //println(a);
        double newR = c1.getRed() + a * (c2.getRed() - c1.getRed());
        double newG = c1.getGreen() + a * (c2.getGreen() - c1.getGreen());
        double newB = c1.getBlue() + a * (c2.getBlue() - c1.getBlue());
        return new double[]{newR, newG, newB};
    }

    public void mouseReleased() {
        if (pressedLeft) {
            pressedLeft = false;
        } else if (pressedRight) {
            pressedRight = false;
        }
    }

    public void keyPressed() {
        maxIter = constrain(maxIter, 1, 1024);
        double w = (maxRe - minRe) * 0.3;
        double h = (maxIm - minIm) * 0.3;
        if (keyCode == LEFT) {
            minRe -= w;
            maxRe -= w;
        } else if (keyCode == RIGHT) {
            minRe += w;
            maxRe += w;
        } else if (keyCode == UP) {
            minIm -= h;
            maxIm -= h;
        } else if (keyCode == DOWN) {
            minIm += h;
            maxIm += h;
        } else if (keyCode == SHIFT) {
            maxIter *= 2;
        } else if (keyCode == CONTROL) {
            maxIter /= 2;
        }
    }

    public void zoomX(double z) {
        // set new center point at mouse point
        double cr = minRe + (maxRe - minRe) * mouseX / width;
        double ci = minIm + (maxIm - minIm) * mouseY / height;
        // zoom
        double tminr = cr - (maxRe - minRe) / 2 / z;
        maxRe = cr + (maxRe - minRe) / 2 / z;
        minRe = tminr;

        double tmini = ci - (maxIm - minIm) / 2 / z;
        maxIm = ci + (maxIm - minIm) / 2 / z;
        minIm = tmini;
    }


    public static void main(String[] args) {
        PApplet.main(Mandelbrot2.class.getName());
    }
}
