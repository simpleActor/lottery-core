package pub.techfun.lottery.impl.china.ssc;

import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.bet.BetNumSection;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.generator.FuShiCombinationGenerator;
import pub.techfun.lottery.util.ACUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CoverUtil {

    /**
     * 生成5星全覆盖号码列表
     *
     * @param indexes     固定索引
     * @param sectionList 原号码列表
     * @return 五星号码列表，格式 List<List<Integer>>
     */
    public static List<BetSection> getFullCover(int[] indexes, List<BetSection> sectionList) {
        if (indexes.length == 0) {
            throw new IllegalArgumentException("固定索引不能为空");
        }
        int fixedLen = indexes.length;
        List<BetSection> allResults = new ArrayList<>();
        for (BetSection section : sectionList) {
            processGenerate(indexes, section.getNumList(), fixedLen, allResults);
        }
        return allResults;
    }

    public static List<BetSection> getFullCoverByNumLists(int[] indexes, List<List<Integer>> numLists) {
        if (indexes.length == 0) {
            throw new IllegalArgumentException("固定索引不能为空");
        }
        int fixedLen = indexes.length;
        List<BetSection> allResults = new ArrayList<>();
        for (List<Integer> smallNum : numLists) {
            if (smallNum.size() != fixedLen) {
                throw new IllegalArgumentException("号码长度和固定索引长度不匹配");
            }
            processGenerate(indexes, smallNum, fixedLen, allResults);
        }
        return allResults;
    }

    private static void processGenerate(int[] indexes, List<Integer> numList, int fixedLen, List<BetSection> allResults) {
        // 初始化号码模板，-1 表示待填充
        int[] base = new int[5];
        for (int i = 0; i < 5; i++) base[i] = -1;

        for (int i = 0; i < fixedLen; i++) {
            base[indexes[i]] = numList.get(i);
        }

        // 用队列迭代完成剩余位全遍历
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(base);

        for (int pos = 0; pos < 5; pos++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                assert cur != null;
                if (cur[pos] != -1) {
                    queue.offer(cur);
                } else {
                    for (int d = 0; d <= 9; d++) {
                        int[] next = cur.clone();
                        next[pos] = d;
                        queue.offer(next);
                    }
                }
            }
        }

        // 转List<Integer>，批量加入结果
        while (!queue.isEmpty()) {
            int[] numArr = queue.poll();
            List<Integer> newNumList = new ArrayList<>(5);
            for (int d : numArr) newNumList.add(d);
            allResults.add(new BetNumSection(newNumList));
        }
    }

    public static List<List<Integer>> generateCombinations(int length) {
        List<List<Integer>> result = new ArrayList<>();
        int total = (int) Math.pow(10, length);

        for (int i = 0; i < total; i++) {
            List<Integer> digits = new ArrayList<>(length);
            int value = i;

            // 先填充所有位
            for (int j = 0; j < length; j++) {
                digits.add(0); // 先加0占位
            }

            // 从最后一位开始填
            int index = length - 1;
            while (index >= 0 && value > 0) {
                digits.set(index, value % 10);
                value /= 10;
                index--;
            }

            result.add(digits);
        }

        return result;
    }

    // 测试
    public static void main(String[] args) {
        int[] indexes = {0, 3, 4}; // 固定前三位
        List<List<Integer>> numLists = new ArrayList<>();
        BetNumRegion region = new BetNumRegion("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
        List<BetSection> sectionList = FuShiCombinationGenerator.generateAll(region.getSectionList());
        sectionList.forEach(section -> {
            numLists.add(section.getNumList());
        });
        long start = System.currentTimeMillis();
        List<BetSection> results = getFullCoverByNumLists(indexes, numLists);
        long end = System.currentTimeMillis();

        System.out.println("生成号码总数: " + results.size());
        System.out.println("耗时(ms): " + (end - start));
        // 打印前10个
        for (int i = 0; i < Math.min(10, results.size()); i++) {
            System.out.println(results.get(i));
        }

        System.out.println(generateCombinations(5).size());
        System.out.println(ACUtil.countA(36, 8));

    }

}
