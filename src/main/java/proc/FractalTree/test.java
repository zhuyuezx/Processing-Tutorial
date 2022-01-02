package proc.FractalTree;

import processing.core.PApplet;
import java.awt.*;
import java.util.*;
import java.util.List;

public class test extends PApplet {

    public List<Integer> getRow(int rowIndex) {
        rowIndex++;
        List<Integer> res = new ArrayList<>();
        res.add(1);
        if (rowIndex == 1) return res;
        long init = 1;
        for (int i = 1; i < rowIndex; i++) {
            init = init * (rowIndex - i) / i;
            res.add((int)init);
        }
        return res;
    }

    public static void main(String[] args) {
        test t = new test();
        System.out.println(t.getRow(30));
    }
}
