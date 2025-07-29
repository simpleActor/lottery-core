package pub.techfun.lottery.util;

import java.util.List;

public class NiuNiuUtil {

    public static int niuNum(List<Integer> numList) {
        List<List<Integer>> lists = ACUtil.C(numList, 3);
        for (List<Integer> list : lists) {
            if (hasNiu(list)) {
                //差集
                List<Integer> subtractList = CollectionUtil.subtract(numList, list);
                return subtractList.stream().mapToInt(Integer::valueOf).sum() % 10;
            }
        }
        return -1;
    }

    private static boolean hasNiu(List<Integer> numList) {
        return numList.stream().mapToInt(Integer::intValue).sum() % 10 == 0;
    }

}
