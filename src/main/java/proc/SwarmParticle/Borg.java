package proc.SwarmParticle;

public class Borg {

    float x,y;
    float vx, vy;
    Initializer operator;

    public Borg(float x, float y, Initializer operator) {
        this.x = x;
        this.y = y;
        this.vx = operator.random(-1, 1);
        this.vy = operator.random(-1, 1);
        this.operator = operator;
    }

    public void update() {
        vx += operator.random((float) -0.01, (float) 0.01);
        vy += operator.random((float) -0.01, (float) 0.01);

        x += vx;
        y += vy;
        x = (x + operator.width) % operator.width;
        y = (y + operator.height) % operator.height;
    }

    public void display() {
        //operator.fill(255);
        //operator.ellipse(x, y, 10, 10);
    }
}
