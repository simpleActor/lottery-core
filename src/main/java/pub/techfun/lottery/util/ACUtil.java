package pub.techfun.lottery.util;


import java.util.*;

public class ACUtil {

    //--------------------------------------------------------------------------------------------- Arrangement

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n 总数
     * @param m 选择的个数
     * @return 排列数
     */
    public static long countA(int n, int m) {
        return Arrangement.count(n, m);
    }

    /**
     * 计算排列数，即A(n, n) = n!
     *
     * @param n 总数
     * @return 排列数
     */
    public static long countA(int n) {
        return Arrangement.count(n);
    }

    /**
     * 排列选择（从列表中选择n个排列）
     *
     * @param datas 待选列表
     * @param m     选择个数
     * @return 所有排列列表
     */
    public static List<List<Integer>> A(List<Integer> datas, int m) {
        List<Integer[]> list = new Arrangement(datas.toArray(new Integer[]{})).select(m);
        return toList(list);
    }

    /**
     * 全排列选择（列表全部参与排列）
     *
     * @param datas 待选列表
     * @return 所有排列列表
     */
    public static List<List<Integer>> A(List<Integer> datas) {
        List<Integer[]> list = new Arrangement(datas.toArray(new Integer[]{})).select();
        return toList(list);
    }

    //--------------------------------------------------------------------------------------------- Combination

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n 总数
     * @param m 选择的个数
     * @return 组合数
     */
    public static long countC(int n, int m) {
        return Combination.count(n, m);
    }

    /**
     * 组合选择（从列表中选择n个组合）
     *
     * @param datas 待选列表
     * @param m     选择个数
     * @return 所有组合列表
     */
    public static List<List<Integer>> C(List<Integer> datas, int m) {
        List<Integer[]> list = new Combination(datas.toArray(new Integer[]{})).select(m);
        return toList(list);
    }

    private static List<List<Integer>> toList(List<Integer[]> list) {
        List<List<Integer>> result = new ArrayList<>();
        list.forEach(array -> {
            result.add(Arrays.stream(array).toList());
        });
        return result;
    }

    public static List<List<Integer>> uniqueList(List<List<Integer>> list) {
        return list.stream().distinct().toList();
    }

    public static Set<List<Integer>> uniqueSet(List<List<Integer>> list) {
        return new LinkedHashSet<>(list);
    }

    public static void main(String[] args) {
        System.out.println(countC(10, 5));
        System.out.println(C(List.of(0, 1, 2, 3, 4), 5));
        System.out.println(countA(5, 5));
        System.out.println(A(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0), 5).stream().distinct().toList().size());
    }

}
