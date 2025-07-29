package pub.techfun.lottery.base.winnumber;

import pub.techfun.lottery.base.Level;

import java.util.List;

public interface WinNumber {

    /**
     * 添加数字
     * @param num
     * @return
     */
    WinNumber add(Integer num);

    /**
     * 添加数字
     * @param numList
     * @return
     */
    WinNumber addAll(List<Integer> numList);

    /**
     * 清理
     */
    void clear();

    /**
     * 大小
     * @return
     */
    int size();

    /**
     * 获取指定索引位置的数字
     * @return
     */
    Integer get(int index);

    /**
     * 数字列表
     * @return
     */
    List<Integer> getNumList();

    /**
     * 奖级
     * @return
     */
    Level level();

}
