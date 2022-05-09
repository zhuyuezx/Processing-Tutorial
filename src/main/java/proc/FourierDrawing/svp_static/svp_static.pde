import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

int numVectors = 501;
float scale = 5;
float origScale = scale;
float rotateSpeed = 0.01;
float changeRate = 0.01;
boolean drawPath = false;

ArrayList<PVector> vectors = new ArrayList();
ArrayList<PVector> nVectors = new ArrayList();
ArrayList<PVector> points = new ArrayList();

ArrayList<String> commands;
PVector pen = new PVector(0, 0);
ArrayList<PVector> route = new ArrayList();

void setup() {
  fullScreen();

  //String path = extractSvg("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/Pi-symbol.svg");
  //String path = extractSvg("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/france-23502.svg");
  //String path = extractSvg("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/britain.svg");
  String path = extractSvg("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/Xi.svg");
  
  commands = extractCommands(path);
  commandsAutoFill();
  
  samplePoints(commands);
  setVectors();
}

void draw() {
  background(0);
  rotateVectors();
  drawVectors();
  drawPath();
  
  rec();
  
  textSize(30);
  String msg1 = "number of vector: " + numVectors;
  String msg2 = "rotation speed: " + round(TWO_PI / rotateSpeed / frameRate) + " seconds per cycle";
  String msg3 = "progress: " + round(100 * ((frameCount * rotateSpeed / TWO_PI) % 1)) + "%";
  fill(238);
  text(msg1, 40, height - 150, 400, 40);
  text(msg2, 40, height - 100, 600, 40);
  text(msg3, 40, height - 50, 400, 40);
  
  //rec();
}
