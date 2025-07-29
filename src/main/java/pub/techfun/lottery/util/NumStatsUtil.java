package pub.techfun.lottery.util;

import pub.techfun.lottery.base.consts.EnumForm;

import java.util.*;

/**
 * 数字形态、次数统计工具类
 */
public class NumStatsUtil {

    /**
     * 获取数字在list中的次数
     *
     * @param numList
     * @param num
     * @return
     */
    public static int getCount(List<Integer> numList, Integer num) {
        return (int) numList.stream().filter(a -> a.equals(num)).count();
    }

    /**
     * 统计数字出现的次数
     */
    public static Map<Integer, Integer> getCountStats(List<Integer> numList) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        numList.forEach(num -> map.put(num, map.getOrDefault(num, 0) + 1));
        return map;
    }

    public static CountMap getCountMap(List<Integer> numList) {
        Map<Integer, Integer> map = getCountStats(numList);
        return getCountMap(map);
    }

    public static CountMap getCountMap(Map<Integer, Integer> map) {
        CountMap countMap = new CountMap();
        map.forEach((k, v) -> {
            countMap.putIfAbsent(v, new NumSet());
            countMap.get(v).add(k);
        });
        return countMap;
    }

    /**
     * 形态统计
     */
    public static FormMap getFormStats(List<Integer> numList) {
        FormMap formMap = new FormMap();
        numList.forEach(num -> {
            if (num > 4) {
                formMap.put(EnumForm.BIG, formMap.getOrDefault(EnumForm.BIG, 0) + 1);
            } else {
                formMap.put(EnumForm.SMALL, formMap.getOrDefault(EnumForm.SMALL, 0) + 1);
            }
            if (num % 2 != 0) {
                formMap.put(EnumForm.ODD, formMap.getOrDefault(EnumForm.ODD, 0) + 1);
            } else {
                formMap.put(EnumForm.EVEN, formMap.getOrDefault(EnumForm.EVEN, 0) + 1);
            }
        });
        return formMap;
    }

}
