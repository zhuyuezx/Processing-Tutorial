class obstacle{
  
  PVector pos;
  float radius;
  color c;
  float respond_rate;
  
  obstacle(float x_pos, float y_pos, float radius, color c, float respond_rate){
   this.pos = new PVector(x_pos, y_pos);
   this.radius = radius;
   this.c = c;
   this.respond_rate = respond_rate;
  }
  
  void display(){
    fill(c);
    stroke(0);
    strokeWeight(2);
    ellipse(pos.x, pos.y, radius, radius);
  }
  
  void checkBalls(ArrayList<ball> balls){
    for (ball b: balls){
      if (this.intersect(b)){
        PVector new_speed = ball_obstacle_Collision(b.pos, b.speed, pos, respond_rate);
        PVector new_pos = obstacle_collision_pos_adjustment(b.pos, pos, b.radius, radius);
        b.pos = new_pos;
        b.speed = new_speed;
      }
    }
  }
  
  boolean intersect(ball b){
    float dist_sqr = (pos.x - b.pos.x) * (pos.x - b.pos.x) + (pos.y - b.pos.y) * (pos.y - b.pos.y);
    return (dist_sqr <= (radius / 2 + b.radius / 2) * (radius / 2 + b.radius / 2));
  }
}

PVector ball_obstacle_Collision(PVector pos, PVector speed, PVector other_pos, float respond_rate){
  float delta_x = other_pos.x - pos.x; float delta_y = other_pos.y - pos.y;
  float normal = sqrt(delta_x * delta_x + delta_y * delta_y);
  PVector transform = new PVector(speed.x * delta_x / normal + speed.y * delta_y / normal,
                                  speed.x * delta_y / normal - speed.y * delta_x / normal);
  //PVector transform_other = new PVector(other_speed.x * delta_x / normal + other_speed.y * delta_y / normal,
  //                                      other_speed.x * delta_y / normal - other_speed.y * delta_x / normal);
  PVector new_speed = new PVector(-transform.x, transform.y);
  
  PVector result;
  result = new PVector(new_speed.x * delta_x / normal + new_speed.y * delta_y / normal,
                          new_speed.x * delta_y / normal - new_speed.y * delta_x / normal);
  
  result = new PVector(result.x * respond_rate, result.y * respond_rate); 
  return result;
}

PVector obstacle_collision_pos_adjustment(PVector pos, PVector other_pos, float radius, float other_radius){
  //println("pos.x: " + pos.x + " pos.y: " + pos.y + " other_pos.x: " + other_pos.x + " other_pos.y: " + other_pos.y);
  float dist = sqrt((pos.x - other_pos.x) * (pos.x - other_pos.x) + (pos.y - other_pos.y) * (pos.y - other_pos.y));
  float adjust_len = (radius / 2 + other_radius / 2 - dist);
  adjust_len ++;
  float k = (pos.y - other_pos.y) / (pos.x - other_pos.x);
  //println("dist: " + dist + " adjust_len: " + adjust_len + " k: " + k);
  PVector result;
  if (pos.x < other_pos.x){
    //print(1);
    result = new PVector(pos.x - adjust_len / sqrt(1 + k * k), pos.y - k * adjust_len / sqrt(1 + k * k));
  } 
  else{
    //print(2);
    result = new PVector(pos.x + adjust_len / sqrt(1 + k * k), pos.y + k * adjust_len / sqrt(1 + k * k));
  }
  
  return result;
}
