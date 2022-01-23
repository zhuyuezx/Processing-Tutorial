color[] colArray = {
            color(255, 255, 255),
            color(200, 5, 20),
            color(55, 188, 25),
            color(15, 25, 250),
            color(125, 235, 250),
            color(240, 245, 15),
            color(160, 60, 235)
    };
    int grid = 100;
    int margin = 100;
    int squareCount = 2 * margin;

    void setup() {
        size(960, 720);
        //noLoop();
    }

    public void draw() {
        background(15, 20, 30);
        
        push();
        noFill();
        stroke(255);

        float size = (float)(grid * 0.6);

        for(int i = 2 *margin; i < width - margin && i <= squareCount; i += grid) {
            for (int j = 2 * margin; j < height - margin && j <= squareCount; j += grid) {
                int colArrayNum = (int)random(7);
                stroke(colArray[colArrayNum]);
                strokeWeight(3);
                generateBlock(7, size, i, j);
            }
        }
        pop();
        rec();
        delay((int)random(200, 500));
        squareCount += grid;
        squareCount = constrain(squareCount, 0, width);
    }

    public void generateBlock(int overLay, float size, int i, int j) {
        for(int num = 0; num < overLay; num++) {
            float x1 = -random(size);
            float y1 = -random(size);
            float x2 = random(size);
            float y2 = -random(size);
            float x3 = random(size);
            float y3 = random(size);
            float x4 = -random(size);
            float y4 = random(size);

            strokeWeight(3);
            pushMatrix();
            translate(i, j);
            quad(x1,y1,x2, y2, x3, y3, x4, y4);
            popMatrix();
        }
    }

    public void keyPressed() {
        if (key == ' ') {
            redraw();
        }
    }
