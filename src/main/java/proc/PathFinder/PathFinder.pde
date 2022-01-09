int cols, rows;
int w = 20;
Cell[] grid;
Cell start;
Cell end;
boolean drawFinish = false;
boolean findPath = false;
int pathCount = 0;
int generateCount = 0;
int methodCount = 0;

Cell current;
ArrayList<Cell> stack;

// A*
ArrayList<Cell> openSet;
ArrayList<Cell> closedSet;

// Best First
ArrayList<Cell> route;


int heuristic(Cell a, Cell b) {
  return abs(a.i - b.i) + abs(a.j - b.j);
}

void setup() {
  //fullScreen();
  size(1000, 1000);
  cols = (int)(width / w);
  rows = (int)(height / w);
  grid = new Cell[cols * rows];

  stack = new ArrayList<Cell>();

  for (int j = 0; j < rows; j++) {
    for (int i = 0; i < cols; i++) {
      grid[i + j * cols] = new Cell(i, j);
    }
  }
  current = grid[0];
  start = grid[0];
  end = grid[grid.length - 1];

  openSet = new ArrayList<Cell>();
  closedSet = new ArrayList<Cell>();
  openSet.add(start);

  route = new ArrayList<Cell>();
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
      GreedyFirst();
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
  findPath = false;
  frameCount = 0;
  pathCount = 0;
  methodCount++;
  if (methodCount == 5) {
    generateCount++;
    drawFinish = false;
    for (Cell c : grid) {
      for (int wall = 0; wall < 4; wall++) 
        c.walls[wall] = true;
    }
  }
  //generateCount %= 2;
  methodCount %= 5;

  current = grid[0];
  start = grid[0];
  end = grid[grid.length - 1];

  openSet = new ArrayList<Cell>();
  closedSet = new ArrayList<Cell>();
  route = new ArrayList<Cell>();
  openSet.add(start);
  route.add(start);

  if (methodCount == 4) {
    for (Cell c : grid) {
      c.visited = false;
      route.add(c);
    }
    route.get(0).dist = 0;
    return;
  }

  for (Cell c : grid) {
    c.visited = false;
  }
  start.visited = true;
}
