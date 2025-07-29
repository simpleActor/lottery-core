package pub.techfun.lottery.base.result;

import pub.techfun.lottery.base.Level;

public interface WinResult {

    /**
     * 是否打和
     * @return
     */
    boolean tie();

    /**
     * 是否中奖
     * @return
     */
    boolean win();

    /**
     * 中奖次数
     * @return
     */
    int winCount();

    /**
     * 赔率
     * @return
     */
    double odds();

    /**
     * 奖级
     * @return
     */
    Level level();

    void setOdds(double odds);

    void setTie(boolean tie);

    void setWinCount(int winCount);

    void setLevel(Level level);

}
