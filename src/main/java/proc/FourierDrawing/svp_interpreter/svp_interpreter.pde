import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

int numVectors = 101;
int scale = 20;

ArrayList<PVector> vectors = new ArrayList();
ArrayList<PVector> nVectors = new ArrayList();
ArrayList<PVector> points = new ArrayList();

String data = "";
ArrayList<String> commands;
PVector pen;
ArrayList<PVector> route = new ArrayList();

void setup() {
  size(800, 800);
  
  try {
    File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/PI_copy.svg");
    Scanner myReader = new Scanner(svg);
    while (myReader.hasNextLine()) {
      data += myReader.nextLine();
    }
    //print(data);
    myReader.close();
  } catch (FileNotFoundException e) {
    print("An error occured\n");
    e.printStackTrace();
  }
  String path = data.substring(data.indexOf("<path"));
  path = path.substring(path.indexOf("\"") + 1, path.indexOf("\"style="));
  //print(path);
  
  commands = new ArrayList();
  String prev = "";
  for (int i = 0; i < path.length(); i++) {
    char curr = path.charAt(i);
    int ascii = (int)curr;
    if ((65 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122)) {
      commands.add(prev);
      prev = "";
      commands.add(Character.toString(curr));
    } else if (ascii == 45 || ascii == 46 || (48 <= ascii && ascii <= 57)) {
      prev += curr;
    } else if (ascii == 32 || ascii == 44) {
      commands.add(prev);
      prev = "";
    }
  }
  if (!prev.equals(""))
    commands.add(prev);
  for (int i = 0; i < commands.size(); i++) {
    print(commands.get(i) + "\n");
  }
  
  samplePoints(commands);
  setVectors();
}

void samplePoints(ArrayList<String> curves) {
  PVector lastPoint = new PVector(0, 0);
  for (int i = 0; i < curves.size();) {
    String curr = curves.get(i);
    if (curr.equals("M")) {
      lastPoint = new PVector(float(curves.get(i + 1)), float(curves.get(i + 2)));
      i += 3;
    } 
    else if (curr.equals("l")) {
      PVector p2 = PVector.add(lastPoint, 
      new PVector(float(curves.get(i + 1)), float(curves.get(i + 2))));
      
      for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
        points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
      }
      lastPoint.set(p2);
      i += 3;
    } 
    else if (curr.equals("L")) {
      PVector p2 = new PVector(float(curves.get(i + 1)), float(curves.get(i + 2)));
      
      for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
        points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
      }
      lastPoint.set(p2);
      i += 3;
    } 
    else if (curr.equals("q")) {
      PVector control = PVector.add(lastPoint, 
      new PVector(float(curves.get(i + 1)), float(curves.get(i + 2))));
      PVector p2 = PVector.add(lastPoint, 
      new PVector(float(curves.get(i + 3)), float(curves.get(i + 4))));
      
      for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
        points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
        add(PVector.mult(control, 2 * t * (1 - t))).
        add(PVector.mult(p2, t * t)));
      }
      lastPoint.set(p2);
      i += 5;
    }
  }
}

void setVectors() {
  for (int n = 0; n < numVectors; n++) {
    PVector total = new PVector(0, 0);
    for (float t = 0; t <= 1; t += 1.0 / float(points.size())) {
      PVector v = points.get(int(t * points.size()));
      float r = sqrt(v.x * v.x + v.y * v.y);
      float theta = atan(v.y / v.x) - n * TWO_PI * t;
      total.add(new PVector(r * cos(theta), r * sin(theta)));
    }
    total.div(points.size());
    vectors.set(n, total);
  }
  nVectors.add(new PVector(0, 0));
  for (int n = 1; n < numVectors; n++) {
    PVector total = new PVector(0, 0);
    for (float t = 0; t <= 1; t += 1.0 / float(points.size())) {
      PVector v = points.get(int(t * points.size()));
      float r = sqrt(v.x * v.x + v.y * v.y);
      float theta = atan(v.y / v.x) + n * TWO_PI * t;
      total.add(new PVector(r * cos(theta), r * sin(theta)));
    }
    total.div(points.size());
    nVectors.set(n, total);
  }
}

void drawVectors() {
  noFill();
  strokeWeight(1);
  stroke(150);

  PVector lastPoint = new PVector(width / scale / 2, height / scale / 2);
  //beginShape();
  for (int n = 0; n < numVectors; n++) {
    line(lastPoint.x * scale, lastPoint.y * scale, 
      (lastPoint.x + vectors.get(n).x) * scale, 
      (lastPoint.y + vectors.get(n).y) * scale);
    lastPoint.add(vectors.get(n));
    line(lastPoint.x * scale, lastPoint.y * scale, 
      (lastPoint.x + nVectors.get(n).x) * scale, 
      (lastPoint.y + nVectors.get(n).y) * scale);
    lastPoint.add(nVectors.get(n));
  }
  pen.set(lastPoint);
}

void drawPath() {
  route.add(PVector.mult(pen, scale));
  stroke(255, 255, 0);
  strokeWeight(3);
  beginShape();
  for (PVector p: route) {
    vertex(p.x, p.y);
  }
  endShape();
}

void rotateVectors() {
  for (int n = 0; n < numVectors; n++) {
    vectors.get(n).rotate(n * 0.01);
    nVectors.get(n).rotate(-n * 0.01);
  }
}

void draw() {
  background(0);
  rotateVectors();
  drawVectors();
  drawPath();
}
