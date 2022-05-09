package proc;

import processing.core.PApplet;

import java.sql.SQLOutput;
import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class test {

    public static int countTexts(String pressedKeys) {
        long[] dp = new long[pressedKeys.length() + 1];
        int mod = (int)Math.pow(10, 9) + 7;
        dp[0] = 1;
        for (int i = 1; i < pressedKeys.length() + 1; i++) {
            dp[i] = dp[i - 1] % mod;
            if (i - 2 >= 0 && pressedKeys.charAt(i - 1) == pressedKeys.charAt(i - 2)) {
                dp[i] = (dp[i] + dp[i - 2]) % mod;
                if (i - 3 >= 0 && pressedKeys.charAt(i - 1) == pressedKeys.charAt(i - 3)) {
                    dp[i] = (dp[i] + dp[i - 3]) % mod;
                    if ("79".indexOf(pressedKeys.charAt(i - 1)) != -1 &&
                    i - 4 >= 0 && pressedKeys.charAt(i - 1) == pressedKeys.charAt(i - 4)) {
                        dp[i] = (dp[i] + dp[i - 4]) % mod;
                    }
                }
            }
        }
        return (int)dp[dp.length - 1];
    }

    public static void main(String[] args) {
        System.out.println(countTexts("22233"));
    }
}
