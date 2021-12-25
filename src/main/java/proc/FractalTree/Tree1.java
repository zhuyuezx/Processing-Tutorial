package proc.FractalTree;

import processing.core.PApplet;

public class Tree1 extends PApplet {

    float angle = PI / 4;
    float branch_ratio = (float) 0.67;
    int count = 0;
    float scale = 2;

    public void settings() {
        fullScreen();
    }

    public void draw() {
        background(51);
        stroke(255);
        translate((float) width / 2, height);
//        translate(0, (float) -width / 10);
//        line(0, 0, 0, (float) width / 10);

        count++;
        angle = PI / 4 + sin(radians((float) count / 2)) * PI / 6;
        branch_ratio = (float) (0.67 - cos(radians((float) count / 3)) * 0.1);
        scale = 2 + sin(radians(count));
        branch(100);
    }

    public void branch(float len) {
        line(0, 0, 0, -len * scale);
        translate(0, -len * scale);
        if (len > 4) {
            pushMatrix();
            rotate(angle);
            branch(branch_ratio * len);
            popMatrix();
            pushMatrix();
            rotate(-angle);
            branch(branch_ratio * len);
            popMatrix();
        }
    }

    public static void main(String[] args) {
        PApplet.main(Tree1.class.getName());
    }
}
