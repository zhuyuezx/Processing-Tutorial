int cols, rows;
int w = 20;
Cell[] grid;
Cell start;
Cell end;
boolean drawFinish = false;
boolean findPath = false;
int pathCount = 0;

Cell current;
ArrayList<Cell> stack;

ArrayList<Cell> openSet;
ArrayList<Cell> closedSet;


int heuristic(Cell a, Cell b) {
  return abs(a.i - b.i) + abs(a.j - b.j);
}

void setup() {
  size(800, 800);
  cols = (int)(width / w);
  rows = (int)(height / w);
  grid = new Cell[cols * rows];
  stack = new ArrayList<Cell>();

  for (int j = 0; j < rows; j++) {
    for (int i = 0; i < cols; i++) {
      grid[i + j * rows] = new Cell(i, j);
    }
  }
  current = grid[0];
  start = grid[0];
  end = grid[grid.length - 1];

  openSet = new ArrayList<Cell>();
  closedSet = new ArrayList<Cell>();
  openSet.add(start);
}

void draw() {
  background(20);
  if (!drawFinish) {
    for (int i = 0; i < grid.length; i++) {
      grid[i].show(color(50, 100));
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
        for (Cell c : grid) {
          c.addNeighbours();
        }
      }
    }
  } else {
    if (openSet.size() > 0) {
      int winner = 0;
      for (int i = 0; i < openSet.size(); i++) {
        if (openSet.get(i).f < openSet.get(winner).f) {
          winner = i;
        }
      }
      current = openSet.get(winner);

      if (current == end) {
        findPath = true;
        //noLoop();
        drawWindow();
        return;
      }

      openSet.remove(current);
      closedSet.add(current);

      ArrayList<Cell> neighbours = current.neighbours;
      for (Cell neighbour : neighbours) {
        if (!closedSet.contains(neighbour)) {
          int tempG = current.g + heuristic(neighbour, current);

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
    } else {
      println("no solution");
      noLoop();
      return;
    }
    drawWindow();
  }
}

void drawWindow() {
  background(20);
  for (Cell c : grid) {
    c.show(color(50, 100));
  }
  if (!findPath) {
    for (Cell c : closedSet) {
      c.show(color(255, 0, 0, 150));
    }
    for (Cell c : openSet) {
      c.show(color(0, 255, 0, 150));
    }
  }
  end.show(color(200, 0, 150));

  ArrayList<Cell> path = new ArrayList<Cell>();
  Cell temp = current;
  path.add(temp);
  while (temp.previous != null) {
    path.add(temp.previous);
    temp = temp.previous;
  }

  if (findPath) {
    stroke(200, 0, 150);
    strokeWeight(w / 2);
    Cell track = current.previous;
    line(current.i * w + w / 2, current.j * w + w / 2, 
      track.i * w + w / 2, track.j * w + w / 2);
    for (int n = 0; n < pathCount; n++) {
      if (track.previous != null) {
        line(track.i * w + w / 2, track.j * w + w / 2, 
          track.previous.i * w + w / 2, track.previous.j * w + w / 2);
        track = track.previous;
      }
    }
    pathCount++;
    strokeWeight(2);
    return;
  }

  noFill();
  stroke(255, 255, 0, 200);
  strokeWeight(w / 2);
  beginShape();
  for (int i = 0; i < path.size(); i++) {
    vertex(path.get(i).i * w + w / 2, path.get(i).j * w + w / 2);
  }
  endShape();
  strokeWeight(2);
}

void removeWalls(Cell a, Cell b) {
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
