void AStar() {
  if (openSet.size() > 0) {
    int winner = 0;
    for (int i = 0; i < openSet.size(); i++) {
      if (openSet.get(i).f < openSet.get(winner).f) {
        winner = i;
      }
    }
    current = openSet.get(winner);

    if (current == end) {
      findPath = true;
      //noLoop();
      drawWindowAStar();
      return;
    }

    openSet.remove(current);
    closedSet.add(current);

    ArrayList<Cell> neighbours = current.neighbours;
    for (Cell neighbour : neighbours) {
      if (!closedSet.contains(neighbour)) {
        int tempG = current.g + heuristic(neighbour, current);

        boolean newPath = false;
        if (openSet.contains(neighbour)) {
          if (tempG < neighbour.g) {
            neighbour.g = tempG;
            newPath = true;
          }
        } else {
          neighbour.g = tempG;
          newPath = true;
          openSet.add(neighbour);
          neighbour.visited = true;
        }

        if (newPath) {
          neighbour.heuristic = heuristic(neighbour, end);
          neighbour.f = neighbour.g + neighbour.heuristic;
          neighbour.previous = current;
        }
      }
    }
  } else {
    println("no solution");
    noLoop();
    return;
  }
  drawWindowAStar();
}

void drawWindowAStar() {
  background(20);
  for (Cell c : grid) {
    if (openSet.contains(c) && !findPath)
      c.show(color(0, 255, 0, 150));
    else if (c.visited && !findPath)
      c.show(color(255, 0, 0, 150));
    else 
      c.show(color(20, 100));
  }
  //if (!findPath) {
  //  for (Cell c : closedSet) {
  //    c.show(color(255, 0, 0, 150));
  //  }
  //  for (Cell c : openSet) {
  //    c.show(color(0, 255, 0, 150));
  //  }
  //}
  start.show(color(0, 0, 200, 150));
  end.show(color(200, 0, 150));

  ArrayList<Cell> path = new ArrayList<Cell>();
  Cell temp = current;
  path.add(temp);
  while (temp.previous != null) {
    path.add(temp.previous);
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
