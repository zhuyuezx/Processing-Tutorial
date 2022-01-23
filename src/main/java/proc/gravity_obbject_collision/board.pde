class board{
  ArrayList<ball> balls;
  obstacle[] obstacles;
  
  board(ArrayList<ball> balls, obstacle[] obstacles){
   this.balls = balls;
   this.obstacles = obstacles;
  }
  
  void display(){
    for (ball b: balls){
      b.display();
    }
    for (int i = 0; i < balls.size(); i++){
      for(int j = i + 1; j < balls.size(); j++){
        balls.get(i).checkCollision(balls.get(j));
      }
    }
   
   for (obstacle o: obstacles){
     o.display();
     o.checkBalls(balls);
   }
  }
}
