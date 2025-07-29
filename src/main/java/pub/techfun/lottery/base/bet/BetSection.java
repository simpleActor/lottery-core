package pub.techfun.lottery.base.bet;

import java.util.Collection;
import java.util.List;

/**
 * 投注号码按位包装
 * 如：时时彩有5位，分别是；万|千|百|十|个，每一个位置分割为一个section
 */
public interface BetSection {

    /**
     * 获取数字列表
     * @return
     */
    List<Integer> getNumList();

    /**
     * 添加
     * @param num
     * @return
     */
    BetSection add(Integer num);

    /**
     * 添加
     * @param numList
     * @return
     */
    BetSection addAll(List<Integer> numList);

    /**
     * 清理
     */
    BetSection clear();

    /**
     * 获取数字个数
     * @return
     */
    int size();

    /**
     * 获取指定索引的数字
     * @param i
     * @return
     */
    Integer get(int i);

    /**
     * 包含
     * @param num
     * @return
     */
    boolean contains(Integer num);

    /**
     * 包含
     * @param nums
     * @return
     */
    boolean contains(Integer... nums);

    /**
     * 包含
     * @param numList
     * @return
     */
    boolean contains(Collection<Integer> numList);

    /**
     * 包含并且位置相同
     * @param num
     * @return
     */
    boolean containsPosMatch(Integer num);

    /**
     * 包含并且位置相同
     * @param nums
     * @return
     */
    boolean containsPosMatch(Integer... nums);

    /**
     * 包含并且位置相同
     * @param numList
     * @return
     */
    boolean containsPosMatch(List<Integer> numList);

}
