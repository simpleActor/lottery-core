package pub.techfun.lottery.impl.vietnam.middlesouth.common;

import lombok.Data;
import pub.techfun.lottery.base.Level;
import pub.techfun.lottery.base.result.WinResult;

@Data
public class MiddleSouthWinResult implements WinResult {

    /** 是否打和 */
    private boolean tie = false;
    /** 中奖次数 */
    private int winCount = 0;
    /** 赔率 */
    private double odds;
    /** 奖级 */
    private Level level;

    public MiddleSouthWinResult(double odds) {
        this.odds = odds;
    }

    @Override
    public boolean tie() {
        return tie;
    }

    @Override
    public boolean win() {
        return winCount > 0;
    }

    @Override
    public int winCount() {
        return winCount;
    }

    @Override
    public double odds() {
        return odds;
    }

    @Override
    public Level level() {
        return level;
    }

}
