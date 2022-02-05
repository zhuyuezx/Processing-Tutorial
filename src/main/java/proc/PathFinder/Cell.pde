class Cell {

  int i, j;
  boolean[] walls = new boolean[]{true, true, true, true};
  ArrayList<Cell> neighbours;
  boolean visited = false;
  float f = 0, g = 0;
  int dist = rows * cols + 1;
  float heuristic = 0;

  Cell previous = null;

  Cell(int i, int j) {
    this.i = i;
    this.j = j;
  }

  void highlight() {
    noStroke();
    fill(200, 0, 200);
    rect(i * w, j * w, w, w);
  }

  void show(color c) {
    int x = i * w;
    int y = j * w;
    stroke(255);
    if (walls[0]) line(x, y, x + w, y);
    if (walls[1]) line(x + w, y, x + w, y + w);
    if (walls[2]) line(x + w, y + w, x, y + w);
    if (walls[3]) line(x, y + w, x, y);

    noStroke();
    fill(c);
    rect(x, y, w, w);
  }

  void addNeighbours() {
    neighbours = new ArrayList<Cell>();
    if (!walls[0] && j > 0) 
      neighbours.add(grid[index(i, j - 1)]);
    if (!walls[3] && i > 0) 
      neighbours.add(grid[index(i - 1, j)]);
    if (!walls[2] && j < rows - 1) 
      neighbours.add(grid[index(i, j + 1)]);
    if (!walls[1] && i < cols - 1) 
      neighbours.add(grid[index(i + 1, j)]);
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
    }
    return null;
  }

  int index(int i, int j) {
    if (i < 0 || j < 0 || i > cols - 1 || j > rows - 1) return -1;
    return i + j * cols;
  }

  void backTrack() {
    ArrayList<Cell> path = new ArrayList<Cell>();
    Cell temp = this;
    while (temp != null) {
      path.add(temp);
      temp = temp.previous;
    }

    if (findPath) {
      boolean endTrack = false;

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
        } else {
          endTrack = true;
        }
      }
      pathCount+=2;
      strokeWeight(2);

      if (endTrack) {
        delay(2000);
        reset();
      }
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
}
