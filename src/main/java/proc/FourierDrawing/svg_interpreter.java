package proc.FourierDrawing;

import processing.core.PApplet;
import processing.core.PVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class svg_interpreter extends PApplet {

    int numVectors = 101;
    float scale = 1f;
    boolean initialized = false;
    float rotateSpeed = 0.01f;

    ArrayList<PVector> vectors = new ArrayList();
    ArrayList<PVector> nVectors = new ArrayList();
    ArrayList<PVector> points = new ArrayList();

    String data = "";
    ArrayList<String> commands;
    PVector pen = new PVector(0, 0);
    ArrayList<PVector> route = new ArrayList();

    public void settings() {
        fullScreen();

        try {
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/PI_copy.svg");
            File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/Pi-symbol.svg");
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/aqua.svg");
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/Apple_logo_black.svg");
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/twitter-line.svg");
            //File svg = new File("D:/processing_code/Processing_Tutorial/src/main/java/proc/FourierDrawing/svp_interpreter/twitter-line1.svg");
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

        commands = extractCommands(path);
        commandsAutoFill();
    }

    public void samplePoints(ArrayList<String> curves) {
        PVector lastPoint = new PVector(0, 0);
        for (int i = 0; i < curves.size(); ) {
            String curr = curves.get(i);
            if (curr.equals("z")) {
                lastPoint = new PVector(0, 0);
                i++;
            } else if (curr.equals("v")) {
                lastPoint.y += Float.parseFloat(curves.get(i + 1));
                i += 2;
            } else if (curr.equals("V")) {
                lastPoint.y = Float.parseFloat(curves.get(i + 1));
                i += 2;
            } else if (curr.equals("M")) {
                lastPoint = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                i += 3;
            } else if (curr.equals("m")) {
                lastPoint.add(new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                i += 3;
            } else if (curr.equals("l")) {
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));

                float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
                }
                lastPoint.set(p2);
                i += 3;
            } else if (curr.equals("L")) {
                PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));

                float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, 1 - t).add(PVector.mult(p2, t)));
                }
                lastPoint.set(p2);
                i += 3;
            } else if (curr.equals("q")) {
                PVector control = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));

                float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
                            add(PVector.mult(control, 2 * t * (1 - t))).
                            add(PVector.mult(p2, t * t)));
                }
                lastPoint.set(p2);
                i += 5;
            } else if (curr.equals("Q")) {
                PVector control = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                PVector p2 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));

                float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
                            add(PVector.mult(control, 2 * t * (1 - t))).
                            add(PVector.mult(p2, t * t)));
                }
                lastPoint.set(p2);
                i += 5;
            } else if (curr.equals("t")) {
                PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
                PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
                PVector control = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
                            add(PVector.mult(control, 2 * t * (1 - t))).
                            add(PVector.mult(p2, t * t)));
                }
                lastPoint.set(p2);
                i += 3;
            } else if (curr.equals("T")) {
                PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
                PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
                PVector control = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
                PVector p2 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                float step = dist(lastPoint.x, lastPoint.y, p2.x, p2.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t)).
                            add(PVector.mult(control, 2 * t * (1 - t))).
                            add(PVector.mult(p2, t * t)));
                }
                lastPoint.set(p2);
                i += 3;
            }else if (curr.equals("c")) {
                PVector p2 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                PVector p3 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));
                PVector p4 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 5)), Float.parseFloat(curves.get(i + 6))));

                float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
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

                float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
                            add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
                            add(PVector.mult(p3, 3 * t * t * (1 - t))).
                            add(PVector.mult(p4, t * t * t)));
                }
                lastPoint.set(p4);
                i += 7;
            } else if (curr.equals("s")) {
                PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
                PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
                PVector p2 = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
                PVector p3 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2))));
                PVector p4 = PVector.add(lastPoint,
                        new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4))));
                float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
                            add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
                            add(PVector.mult(p3, 3 * t * t * (1 - t))).
                            add(PVector.mult(p4, t * t * t)));
                }
                lastPoint.set(p4);
                i += 5;
            } else if (curr.equals("S")) {
                PVector lastControl = new PVector(Float.parseFloat(curves.get(i - 4)), Float.parseFloat(curves.get(i - 3)));
                PVector lastEnd = new PVector(Float.parseFloat(curves.get(i - 2)), Float.parseFloat(curves.get(i - 1)));
                PVector p2 = PVector.add(lastPoint, PVector.sub(lastEnd, lastControl));
                PVector p3 = new PVector(Float.parseFloat(curves.get(i + 1)), Float.parseFloat(curves.get(i + 2)));
                PVector p4 = new PVector(Float.parseFloat(curves.get(i + 3)), Float.parseFloat(curves.get(i + 4)));
                float step = dist(lastPoint.x, lastPoint.y, p4.x, p4.y);
                step = 1 / max(30, step);
                for (float t = 0; t < 1; t += step) {
                    points.add(PVector.mult(lastPoint, (1 - t) * (1 - t) * (1 - t)).
                            add(PVector.mult(p2, 3 * t * (1 - t) * (1 - t))).
                            add(PVector.mult(p3, 3 * t * t * (1 - t))).
                            add(PVector.mult(p4, t * t * t)));
                }
                lastPoint.set(p4);
                i += 5;
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
        if (route.size() <= TWO_PI / rotateSpeed + 10)
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
            vectors.get(n).rotate(n * rotateSpeed);
            nVectors.get(n).rotate(-n * rotateSpeed);
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

    public ArrayList<String> extractCommands(String path) {
        ArrayList<String> res = new ArrayList();
        String prev = "";
        for (int i = 0; i < path.length(); i++) {
            char curr = path.charAt(i);
            int ascii = curr;
            if ((65 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122)) { // letters
                if (!prev.equals(""))
                    res.add(prev);
                prev = "";
                res.add(Character.toString(curr));
            } else if (48 <= ascii && ascii <= 57) { // digit
                prev += curr;
            } else if (ascii == 32 || ascii == 44) { // space or comma
                if (!prev.equals(""))
                    res.add(prev);
                prev = "";
            } else if (ascii == 45) { // "-"
                if (prev.equals(""))
                    prev += curr;
                else {
                    res.add(prev);
                    prev = Character.toString(curr);
                }
            } else if (ascii == 46) { // "."
                if (prev.equals("") || prev.equals("-")) {
                    prev += curr;
                } else if (prev.contains(".")) {
                    res.add(prev);
                    prev = Character.toString(curr);
                } else {
                    prev += curr;
                }
            }
        }
        if (!prev.equals(""))
            res.add(prev);

        return res;
    }

    public void commandsAutoFill() {
        ArrayList<String> temp = new ArrayList<>();
        HashMap<String, Integer> pool = new HashMap<String, Integer>() {{
            put("m", 3); put("M", 3); put("z", 1);
            put("v", 2); put("V", 2);
            put("l", 3); put("L", 3);
            put("v", 2); put("V", 2);
            put("q", 5); put("Q", 5);
            put("t", 3); put("T", 3);
            put("c", 7); put("C", 7);
            put("s", 5); put("S", 5);
        }};
        String lastCommand = null;
        for (int i = 0; i < commands.size();) {
            String curr = commands.get(i);
            int val = pool.getOrDefault(curr, -1);
            if (val == -1) {
                if (lastCommand == null) {
                    print("Badly formatted command\n");
                    exit();
                } else {
                    temp.add(lastCommand);
                    val = pool.get(lastCommand);
                    for (int j = 0; j < val - 1; j++) {
                        temp.add(commands.get(i + j));
                    }
                    i += val - 1;
                }
            } else {
                lastCommand = curr;
                for (int j = 0; j < val; j++) {
                    temp.add(commands.get(i + j));
                }
                i += val;
            }
        }
        commands = temp;

        for (int i = 0; i < commands.size(); i++) {
            print(commands.get(i) + "\n");
        }
    }

    public static void main(String[] args) {
        PApplet.main(svg_interpreter.class.getName());
    }

}
