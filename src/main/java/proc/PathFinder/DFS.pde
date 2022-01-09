void DFS() {
  if (route.size() > 0) {
    current = route.get(0);
    if (current == end) {
      findPath = true; 
      println("Found");
      drawWindowDFS();
      return;
    }
    route.remove(current);

    ArrayList<Cell> neighbours = current.neighbours;
    for (Cell neighbour : neighbours) {
      if (!neighbour.visited) {
        neighbour.visited = true;
        neighbour.previous = current;
        route.add(0, neighbour);
      }
    }
    drawWindowDFS();
  } else {
    println("No solution");
    drawWindowDFS();
    noLoop();
    return;
  }
}

void drawWindowDFS() {
  background(20);
  for (Cell c : grid) {
    if (current == c && !findPath)
      c.show(color(0, 255, 0, 150));
    else if (c.visited && !findPath)
      c.show(color(255, 0, 0, 150));
    else 
      c.show(color(20, 100));
  }
  start.show(color(0, 0, 200, 150));
  end.show(color(200, 0, 150));

  current.backTrack();
}
