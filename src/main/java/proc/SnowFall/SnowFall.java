package proc.SnowFall;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class SnowFall extends PApplet {

    ArrayList<SnowFlake> snow;
    PVector gravity;
    int[] accumulation;

    float zOff = 0;
    boolean initialize = false;

    PImage spiritSheet;
    ArrayList<PImage> textures;

    public void settings() {
        fullScreen();
        //size(800, 600);
        snow = new ArrayList<>();
        textures = new ArrayList<>();
    }

    public void draw() {
        if (!initialize) {
            accumulation = new int[width + 1];
            spiritSheet = loadImage(
                    "src\\main\\java\\proc\\SnowFall\\flakes32.png");
            gravity = new PVector(0, (float) 0.3);
            for (int x = 0; x < spiritSheet.width; x += 32) {
                for (int y = 0; y < spiritSheet.height; y += 32) {
                    PImage img = spiritSheet.get(x, y, 32, 32);
                    //image(img, x, y);
                    textures.add(img);
                }
            }
            for (int i = 0; i < 2500; i++) {
                float x = random(width);
                float y = random(height);
                int designIndex = floor(random(textures.size()));
                PImage design = textures.get(designIndex);
                snow.add(new SnowFlake(x, y, design, this));
            }
            initialize = true;
        }

        background(0);

        zOff += 0.1;

        for (SnowFlake flake : snow) {
            float xOff = flake.pos.x / width;
            float yOff = flake.pos.y / height;
            float wAngle = noise(xOff, yOff, zOff) *2 * PI;
            PVector wind = PVector.fromAngle(wAngle);
            wind.mult((float)0.1);

            flake.applyForce(gravity);
            flake.applyForce(wind);
            flake.update();
            flake.render();
        }
    }

    public static void main(String[] args) {
        PApplet.main(SnowFall.class.getName());
    }
}
