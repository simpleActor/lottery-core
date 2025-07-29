package pub.techfun.lottery.base.bet;

import java.util.List;

public interface BetRegion {

    /**
     * 获取按位分割列表
     * @return
     */
    List<BetSection> getSectionList();

    /**
     * 添加
     * @param section
     * @return
     */
    BetRegion add(BetSection section);

    /**
     * 添加
     * @param sectionList
     * @return
     */
    BetRegion addAll(List<BetSection> sectionList);

    /**
     * 清理
     */
    BetRegion clear();

    /**
     * 获取按位分割列表长度
     * @return
     */
    int size();

    /**
     * 获取指定位的实体
     * @param i
     * @return
     */
    BetSection get(int i);

    /**
     * 包含
     * @param section
     * @return
     */
    boolean contains(BetSection section);

    /**
     * 包含
     * @param sections
     * @return
     */
    boolean contains(BetSection... sections);

    /**
     * 包含
     * @param sectionList
     * @return
     */
    boolean contains(List<BetSection> sectionList);

    /**
     * 包含并且位置相同
     * 注意：这里位置相同会匹配 Section 内部的数字位置也要相同
     * @param section
     * @return
     */
    boolean containsPosMatch(BetSection section);

    /**
     * 包含并且位置相同
     * 注意：这里位置相同会匹配 Section 内部的数字位置也要相同
     * @param sections
     * @return
     */
    boolean containsPosMatch(BetSection... sections);

    /**
     * 包含并且位置相同
     * 注意：这里位置相同会匹配 Section 内部的数字位置也要相同
     * @param sectionList
     * @return
     */
    boolean containsPosMatch(List<BetSection> sectionList);

}
