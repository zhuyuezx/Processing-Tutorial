class Cell {
  constructor(pos) {
    this.pos = pos;
    // Whether there's a wall on the left/top side.
    this.left = this.top = false;
    this.visited = false;
    this.step = Infinity;
  }
  loc(dim = 1) {
    // Get 1D position
    if (dim === 1) return this.pos;
    // Get 2D position (x, y)
    if (dim === 2) return Cell.conv(this.pos);
    return null;
  }
  getNeighbors(hasWalls) {
    const [x, y] = this.loc(2);
    const neighbors = new Set();
    x > 0 && neighbors.add(Cell.at(x - 1, y));
    y > 0 && neighbors.add(Cell.at(x, y - 1));
    x < sizeMaze - 1 && neighbors.add(Cell.at(x + 1, y));
    y < sizeMaze - 1 && neighbors.add(Cell.at(x, y + 1));
    // Filter accessible neighbors when walls have been erected.
    if (hasWalls) {
      let n;
      !this.left && neighbors.delete(Cell.at(x - 1, y));
      !this.top && neighbors.delete(Cell.at(x, y - 1));
      x < sizeMaze - 1 && !(n = Cell.at(x + 1, y)).left && neighbors.delete(n);
      y < sizeMaze - 1 && !(n = Cell.at(x, y + 1)).top && neighbors.delete(n);
    }
    return [...neighbors].filter((cell) => !cell.visited);
  }

  // Convert 1D position to 2D position and vice versa.
  static conv(arg1, arg2) {
    if (isNaN(arg2)) return [arg1 % sizeMaze, (arg1 / sizeMaze) | 0];
    return arg2 * sizeMaze + arg1;
  }

  static at(arg1, arg2) {
    if (isNaN(arg2)) return maze[arg1];
    return maze[Cell.conv(arg1, arg2)];
  }

  static dist(cell1, cell2) {
    const [x1, y1] = cell1.loc(2);
    const [x2, y2] = cell2.loc(2);
    // 4-direction
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    // 8-direction
    // return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    // 360
    // return Math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2);
  }
}

const sizeMaze = 60;
const sizeRoom = 600;
const sizeCell = sizeRoom / sizeMaze;
const maze = Array(sizeMaze ** 2)
  .fill()
  .map((_, i) => new Cell(i));
let curr = maze[0];
curr.visited = true;

function setup() {
  createCanvas(sizeRoom, sizeRoom);
  frameRate(60);
}

function draw() {
  // Animate the maze generating process.
  if (!maze.start) {
    repeat(buildPath, 10);
    // Draw the exit when the map generation is completed.
    if (maze.exit) {
      noStroke();
      fill(255, 0, 0, 150);
      const [x, y] = maze.exit.loc(2);
      rect(x * sizeCell, y * sizeCell, sizeCell, sizeCell);
      return;
    }
    background(0);
    stroke(255);
    for (let i = 0; i < maze.length; i++) {
      const cell = maze[i];
      let [x, y] = cell.loc(2);
      x *= sizeCell;
      y *= sizeCell;
      x && !cell.left && line(x, y, x, y + sizeCell);
      y && !cell.top && line(x, y, x + sizeCell, y);
    }
    return;
  }
  noStroke();
  // Animate the path finding algorithm.
  if (maze.exit) {
    fill(142, 68, 173, 100);
    repeat(() => {
      findPath();
      const [x, y] = curr.loc(2);
      if (!maze.exit) return false;
      rect(x * sizeCell, y * sizeCell, sizeCell, sizeCell);
    }, 10);
    return;
  }
  // If the path from exit to start has been drawn, quit.
  if (!retrievePath()) return;
  // Animate the drawing of the path from exit to start.
  const [x, y] = curr.loc(2);
  fill(243, 156, 18, 100);
  rect(x * sizeCell, y * sizeCell, sizeCell, sizeCell);
}

function repeat(func, times) {
  for (let i = 0; i < times; i++) {
    if (func() === false) return;
  }
}

function buildPath() {
  const neighbors = curr.getNeighbors();
  if (neighbors.length) {
    const chosen = random(neighbors);
    chosen.visited = true;
    const [x, y] = curr.loc(2);
    const [x2, y2] = chosen.loc(2);
    x2 === x - 1 && (curr.left = true);
    x2 === x + 1 && (chosen.left = true);
    y2 === y - 1 && (curr.top = true);
    y2 === y + 1 && (chosen.top = true);
    curr = chosen;
    return;
  }
  // If all the neighbors of the current cell are visited, choose another cell on the board.
  // A better method is to go backward of the newly created path until there's a cell that has an unvisited neighbor.
  const remainingPaths = maze.filter(
    (cell) => cell.visited && cell.getNeighbors().length
  );
  if (remainingPaths.length) {
    curr = random(remainingPaths);
    return;
  }
  // If the maze has been generated completely, choose a random exit and prepare for the path finding algorithm.
  const start = (maze.start = maze[0]);
  // const start = maze.start = random(maze.slice(1));
  const exit = (maze.exit = random(maze.slice(1)));
  maze.forEach(
    (cell) => ((cell.visited = false), (cell.dist = Cell.dist(start, exit)))
  );
  start.step = 0;
}

function findPath() {
  if (curr === maze.exit) {
    maze.exit = null;
    return;
  }
  curr = maze.reduce(
    (min, cell, score) => (
      (score = cell.dist + cell.step),
      !cell.visited && score < min[0] && ((min[0] = score), (min[1] = cell)),
      min
    ),
    [Infinity, null]
  )[1];
  curr.visited = true;
  const [x, y] = curr.loc(2);
  const neighbors = curr.getNeighbors(true);
  neighbors.forEach(
    (cell) =>
      cell.step > curr.step + 1 &&
      ((cell.step = curr.step + 1), (cell.parent = curr))
  );
}

function retrievePath() {
  if (curr === maze.start) return false;
  curr = curr.parent;
  return true;
}
