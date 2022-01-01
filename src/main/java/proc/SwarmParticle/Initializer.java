package proc.SwarmParticle;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Initializer extends PApplet {

    ArrayList<Borg> borgs;

    public void settings() {
        fullScreen();
        size(1920, 1080);

        borgs = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            borgs.add(new Borg(random(width), random(height), this));
        }
    }

    public void draw() {
        fill(200, 1);
        rect(0, 0, width, height);
        checkCollisions();
        for (Borg b : borgs) {
            b.update();
            b.display();
        }
    }

    public void checkCollisions() {
        ArrayList<Borg> toDie = new ArrayList<>();
        ArrayList<Borg> toCreate = new ArrayList<>();

        for (int a = 0; a < borgs.size(); a++) {
            Borg p = borgs.get(a);
            for (int b = a + 1; b < borgs.size(); b++) {
                Borg q = borgs.get(b);
                PVector pq = new PVector(q.x - p.x, q.y - p.y);
                if (pq.mag() < 50) {
                    line(p.x, p.y, q.x, q.y);

                    float sim = p.vx * q.vx + p.vy * q.vy;
                    if (sim > 1) {
                        toCreate.add(new Borg
                                ((float) ((p.x + q.y) / 2.0), (float) ((p.y + q.y) / 2.0), this));
                    }
                    if (sim < -0.5) {
                        toDie.add(p);
                    }
                }
            }
        }
        borgs.removeAll(toDie);
        borgs.addAll(toCreate);
    }


    public static void main(String[] args) {
        PApplet.main("proc.SwarmParticle.Initializer");
    }
}
