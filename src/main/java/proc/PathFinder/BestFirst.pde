void GreedyFirst() {
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
      //println("FOUND");
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
    println("no solution");
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
    else if (c.visited && !findPath) {
      c.show(color(255, 0, 0, 150));
    } else {
      c.show(color(20, 100));
    }
  }
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
