void AStar() {
  if (route.size() > 0) {
    int winner = 0;
    for (int i = 0; i < route.size(); i++) {
      if (route.get(i).f < route.get(winner).f) 
        winner = i;
    }
    current = route.get(winner);

    if (current == end) {
      findPath = true; 
      println("Found");
      drawWindowAStar();
      return;
    }
    route.remove(current);
    closedSet.add(current);

    ArrayList<Cell> neighbours = current.neighbours;
    for (Cell neighbour : neighbours) {
      if (!closedSet.contains(neighbour)) {
        int tempG = current.g + 1;

        boolean newPath = false;
        if (route.contains(neighbour)) {
          if (tempG < neighbour.g) {
            neighbour.g = tempG;
            newPath = true;
          }
        } else {
          neighbour.g = tempG;
          neighbour.visited = true;
          newPath = true;
          route.add(neighbour);
        }
        if (newPath) {
          neighbour.heuristic = heuristic(neighbour, end);
          neighbour.f = neighbour.g + neighbour.heuristic;
          neighbour.previous = current;
        }
      }
    }
  } else {
    println("No solution");
    noLoop();
    return;
  }
  drawWindowAStar();
}

void drawWindowAStar() {
  background(20);
  for (Cell c : grid) {
    if (current == c && !findPath)
      c.show(color(0, 255, 0, 150));
    else if (route.contains(c) && !findPath)
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
