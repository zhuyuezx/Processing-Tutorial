void BestFirst() {
  if (route.size() > 0) {
    int winner = 0;
    int minDist = heuristic(route.get(winner), end);
    for (int i = 0; i < route.size(); i++) {
      int dist = heuristic(route.get(i), end);
      if (dist < minDist) {
        minDist = dist;
        winner = i;
      }
    }
    current = route.get(winner);
    
    if (current == end) {
      findPath = true; 
      println("Found");
      drawWindowBestFirst();
      return;
    }
    route.remove(current);

    ArrayList<Cell> neighbours = current.neighbours;
    for (Cell neighbour : neighbours) {
      if (!neighbour.visited) {
        neighbour.visited = true;
        neighbour.previous = current;
        route.add(neighbour);
      }
    }
    drawWindowBestFirst();
  } else {
    println("No solution");
    drawWindowBestFirst();
    noLoop();
    return;
  }
}

void drawWindowBestFirst() {
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
