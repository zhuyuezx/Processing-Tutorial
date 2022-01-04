package proc.FractalTree;

import processing.core.PApplet;

import java.util.Arrays;

public class test extends PApplet {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) return new int[][]{new int[]{newInterval[0], newInterval[1]}};

        int pos1 = helper1(intervals, newInterval, 0, intervals.length - 1);
        int pos2 = helper2(intervals, newInterval, 0, intervals.length - 1);
        System.out.println(pos1);
        System.out.println(pos2);
        if (pos1 == 0 && intervals[pos1][0] > newInterval[1]) {
            int[][] res = new int[intervals.length + 1][2];
            res[0] = newInterval;
            for (int i = 1; i < res.length; i++)
                res[i] = intervals[i - 1];
            return res;
        }
        if (pos2 == intervals.length - 1 && intervals[pos2][1] < newInterval[0]) {
            int[][] res = new int[intervals.length + 1][2];
            for (int i = 0; i < intervals.length; i++)
                res[i] = intervals[i];
            res[intervals.length] = newInterval;
            return res;
        }

        int[][] temp = new int[intervals.length + 1][2];
        int counter;
        for (counter = 0; counter < pos1 - 1; counter++) {
            temp[counter] = intervals[counter];
        }
        if (intervals[Math.max(pos1 - 1, 0)][1] >= newInterval[0] && intervals[pos2][0] <= newInterval[1]) {
            temp[counter] = new int[]{intervals[Math.max(pos1 - 1, 0)][0], intervals[pos2][1]};
            counter += 2 + pos2 - pos1;
        }
        if (intervals[Math.max(pos1 - 1, 0)][1] >= newInterval[0]) {
            temp[counter] = new int[]{intervals[Math.max(pos1 - 1, 0)][0], newInterval[1]};
            temp[counter++] = intervals[pos1];
            counter += pos2 - pos1;
        }
        if (intervals[pos2][0] <= newInterval[1]) {
            temp[++counter] = intervals[pos1 - 1];
            temp[counter] = new int[]{newInterval[0], intervals[pos2][1]};
        }
        temp[pos1] = new int[]{Math.min(intervals[pos1][0], newInterval[0]),
                Math.max(intervals[pos2][1], newInterval[1])};

        for (int i = pos1 + 1; i < temp.length; i++) {
            temp[i] = intervals[i + pos2 - pos1];
        }
        return temp;
    }

    public int helper1(int[][] intervals, int[] newInterval, int start, int end) {
        int mid = 0;
        while (start < end) {
            mid = (start + end) / 2;
            int toCompare = intervals[mid][0];
            if (toCompare == newInterval[0]) return mid - 1;
            if (toCompare > newInterval[0])
                end = mid - 1;
            else
                start = mid + 1;
        }
        return start;
    }

    public int helper2(int[][] intervals, int[] newInterval, int start, int end) {
        int mid = 0;
        while (start < end) {
            mid = (start + end) / 2;
            int toCompare = intervals[mid][1];
            if (toCompare == newInterval[1]) return mid + 1;
            if (toCompare > newInterval[1])
                end = mid - 1;
            else
                start = mid + 1;
        }
        return start;
    }

    public static void main(String[] args) {
        test t = new test();
//        System.out.println(Arrays.deepToString(
//                t.insert(new int[][]{new int[]{1, 2}, new int[]{3, 5}, new int[]{6, 7},
//                        new int[]{8, 10}, new int[]{12, 16}}, new int[]{4, 8})));
        System.out.println(Arrays.deepToString(
                t.insert(new int[][]{new int[]{1, 5}, new int[]{6, 8}}, new int[]{5, 6})));
    }
}
