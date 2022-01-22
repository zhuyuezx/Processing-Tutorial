package proc.GameOfLife;

import processing.core.PApplet;

public class operator extends PApplet {
    grid game_of_life;
    boolean[][] drawBoard;
    boolean initialize = false;
    int size = 20;
    boolean drawFinish = false;

    public static void main(String[] args) {
        PApplet.main(operator.class.getName());
    }

    public void settings() {
//        size(1920, 1080);
        fullScreen();
    }

    public void draw() {
        if (!initialize) {
            //noStroke();
            stroke(50);
            strokeWeight(2);

            drawBoard = new boolean[height / size][width / size];
            game_of_life = new grid();
            //game_of_life.initialize();
            initialize = true;
        }
        if (!drawFinish) {
            if (mousePressed)
                game_of_life.change(mouseY / size, mouseX / size);
            if (keyPressed && key == ' ')
                drawFinish = true;

            game_of_life.display();
            return;
        }
        game_of_life.update();
        game_of_life.display();
        if (keyPressed && key == ' ')
            drawFinish = false;
    }

    private class grid {
        private boolean[][] cells;
        private boolean[][] next;
        int loc1 = width / size;
        int loc2 = height / size;

        public grid() {
            cells = new boolean[loc2][loc1];
            next = new boolean[loc2][loc1];
        }

        public void initialize() {
            for (int i = 0; i < loc2; i++) {
                for (int j = 0; j < loc1; j++) {
                    if (random(1) < 0.2)
                        cells[i][j] = true;
                }
            }
        }

        public void change(int i, int j) {
            if (mouseButton == LEFT) cells[i][j] = true;
            if (mouseButton == RIGHT) cells[i][j] = false;
        }

        public void update() {
            for (int i = 0; i < loc2; i++) {
                for (int j = 0; j < loc1; j++) {
                    int b_count = 0;
                    b_count += getIndex(i - 1, j - 1) ? 1 : 0;
                    b_count += getIndex(i - 1, j) ? 1 : 0;
                    b_count += getIndex(i - 1, j + 1) ? 1 : 0;
                    b_count += getIndex(i, j - 1) ? 1 : 0;
                    b_count += getIndex(i, j + 1) ? 1 : 0;
                    b_count += getIndex(i + 1, j - 1) ? 1 : 0;
                    b_count += getIndex(i + 1, j) ? 1 : 0;
                    b_count += getIndex(i + 1, j + 1) ? 1 : 0;

                    if (b_count < 2) {
                        assignBool(i, j, false);
                    } else if (b_count == 2) {
                        assignBool(i, j, cells[i][j]);
                    } else assignBool(i, j, b_count == 3);
                }
            }

            for (int i = 0; i < loc2; i++) {
                for (int j = 0; j < loc1; j++) {
                    cells[i][j] = next[i][j];
                    next[i][j] = false;
                }
            }
        }

        public void display() {
            for (int i = 0; i < loc2; i++) {
                for (int j = 0; j < loc1; j++) {
                    if (cells[i][j]) {
                        fill(0);
                    } else {
                        fill(255);
                    }
                    rect(size * j, size * i, size, size);
                }
            }
        }

        public boolean getIndex(int i, int j) {
            return cells[(loc2 + i) % loc2][(loc1 + j) % loc1];
        }

        public void assignBool(int i, int j, boolean assign) {
            next[(loc2 + i) % loc2][(loc1 + j) % loc1] = assign;
        }
    }
}
