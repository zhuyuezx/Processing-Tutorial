import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

int numVectors = 101;
float scale = 10;
float origScale = scale;
float rotateSpeed = 0.005;
float changeRate = 0.01;

ArrayList<PVector> vectors = new ArrayList();
ArrayList<PVector> nVectors = new ArrayList();
ArrayList<PVector> points = new ArrayList();

ArrayList<String> commands;
PVector pen = new PVector(0, 0);
ArrayList<PVector> route = new ArrayList();

void setup() {
  fullScreen();

  //String path = extractSvg("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/Pi-symbol.svg");
  String path = extractSvg("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/france-23502.svg");
  
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
}
