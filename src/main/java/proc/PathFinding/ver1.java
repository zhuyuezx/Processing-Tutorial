package proc.PathFinding;

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class ver1 extends PApplet {
    int cols, rows;
    int w = 20;
    Cell[] grid;
    boolean drawFinish = false;

    Cell current;
    Cell start;
    Cell end;

    ArrayList<Cell> stack;
    ArrayList<Cell> openSet = new ArrayList<>();
    ArrayList<Cell> closedSet = new ArrayList<>();

    ArrayList<Cell> path = new ArrayList<>();

    public int heuristic(Cell a, Cell b) {
        return abs(a.i - b.i) + abs(a.j - b.j);
    }

    public void settings() {
        size(800, 800);
        cols = width / w;
        rows = height / w;
        grid = new Cell[cols * rows];
        stack = new ArrayList<>();

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                grid[i + j * rows] = new Cell(i, j);
            }
        }

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                grid[i + j * rows].addNeighbours();
            }
        }
        current = grid[0];

        start = grid[0];
        end = grid[grid.length - 1];

        openSet.add(start);
    }

    public void draw() {
        background(20);

        if (! drawFinish) {
            for (Cell cell : grid) {
                cell.show(new Color(50, 50, 50, 100));
            }

            for (int i = 0; i < 10; i++) {
                current.visited = true;
                current.highlight();
                Cell next = current.checkNeighbours();
                if (next != null) {
                    next.visited = true;
                    stack.add(current);

                    removeWalls(current, next);

                    current = next;
                } else if (stack.size() > 0) {
                    current = stack.remove(stack.size() - 1);
                }
                if (frameCount > 10 && stack.size() == 0) {
                    drawFinish = true;
                }
            }
        } else {
            //println(openSet.size());
            if (openSet.size() == 0) {
                println("no solution");
                noLoop();
                return;
            }
            int winner = 0;
            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).f < openSet.get(winner).f) {
                    winner = i;
                }
            }
            current = openSet.get(winner);

            if (current == end) {
                noLoop();
                println("DONE");
            }

            openSet.remove(current);
            closedSet.add(current);

            ArrayList<Cell> neighbours = current.neighbours;
            for (Cell neighbour : neighbours) {
                if (!closedSet.contains(neighbour)) {
                    float tempG = current.g + heuristic(neighbour, current);

                    boolean newPath = false;
                    if (openSet.contains(neighbour)) {
                        if (tempG < neighbour.g) {
                            neighbour.g = tempG;
                            newPath = true;
                        }
                    } else {
                        neighbour.g = tempG;
                        newPath = true;
                        openSet.add(neighbour);
                    }

                    if (newPath) {
                        neighbour.heuristic = heuristic(neighbour, end);
                        neighbour.f = neighbour.g + neighbour.heuristic;
                        neighbour.previous = current;
                    }
                }
            }
            background(20);
            for (Cell cell : grid) {
                cell.show(new Color(50, 50, 50, 100));
            }
            for (Cell cell : closedSet) {
                cell.show(new Color(255, 0, 0, 100));
            }
            for (Cell cell : openSet) {
                cell.show(new Color(0, 255, 0, 100));
            }

            ArrayList<Cell> path = new ArrayList<>();
            Cell temp = current;
            path.add(temp);
            while (temp.previous != null) {
                path.add(temp.previous);
                temp = temp.previous;
            }

            noFill();
            stroke(255, 0, 200);
            strokeWeight((float) w / 2);
            beginShape();
            for (Cell cell : path) {
                vertex(cell.i * w + (float) w / 2, cell.j * w + (float) w / 2);
            }
            endShape();
        }
    }

    public void insideFill(Color c) {
        fill(c.getRed(), c.getBlue(), c.getGreen());
    }

    public void removeWalls(Cell a, Cell b) {
        int x = a.i - b.i;
        if (x == 1) {
            a.walls[3] = false;
            b.walls[1] = false;
        } else if (x == -1) {
            a.walls[1] = false;
            b.walls[3] = false;
        }
        int y = a.j - b.j;
        if (y == 1) {
            a.walls[0] = false;
            b.walls[2] = false;
        } else if (y == -1) {
            a.walls[2] = false;
            b.walls[0] = false;
        }
    }

    public class Cell {
        int i, j;
        boolean[] walls = new boolean[]{true, true, true, true};
        boolean visited = false;
        float f = 0, g = 0;
        float heuristic = 0;
        ArrayList<Cell> neighbours = new ArrayList<>();

        Cell previous = null;

        public Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void highlight() {
            int x = i * w;
            int y = j * w;
            noStroke();
            fill(0, 0, 255, 100);
            rect(x, y, w, w);
        }

        public void show(Color c) {
            int x = i * w;
            int y = j * w;
            stroke(255);
            if (this.walls[0])
                line(x, y, x + w, y);
            if (this.walls[1])
                line(x + w, y, x + w, y + w);
            if (this.walls[2])
                line(x + w, y + w, x, y + w);
            if (this.walls[3])
                line(x, y + w, x, y);
            insideFill(c);
            if (this.visited) {
                noStroke();
                rect(x, y, w - 1, w - 1);
            }
        }

        public void addNeighbours() {
            if (!walls[0] && j > 0)
                neighbours.add(grid[index(i, j - 1)]);
            if (!walls[1] && i < cols - 1)
                neighbours.add(grid[index(i + 1, j)]);
            if (!walls[2] && j < rows - 1)
                neighbours.add(grid[index(i, j + 1)]);
            if (!walls[3] && i > 0)
                neighbours.add(grid[index(i - 1, j)]);
        }

        public Cell checkNeighbours() {
            ArrayList<Cell> around = new ArrayList<>();
            int top = index(i, j - 1);
            int right = index(i + 1, j);
            int bottom = index(i, j + 1);
            int left = index(i - 1, j);
            if (top != -1 && !grid[top].visited)
                around.add(grid[top]);
            if (right != -1 && !grid[right].visited)
                around.add(grid[right]);
            if (bottom != -1 && !grid[bottom].visited)
                around.add(grid[bottom]);
            if (left != -1 && !grid[left].visited)
                around.add(grid[left]);

            if (around.size() > 0) {
                int r = (int)random(around.size());
                return around.get(r);
            } else {
                return null;
            }
        }

        public int index(int i, int j) {
            if (i < 0 || j < 0 || i > cols - 1 || j > rows - 1) return -1;
            return i + j * cols;
        }
    }

    public static void main(String[] args) {
        PApplet.main(ver1.class.getName());
    }

}
