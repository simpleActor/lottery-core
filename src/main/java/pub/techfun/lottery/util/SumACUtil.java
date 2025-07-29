package pub.techfun.lottery.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 求和值的所有可能排列、组合数
 * 数字范围：[0-9]
 */
public class SumACUtil {

    /**
     * 获取n个数和值为s的所有排列结果
     *
     * @param n
     * @param s
     * @return
     */
    public static List<List<Integer>> getSumNListsA(int n, int s) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackA(n, s, new ArrayList<>(), result);
        return result;
    }

    // 回溯方法
    private static void backtrackA(int n, int remainingSum, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == n) {
            if (remainingSum == 0) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        // 剪枝：每位最多只能是 9
        for (int digit = 0; digit <= 9; digit++) {
            if (digit > remainingSum) break; // 剪枝：跳过不可能的情况
            current.add(digit);
            backtrackA(n, remainingSum - digit, current, result);
            current.removeLast(); // 回溯
        }
    }

    /**
     * 获取n个数和值为s的排列数
     *
     * @param n
     * @param s
     * @return
     */
    public static long getSumNCountA(int n, int s) {
        long total = 0;
        int maxK = s / 10;
        for (int k = 0; k <= maxK; k++) {
            long sign = (k % 2 == 0) ? 1 : -1;
            long term = ACUtil.countC(n, k) * ACUtil.countC(s - 10 * k + n - 1, n - 1);
            total += sign * term;
        }
        return total;
    }

    /**
     * 获取n个数和值为s的所有组合结果
     *
     * @param n
     * @param s
     * @return
     */
    public static List<List<Integer>> getSumNListsC(int n, int s) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackC(n, s, 1, new ArrayList<>(), result);
        return result;
    }

    private static void backtrackC(int n, int remainingSum, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == n) {
            if (remainingSum == 0) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        for (int i = start; i <= remainingSum; i++) {
            current.add(i);
            backtrackC(n, remainingSum - i, i, current, result); // 可重复使用 i，也可以换成 i + 1 表示不重复
            current.removeLast(); // 回溯
        }
    }

    /**
     * 获取n个数和值为s的组合数
     *
     * @param n
     * @param s
     * @return
     */
    public static long getSumNCountC(int n, int s) {
        if (s < n) return 0; // 不存在方案
        return processSumNCountC(s - 1, n - 1);
    }

    // 计算组合数 C(n, k)
    private static long processSumNCountC(int n, int k) {
        if (k < 0 || k > n) return 0;
        k = Math.min(k, n - k); // 利用对称性降低计算量
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getSumNListsC(3, 27));
        System.out.println(getSumNCountA(3, 15));
        System.out.println(getSumNCountA(3, 11));
    }

}
