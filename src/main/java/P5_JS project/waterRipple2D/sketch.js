var current, previous;
var minStrength = 255;
var maxStrength = 2500;
var dampening = 0.99;
var diff = 1;

var slider1;
var slider2;
var slider3;
var slider4, slider5;
var diffSlider;

function setup() {
  pixelDensity(1);
  createCanvas(window.innerWidth, window.innerHeight);
  setupButtons();
  current = new Array(width).fill(0).map(n => new Array(height).fill(0));
  previous = new Array(width).fill(0).map(n => new Array(height).fill(0));
}

function mouseDragged() {
  if (mouseX < 200 && mouseY < 200) {
    return;
  }
  previous[mouseX][mouseY] = 250;
}

function draw() {
  background(0);
  textSize(24);
  fill(200);
  index1 = slider1.value();
  text(index1, 100, 30);
  index2 = slider2.value();
  text(index2, 100, 60);
  index3 = slider3.value();
  text(index3, 100, 90);
  index4 = slider4.value();
  text(index4, 100, 120);
  index5 = slider5.value();
  text(index5, 100, 150);
  diff = diffSlider.value();
  text(diff, 100, 180);
  update(diff);
  [current, previous] = [previous, current];
}

// function transform(i, j) {
//   current[i][j] = (previous[i - 1][j] + previous[i + 1][j] + previous[i][j - 1] + previous[i][j + 1]) * index1 
//   - current[i][j] * index3;
//   current[i][j] *= dampening;
// }

function update(diff) {
  loadPixels();
  for (var n = 0; n < 2; n++)
  previous[floor(random(width))][floor(random(height))] = random(minStrength, maxStrength);
  for (var i = diff; i < width - diff; i++) {
    for (var j = diff; j < height - diff; j++) {
      transform(i, j);
      let index = (i + j * width) * 4;
      pixels[index + 0] = current[i][j] * 0.35; 
      pixels[index + 1] = current[i][j] * 0.8; 
      pixels[index + 2] = current[i][j];
    }
  }
  updatePixels();
}

function transform(i, j) {
  current[i][j] = (previous[i - diff][j] + previous[i + diff][j]) * index1 + 
  (previous[i][j - diff] + previous[i][j + diff]) * index2  - 
  current[i][j] * index3;
  current[i][j] = constrain(current[i][j], 0, 2500);
  current[i][j] *= dampening;
}

// function transform(i, j) {
//   current[i][j] = (previous[i - diff][j] + previous[i + diff][j]) * index1 + 
//   (previous[i][j - diff] + previous[i][j + diff]) * index2  + 
//   (previous[i - diff][j - diff] + previous[i + diff][j - diff]) * index3 + 
//   (previous[i - diff][j + diff] + previous[i + diff][j + diff]) * index4  - 
//   current[i][j] * index5;
//   current[i][j] = constrain(current[i][j], 0, 2500);
//   current[i][j] *= dampening;
// }
// 0.17, 0.3, 0.97