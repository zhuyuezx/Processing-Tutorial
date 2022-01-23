ball b;
ArrayList<ball> balls;
obstacle[] obstacles;
float radius_ball = 20;
float radius_obstacle = 100;
board overall;
float elasticity = 0.75;

void setup(){
  size(960, 640);
  //fullScreen();
  //background(100);
  balls = new ArrayList<ball>();
  for (int i =0; i < 100; i++){
    balls.add(new ball(random(radius_ball, width - radius_ball), random(100), 
    random(radius_ball / 3, radius_ball * 5 / 3), random(-10, 10), 0, 
    color(random(255), random(255), random(255))));
  }
  obstacles = new obstacle[22];
  for (int i = 0; i < 5; i++){
    obstacles[i] = new obstacle(i * width / 4 , height * 2 / 5, radius_obstacle, color(0), elasticity);
  }
  for (int i = 5; i < 11; i++){
    obstacles[i] = new obstacle((i - 5) * width / 5 , height * 3 / 5, radius_obstacle, color(0), elasticity);
  }
  for (int i = 11; i < 16; i++){
    obstacles[i] = new obstacle((i - 11) * width / 4 , height * 4 / 5, radius_obstacle, color(0), elasticity);
  }
  for (int i = 16; i < 22; i++){
    obstacles[i] = new obstacle((i - 16) * width / 5 , height * 5 / 5, radius_obstacle, color(0), elasticity);
  }
  
  overall = new board(balls, obstacles);
}

void draw(){
  push();
  //background(255);
  fill(255, 50);
  rect(0, 0, width, height);
  overall.display();
  //delay(10);
  pop();
  rec();
}

void keyPressed() {
   if (key == ' ') {
     saveFrame("frame-####.jpg");
   }
}
