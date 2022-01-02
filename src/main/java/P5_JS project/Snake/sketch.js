var gridCountY = 20;
var gridCountX;
var gridSize;
var grid = [];
var snake = [];
var food;
var snakeDirection = RIGHT_ARROW;
var lose = false;

function setup() {
  createCanvas(1200, 800);
  frameRate(10);
  gridSize = floor(height / gridCountY);
  gridCountX = floor(width / gridSize);
  for (var i = 0; i < gridCountY; i++) {
    var subGrid = [];
    for (var j = 0; j < gridCountX; j++) {
      subGrid.push(new Block(j * gridSize, i * gridSize));
    }
    grid.push(subGrid);
  }

  // food = new Food(floor(random(gridCountY)), floor(random(gridCountX)));
  food = new Food(floor(random(gridCountY)), floor(random(gridCountX)));
  snake.push(createVector(0, 2));
  snake.push(createVector(0, 1));
  snake.push(createVector(0, 0));
}

function draw() {
  if (lose) {
    textSize(100);
    stroke(0);
    text("Game Over", width / 2, height / 2);
    return;
  }
  background(255);
  stroke(0);
  strokeWeight(3);
  noFill();
  rect(0, 0, width, height);
  //strokeWeight(1);
  // for (var i = 0; i < gridCountY; i++) {
  //   for (var j = 0; j < gridCountX; j++) {
  //     grid[i][j].display();
  //   }
  // }
  var eat = false;
  if (food.detectSnake()) {
    food.reposition();
    eat = true;
  }
  food.display();
  move(eat);
  // if (lose) return;
  var counter = 0;
  snake.forEach(function(pos) {
    grid[pos.x][pos.y].display(true, counter);
    counter++;
  })
}

function move(eat) {
  var head, headX, headY;
  head = snake[0];
  if (snakeDirection == UP_ARROW) {
    headX = head.x - 1; headY = head.y;
  } else if (snakeDirection == DOWN_ARROW) {
    headX = head.x + 1; headY = head.y;
  } else if (snakeDirection == LEFT_ARROW) {
    headX = head.x; headY = head.y - 1;
  } else {
    headX = head.x; headY = head.y + 1;
  }

  for (var i = 1; i < snake.length; i++) {
    if (snake[i].x == head.x && snake[i].y == head.y) {
      lose = true;
      return;
    }
  }

  if (headX >= gridCountY || headY >= gridCountX
  || headX < 0 || headY < 0) {
    lose = true;
    return;
  }

  if (!eat) {
    snake.splice(snake.length - 1, 1);
  }
  snake.splice(0, 0, createVector(headX, headY));
}

function keyPressed() {
  if (keyCode == UP_ARROW && snakeDirection != DOWN_ARROW) {
    snakeDirection = UP_ARROW;
  } else if (keyCode == DOWN_ARROW && snakeDirection != UP_ARROW) {
    snakeDirection = DOWN_ARROW;
  } else if (keyCode == LEFT_ARROW && snakeDirection != RIGHT_ARROW) {
    snakeDirection = LEFT_ARROW;
  } else if (keyCode == RIGHT_ARROW && snakeDirection != LEFT_ARROW) {
    snakeDirection = RIGHT_ARROW;
  } else {
    return;
  }
}


function Block(x, y) {
  this.x = x;
  this.y = y;

  this.display = function(hasSnake = false, index) {
    if (hasSnake) fill(255, 0, 0);
    else noFill();

    noStroke();
    rect(this.x, this.y, gridSize, gridSize);

    var row = floor(this.y / gridSize);
    var col = floor(this.x / gridSize);
    stroke(0);
    strokeWeight(3);
    if (!containsSnake(row - 1, col, index))
      line(x, y, x + gridSize, y);
    if (!containsSnake(row + 1, col, index))
      line(x, y + gridSize, x + gridSize, y + gridSize);
    if (!containsSnake(row, col - 1, index))
      line(x, y, x, y + gridSize);
    if (!containsSnake(row, col + 1, index))
      line(x + gridSize, y, x + gridSize, y + gridSize);
  }
}

function containsSnake(row, col, index) {
  for (var i = max(0, index - 1); i < min(snake.length, index + 2); i++) {
    if (row == snake[i].x && col == snake[i].y) return true;
  }
  return false;
}


function Food(x, y) {
  this.x = x;
  this.y = y;

  this.detectSnake = function() {
    for (var i = 0; i < snake.length; i++) {
      if (snake[i].x == this.x && snake[i].y == this.y)
        return true;
    }
    return false;
  }

  this.reposition = function() {
    this.x = floor(random(gridCountY));
    this.y = floor(random(gridCountX));
    while (true) {
      snake.forEach(function(block) {
        if (this.x == block.x && this.y == block.y) {
          this.x = floor(random(gridCountY));
          this.y = floor(random(gridCountX));
        }
      })
      break;
    }
  }

  this.display = function() {
    fill(0, 255, 0);
    stroke(0);
    ellipse((this.y + 0.5) * gridSize, (this.x + 0.5)* gridSize, gridSize - 2, gridSize - 2);
  }
}
