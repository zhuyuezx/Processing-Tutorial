var rows;
var cols;
var current;
var previous;
var minStrength = 255;
var maxStrength = 2500;

var dampening = 0.99;

function setup() {
  pixelDensity(1);
  createCanvas(window.innerWidth, window.innerHeight);
  
  cols = width;
  rows = height;
  current = new Array(cols).fill(0).map(n => new Array(rows).fill(0));
  previous = new Array(cols).fill(0).map(n => new Array(rows).fill(0));
}

function mouseDragged() {
  previous[mouseX][mouseY] = random(minStrength, maxStrength);
}

function draw() {
  background(0);
  loadPixels();

  for (var n = 0; n < 3; n++)
  previous[floor(random(width))][floor(random(height))] = random(minStrength, maxStrength);

  for (var i = 1; i < cols - 1; i++) {
    for (var j = 1; j < rows - 1; j++) {
      current[i][j] = (previous[i - 1][j] + previous[i + 1][j] 
        + previous[i][j - 1] + previous[i][j + 1]) / 2 
        - current[i][j];
      current[i][j] *= dampening;
      let index = (i + j * cols) * 4;
      pixels[index + 0] = current[i][j];
      pixels[index + 1] = current[i][j];
      pixels[index + 2] = current[i][j];
    }
  }
  updatePixels();

  let temp = previous;
  previous = current;
  current = temp;
}
