void generateMaze() {
  for (int i = 0; i < grid.length; i++) {
    grid[i].show(color(50, 100));
  }
  for (int i = 0; i < 20; i++) {
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
        c.visited = false;
      }
      start.visited = true;
      return;
    }
  }
}
