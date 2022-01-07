void BFS() {
  if (route.size() > 0) {
    current = route.get(0);
    if (current == end) {
      findPath = true;
      println("FOUND");
      drawWindow();
      return;
    }
    route.remove(current);

    ArrayList<Cell> neighbours = current.neighbours;
    for (Cell neighbour : neighbours) {
      println(neighbour.i);
      println(neighbour.j);
      if (!neighbour.visited) {
        neighbour.visited = true;
        neighbour.previous = current;
        route.add(neighbour);
      }
    }
    drawWindow();
  } else {
    println("no solution");
    drawWindow();
    noLoop();
    return;
  }
}

void drawWindow() {
  background(20);
  for (Cell c : grid) {
    if (c.visited && !findPath) {
      c.show(color(255, 0, 0, 100));
    } else {
      c.show(color(50, 100));
    }
  }
  end.show(color(200, 0, 150));
  
  ArrayList<Cell> path = new ArrayList<Cell>();
  Cell temp = current;
  path.add(temp);
  while (temp.previous != null) {
    path.add(temp.previous);
    temp = temp.previous;
  }

  if (findPath) {
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
      }
    }
    pathCount++;
    strokeWeight(2);
    return;
  }
}
