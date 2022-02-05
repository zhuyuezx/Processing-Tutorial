void randomGenerate() {
  removeWalls(grid[0], grid[1]);
  removeWalls(grid[grid.length - 1], grid[grid.length - 2]);

  for (int n = 0; n < 10; n++) {
    Cell current = grid[frameCount * 10 - 10 + n];
    int i = current.i, j = current.j;
    ArrayList<Cell> around = new ArrayList<Cell>();
    int top = current.index(i, j - 1);
    int right = current.index(i + 1, j);
    int bottom = current.index(i, j + 1);
    int left = current.index(i - 1, j);
    if (top != -1) 
      around.add(grid[top]);
    if (right != -1) 
      around.add(grid[right]);
    if (bottom != -1) 
      around.add(grid[bottom]);
    if (left != -1) 
      around.add(grid[left]);
    for (Cell neighbour : around) {
      float r = random(1);
      if (r < 0.5)
        removeWalls(current, neighbour);
    }
  }

  for (Cell c : grid) 
    c.show(color(20, 100));

  if (frameCount > grid.length / 10 - 1) {
    drawFinish = true;
    for (Cell c : grid) {
      c.addNeighbours();
      c.visited = false;
    }
    start.visited = true;
  }
}
