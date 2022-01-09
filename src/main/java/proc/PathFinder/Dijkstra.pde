void Dijkstra() {
  println(route.size());
  if (route.size() > 0) {
    int winner = 0;
    for (int i = 0; i < route.size(); i++) {
      if (route.get(i).dist < route.get(winner).dist) {
        winner = i;
      }
    }
    current = route.get(winner);
    current.visited = true;

    if (current == end) {
      findPath = true; 
      println("Found");
      drawWindowDijkstra();
      return;
    }
    route.remove(winner);

    ArrayList<Cell> neighbours = current.neighbours;
    for (Cell neighbour : neighbours) {
      if (!neighbour.visited) {
        int alt = current.dist + 1;
        if (alt < neighbour.dist) {
          neighbour.dist = alt;
          neighbour.previous = current;
        }
      }
    }
  } else {
    println("No solution");
    noLoop();
    return;
  }
  drawWindowDijkstra();
}

void drawWindowDijkstra() {
  background(20);
  for (Cell c : grid) {
    if (current == c && !findPath)
      c.show(color(0, 255, 0, 150));
    else if (!c.visited || findPath)
      c.show(color(20, 100));
    else 
      c.show(color(200, 0, 0, 150));
  }
  start.show(color(0, 0, 200, 150));
  end.show(color(200, 0, 150));

  current.backTrack();
}
