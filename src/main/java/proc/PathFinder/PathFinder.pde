// Step1: create the basic grid
// Step2: try implementing the maze generating algorithm
// Step3: create stack to aviod dead end
// Step4: first implement a basic search algorithm
// Step5: show path and backtrack
// Step6: implement more algorithms and apply them to same maze
// Step7: implement another random generation for comparison
// Step8: enable recording

int rows, cols;
int w = 20;
Cell current;
Cell[] grid;
int pathCount = 0;
int methodCount = 0;
int generateCount = 0;

ArrayList<Cell> route;
ArrayList<Cell> closedSet;
Cell start, end;

boolean drawFinish = false;
boolean findPath = false;

ArrayList<Cell> stack;

int heuristic(Cell a, Cell b) {
  return abs(a.i - b.i) + abs(a.j - b.j);
}

void setup() {
  size(1200, 1000);
  cols = (int)(width / w);
  rows = (int)(height / w);
  grid = new Cell[rows * cols];

  for (int j = 0; j < rows; j++) {
    for (int i = 0; i < cols; i++) {
      grid[i + j * cols] = new Cell(i, j);
    }
  }
  stack = new ArrayList<Cell>();
  current = grid[0];
  route = new ArrayList<Cell>();
  closedSet = new ArrayList<Cell>();
  start = grid[0];
  int endIndex = start.index(
    (int)random(cols - 5, cols), 
    (int)random(rows - 5, rows)
    );
  end = grid[endIndex];
  route.add(start);
}

void draw() {
  push();
  background(20);
  strokeWeight(2);
  if (!drawFinish) {
    if (generateCount == 0) {
      generateMaze();
    } else if (generateCount == 1) {
      randomGenerate();
    } else
      noLoop();
  } else {
    if (methodCount == 0)
      AStar();
    else if (methodCount == 1)
      BestFirst();
    else if (methodCount == 2)
      BFS();
    else if (methodCount == 3)
      DFS();
    else if (methodCount == 4)
      Dijkstra();
    else
      noLoop();
  }
  pop();
  rec();
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

void reset() {
  route = new ArrayList<Cell>();
  closedSet = new ArrayList<Cell>();
  pathCount = 0;
  frameCount = 0;
  findPath = false;
  start = grid[0];
  current = grid[0];
  route.add(start);

  methodCount++;
  if (methodCount == 5) {
    generateCount++;
    drawFinish = false;
    for (Cell c : grid) {
      for (int wall = 0; wall < 4; wall++) 
        c.walls[wall] = true;
    }
    int endIndex = start.index(
    (int)random(cols - 5, cols), 
    (int)random(rows - 5, rows)
    );
  end = grid[endIndex];
  }
  methodCount %= 5;

  if (methodCount == 4) {
    for (Cell c : grid) {
      c.visited = false;
      route.add(c);
    }
    route.get(0).dist = 0;
    return;
  }

  for (Cell c : grid)
    c.visited = false;

  start.visited = true;
}
