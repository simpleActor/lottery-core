package pub.techfun.lottery.base.bet;

import pub.techfun.lottery.base.result.BetCheckResult;

public interface BetWrapper {

    /**
     * 基础注单检测
     */
    void baseCheck(String bet);

    /**
     * 详细注单检测
     * @param bet
     */
    void check(String bet, BetCheckResult checkResult);

    /**
     * 是否有效
     * @return
     */
    boolean valid();

    /**
     * 错误信息
     * @return
     */
    String error();

    /**
     * 获取注单
     * @return
     */
    BetRegion getBet();

    /**
     * 获取注数
     * @return
     */
    long getCount();

    /**
     * 获取激活索引，这里指开奖号码的位置索引
     * @return
     */
    int[] getIndexes();

}
