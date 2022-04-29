package proc.FourierDrawing;

import processing.core.PApplet;
import processing.core.PVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class svg_interpreter extends PApplet {

    int numVectors = 401;
    float scale = 3f;
    boolean initialized = false;

    ArrayList<PVector> vectors = new ArrayList();
    ArrayList<PVector> nVectors = new ArrayList();
    ArrayList<PVector> points = new ArrayList();

    String data = "";
    ArrayList<String> commands;
    PVector pen = new PVector(0, 0);
    ArrayList<PVector> route = new ArrayList();

    public void settings() {
        //size(800, 800);
        fullScreen();

        try {
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/PI_copy.svg");
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/Pi-symbol.svg");
            File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/aqua.svg");
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
        path = path.substring(path.indexOf(" d=\"") + 4);
        path = path.substring(0, path.indexOf("\""));
        //print(path);

        commands = new ArrayList();
        String prev = "";
        for (int i = 0; i < path.length(); i++) {
            char curr = path.charAt(i);
            int ascii = curr;
            if ((65 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122)) { // letters
                if (!prev.equals(""))
                    commands.add(prev);
                prev = "";
                commands.add(Character.toString(curr));
            } else if (ascii == 45 || ascii == 46 || (48 <= ascii && ascii <= 57)) { //"-", "." or digit
                prev += curr;
            } else if (ascii == 32 || ascii == 44) { // space or comma
                if (!prev.equals(""))
                    commands.add(prev);
                prev = "";
            }
        }
        if (!prev.equals(""))
            commands.add(prev);
        for (int i = 0; i < commands.size(); i++) {
            print(commands.get(i) + "\n");
        }
    }

    public void samplePoints(ArrayList<String> curves) {
        PVector lastPoint = new PVector(0, 0);
        for (int i = 0; i < curves.size(); ) {
            String curr = curves.get(i);
            if (curr.equals("M")) {
                lastPoint = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                i += 3;
            } else if (curr.equals("l")) {
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));

                for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
                    points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
                }
                lastPoint.set(p2);
                i += 3;
            } else if (curr.equals("L")) {
                PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));

                for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
                    points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
                }
                lastPoint.set(p2);
                i += 3;
            } else if (curr.equals("q")) {
                PVector control = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));

                for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
                            add(PVector.mult(control, 2 * t * (1 - t))).
                            add(PVector.mult(p2, t * t)));
                }
                lastPoint.set(p2);
                i += 5;
            } else if (curr.equals("Q")) {
                PVector control = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                PVector p2 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));

                for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
                            add(PVector.mult(control, 2 * t * (1 - t))).
                            add(PVector.mult(p2, t * t)));
                }
                lastPoint.set(p2);
                i += 5;
            }else if (curr.equals("c")) {
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                PVector p3 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));
                PVector p4 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 4)), Float.parseFloat(curves.get(i + 5))));

                for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
                            add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
                            add(PVector.mult(p3, 3 * t * t * (1 - t))).
                            add(PVector.mult(p4, t * t * t)));
                }
                lastPoint.set(p4);
                i += 7;
            } else if (curr.equals("C")) {
                PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                PVector p3 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));
                PVector p4 = new PVector(Float.parseFloat(curves.get(i + 5)), Float.parseFloat(curves.get(i + 6)));

                for (float t = 0; t < 1; t += 1 / dist(lastPoint.x, lastPoint.y, p2.x, p2.y)) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
                            add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
                            add(PVector.mult(p3, 3 * t * t * (1 - t))).
                            add(PVector.mult(p4, t * t * t)));
                }
                lastPoint.set(p4);
                i += 7;
            }
            else {
                i++;
            }
        }
    }

    public void setVectors() {
        for (int n = 0; n < numVectors; n++) {
            PVector total = new PVector(0, 0);
            for (float t = 0; t <= 1; t += 1.0 / (float) points.size()) {
                PVector v = points.get((int) (t * points.size()));
                float r = sqrt(v.x * v.x + v.y * v.y);
                float theta = atan(v.y / v.x) - n * TWO_PI * t;
                total.add(new PVector(r * cos(theta), r * sin(theta)));
            }
            total.div(points.size());
            //vectors.set(n, total);
            vectors.add(total);
        }
        nVectors.add(new PVector(0, 0));
        for (int n = 1; n < numVectors; n++) {
            PVector total = new PVector(0, 0);
            for (float t = 0; t <= 1; t += 1.0 / (float) points.size()) {
                PVector v = points.get((int) (t * points.size()));
                float r = sqrt(v.x * v.x + v.y * v.y);
                float theta = atan(v.y / v.x) + n * TWO_PI * t;
                total.add(new PVector(r * cos(theta), r * sin(theta)));
            }
            total.div(points.size());
            //nVectors.set(n, total);
            nVectors.add(total);
        }
        print("here");
    }

    public void drawVectors() {
        noFill();
        strokeWeight(2);
        stroke(150, 100);

        //PVector lastPoint = new PVector(0, 0);
        //PVector lastPoint = new PVector(width / scale / 2, height / scale / 2);
        //PVector lastPoint = new PVector(vectors.get(0).x, vectors.get(0).y);
        PVector lastPoint = new PVector(width / 2 / scale, height / 2 / scale);
        beginShape();
        for (int n = 1; n < numVectors; n++) {
            line(lastPoint.x * scale, lastPoint.y * scale,
                    (lastPoint.x + vectors.get(n).x) * scale,
                    (lastPoint.y + vectors.get(n).y) * scale);
            float r = 2 * sqrt(vectors.get(n).x * scale * vectors.get(n).x * scale +
                    vectors.get(n).y * scale * vectors.get(n).y * scale);
            ellipse(lastPoint.x * scale, lastPoint.y * scale,
                    r, r);
            lastPoint.add(vectors.get(n));
            line(lastPoint.x * scale, lastPoint.y * scale,
                    (lastPoint.x + nVectors.get(n).x) * scale,
                    (lastPoint.y + nVectors.get(n).y) * scale);
            r = 2 * sqrt(nVectors.get(n).x * scale * nVectors.get(n).x * scale +
                    nVectors.get(n).y * scale * nVectors.get(n).y * scale);
            ellipse(lastPoint.x * scale, lastPoint.y * scale,
                    r, r);
            lastPoint.add(nVectors.get(n));
        }
        pen.set(lastPoint);
    }

    public void drawPath() {
        if (route.size() <= points.size())
            route.add(PVector.mult(pen, scale));
        //route.add(pen);
        stroke(255, 255, 0);
        strokeWeight(3);
        beginShape();
        for (PVector p : route) {
            vertex(p.x, p.y);
        }
        endShape();
    }

    public void rotateVectors() {
        for (int n = 0; n < numVectors; n++) {
            vectors.get(n).rotate(n * 0.01f);
            nVectors.get(n).rotate(-n * 0.01f);
        }
    }

    public void draw() {
        if (!initialized) {
            samplePoints(commands);
            setVectors();
            initialized = true;
        }
        background(0);
        rotateVectors();
        drawVectors();
        drawPath();
    }

    public static void main(String[] args) {
        PApplet.main(svg_interpreter.class.getName());
    }

}
