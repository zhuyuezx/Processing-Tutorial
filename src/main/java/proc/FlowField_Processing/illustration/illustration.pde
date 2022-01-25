float angle;
float noiseValue;
float strength = 0.005;

int gridSize = 25;
PVector[][] grid;

void setup() {
  size(960, 640);
  //fullScreen();
  background(238);
  grid = new PVector[height / gridSize + 1][width / gridSize + 1];
}

void draw() {
  background(238);
  for (int y = 0; y < grid.length; y++) {
    for (int x = 0; x < grid[0].length; x++) {
      noiseValue = noise(x * strength, y * strength, angle * strength);
      grid[y][x] = PVector.fromAngle(noiseValue * 5 * TWO_PI);
      PVector curr = grid[y][x];
      stroke(0);
      strokeWeight(5);
      line(x * gridSize, y * gridSize, 
      (x + curr.x) * gridSize, (y + curr.y) * gridSize);
    }
  }
  angle++;
  rec();
}
