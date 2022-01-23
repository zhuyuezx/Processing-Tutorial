class ball{
  
  PVector pos;
  PVector last_pos;
  float radius;
  PVector speed;
  color c;
  float hei_lim = height;
  
  ball(float x_pos, float y_pos, float radius, float x_speed, float y_speed, color c){
    this.pos = new PVector(x_pos, y_pos);
    this.last_pos = new PVector(radius, radius); //random value that won't change speed
    this.speed = new PVector(x_speed, y_speed);
    this.c = c;
    this.radius = radius;
  }
  
  void illustrate(){
   fill(c);
   noStroke();
   //stroke(0);
   //strokeWeight(2);
   ellipse(pos.x, pos.y, radius, radius);
  }
  
  void update(){
    pos.x += speed.x;
    pos.y += speed.y;
  }
  
  void checkEdges(){
    if (pos.x < radius / 2 || pos.x > width - radius / 2){
      speed.x = -speed.x;
    }
    if (pos.y < radius / 2 || pos.y > height - radius / 2){
      speed.y = -speed.y;
    }
    
    if(last_pos.x < radius / 2 && pos.x < radius / 2 && speed.x <= 0){
      pos.x = radius / 2;
    }
    if(last_pos.x > width - radius / 2 && pos.x > width - radius / 2 && speed.x >= 0){
      pos.x = width - radius / 2;
    }
    if(last_pos.y < radius / 2 && pos.y < radius / 2 && speed.y <= 0){
      pos.y = radius / 2;
    }
    if(last_pos.y > height - radius / 2 && pos.y > height - radius / 2 && speed.y >= 0){
      pos.y = height - radius / 2;
    }
    
    
    last_pos = new PVector(pos.x, pos.y);
  }
  
  
  void checkCollision(ball other){
    float dist_sqr = (pos.x - other.pos.x) * (pos.x - other.pos.x) + (pos.y - other.pos.y) * (pos.y - other.pos.y);
    
    PVector[] result = new PVector[2];
    PVector[] pos_adjustment = new PVector[2];
    
    if (dist_sqr < (radius / 2) * 2 * (other.radius / 2) * 2){
      result = ballCollision(pos, speed, other.pos, other.speed);
      speed = result[0];
      other.speed = result[1];
      
      pos_adjustment = collision_pos_adjustment(pos, other.pos, radius, other.radius);
      pos = pos_adjustment[0];
      other.pos = pos_adjustment[1];
    }
  }
  
  void speedChange(){
    if(pos.y < hei_lim - radius / 2){speed.y += 0.1;} //println("speed:"+y_speed+" pos:"+y_pos);}
    //else{speed.x *= 0.99;}
    else{
      pos.x = random(radius, width - radius); 
      pos.y = -radius;
      speed.y = random(0);
    }
    //println("pos_y: " + pos.y + " speed_y: " + speed.y);
  }
  
  void display(){
    this.illustrate();
    this.speedChange();
    this.update();
    this.checkEdges();
  }
  
}

PVector[] ballCollision(PVector pos, PVector speed, PVector other_pos, PVector other_speed){
  float delta_x = other_pos.x - pos.x; float delta_y = other_pos.y - pos.y;
  float normal = sqrt(delta_x * delta_x + delta_y * delta_y);
  PVector transform = new PVector(speed.x * delta_x / normal + speed.y * delta_y / normal,
                                  speed.x * delta_y / normal - speed.y * delta_x / normal);
  PVector transform_other = new PVector(other_speed.x * delta_x / normal + other_speed.y * delta_y / normal,
                                        other_speed.x * delta_y / normal - other_speed.y * delta_x / normal);
  PVector new_speed = new PVector((transform.x + transform_other.x) / 2, transform.y);
  PVector new_speed_other = new PVector((transform.x + transform_other.x) / 2, transform_other.y);
  
  PVector[] result = new PVector[2];
  result[0] = new PVector(new_speed.x * delta_x / normal + new_speed.y * delta_y / normal,
                          new_speed.x * delta_y / normal - new_speed.y * delta_x / normal);
  result[1] = new PVector(new_speed_other.x * delta_x / normal + new_speed_other.y * delta_y / normal,
                                  new_speed_other.x * delta_y / normal - new_speed_other.y * delta_x / normal);
                                  
  
  return result;
}

PVector[] collision_pos_adjustment(PVector pos, PVector other_pos, float radius, float other_radius){
  //println("pos.x: " + pos.x + " pos.y: " + pos.y + " other_pos.x: " + other_pos.x + " other_pos.y: " + other_pos.y);
  float dist = sqrt((pos.x - other_pos.x) * (pos.x - other_pos.x) + (pos.y - other_pos.y) * (pos.y - other_pos.y));
  float adjust_len = (radius / 2 + other_radius / 2 - dist) / 2;
  adjust_len ++;
  float k = (pos.y - other_pos.y) / (pos.x - other_pos.x);
  //println("dist: " + dist + " adjust_len: " + adjust_len + " k: " + k);
  PVector[] result = new PVector[2];
  if (pos.x < other_pos.x){
    //print(1);
    result[0] = new PVector(pos.x - adjust_len / sqrt(1 + k * k), pos.y - k * adjust_len / sqrt(1 + k * k));
    result[1] = new PVector(other_pos.x + adjust_len / sqrt(1 + k * k), other_pos.y + k * adjust_len / sqrt(1 + k * k));
  } 
  else{
    //print(2);
    result[0] = new PVector(pos.x + adjust_len / sqrt(1 + k * k), pos.y + k * adjust_len / sqrt(1 + k * k));
    result[1] = new PVector(other_pos.x - adjust_len / sqrt(1 + k * k), other_pos.y - k * adjust_len / sqrt(1 + k * k));
  }
  
  return result;
}
