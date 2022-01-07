class Cell {

  int i, j;
  boolean[] walls = new boolean[]{true, true, true, true};
  boolean visited = false;
  int f = 0, g = 0;
  int heuristic = 0;
  ArrayList<Cell> neighbours = new ArrayList<Cell>();

  Cell previous = null;

  Cell(int i, int j) {
    this.i = i;
    this.j = j;
  }

  void highlight() {
    int x = i * w;
    int y = j * w;
    noStroke();
    fill(0, 0, 255, 100);
    rect(x, y, w, w);
  }

  void show(color c) {
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

    if (this.visited && drawFinish) {
      noStroke();
      fill(c);
      rect(x, y, w, w);
      //rect(x + w / 4, y + w / 4, w / 2, w / 2);
    }
  }

  void addNeighbours() {
    if (!walls[0] && j > 0)
      neighbours.add(grid[index(i, j - 1)]);
    if (!walls[1] && i < cols - 1)
      neighbours.add(grid[index(i + 1, j)]);
    if (!walls[2] && j < rows - 1)
      neighbours.add(grid[index(i, j + 1)]);
    if (!walls[3] && i > 0)
      neighbours.add(grid[index(i - 1, j)]);
  }

  Cell checkNeighbours() {
    ArrayList<Cell> around = new ArrayList<Cell>();
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

  int index(int i, int j) {
    if (i < 0 || j < 0 || i > cols - 1 || j > rows - 1) return -1;
    return i + j * cols;
  }
}
