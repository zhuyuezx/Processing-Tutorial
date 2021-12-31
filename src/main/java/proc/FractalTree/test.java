package proc.FractalTree;

import processing.core.PApplet;

import java.awt.*;
import java.util.*;
import java.util.List;

public class test extends PApplet {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();

        res.add(new ArrayList<>(Arrays.asList(1)));
        if (numRows == 1) return res;
        res.add(new ArrayList<>(Arrays.asList(1, 1)));
        if (numRows == 2) return res;

        int counter = 3;
        while (counter <= numRows) {
            res.add(generateAtN(res.get(res.size() - 1)));
            counter++;
        }
        return res;
    }

    public List<Integer> generateAtN(List<Integer> last) {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(1);
        for (int i = 0; i < last.size() - 1; i++) {
            res.add(last.get(i) + last.get(i + 1));
        }
        res.add(1);
        return res;
    }

    public static void main(String[] args) {
    }
}
