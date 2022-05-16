function setup() {
  createCanvas(windowWidth, windowHeight);
}

function draw() {
  background(0);
  stroke(200);
  fill(200);
  drawArrowStatic(0, 0, 100, 100);
}

function drawArrowStatic(xStart, yStart, xEnd, yEnd) {
  var len = dist(xStart, yStart, xEnd, yEnd);
  var sw = len / 60;
  sw = max(sw, 1);
  strokeWeight(sw);
  line(xStart, yStart, xEnd, yEnd);
  var angle = atan2(yEnd - yStart, xEnd - xStart);
  var complementAngle = HALF_PI - angle;
  beginShape();
  vertex(xEnd + 2 * sw * cos(angle), yEnd + 2 * sw * sin(angle));
  var newX = xEnd - 2 * sw * cos(angle);
  var newY = yEnd - 2 * sw * sin(angle);
  vertex(newX - 2 * sw * cos(complementAngle), newY + 2 * sw * sin(complementAngle));
  vertex(newX + 2 * sw * sin(angle), newY - 2 * sw * cos(angle));
  vertex(xEnd + 2 * sw * cos(angle), yEnd + 2 * sw * sin(angle));
  endShape();
}
