package pub.techfun.lottery.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 已知n个数差值为diff，求排列数
 * 注意：数字范围是[0-9]
 */
public class SubACUtil {

    public static List<List<Integer>> getSubNListsA(int n, int diff) {
        // 限制在 0–9
        int minVal = 0, maxVal = 9;
        return getSubNListsA(n, diff, minVal, maxVal);
    }

    private static List<List<Integer>> getSubNListsA(int n, int diff, int minVal, int maxVal) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(n, diff, new ArrayList<>(), res, minVal, maxVal, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return res;
    }

    private static void backtrack(int n, int diff, List<Integer> curr, List<List<Integer>> res,
                                  int minVal, int maxVal, int currMin, int currMax, int limit) {
        if (res.size() >= limit) return;

        if (curr.size() == n) {
            if (currMax - currMin == diff) {
                res.add(new ArrayList<>(curr));
            }
            return;
        }

        for (int i = minVal; i <= maxVal; i++) {
            int nextMin = Math.min(currMin, i);
            int nextMax = Math.max(currMax, i);

            if (nextMax - nextMin > diff) continue;

            curr.add(i);
            backtrack(n, diff, curr, res, minVal, maxVal, nextMin, nextMax, limit);
            curr.removeLast();
        }
    }


    public static long getSubNCountC(int n, int diff) {
        if (diff < 0 || diff > 9 || n < 2) return 0;

        long part1 = pow(diff + 1, n);
        long part2 = 2 * pow(diff, n);
        long part3 = (diff - 1 >= 0) ? pow(diff - 1, n) : 0;

        long perGroup = part1 - part2 + part3;
        long groupCount = 10 - diff;

        return perGroup * groupCount;
    }

    public static long pow(int base, int exponent) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getSubNCountC(2, 5));
        System.out.println(getSubNListsA(3, 1));
    }

}
