package pub.techfun.lottery.base.calculator;

import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;

public interface Calculator<B extends BetWrapper, W extends WinNumberGroup> {

    /**
     * 覆盖的所有号码
     * @param bet
     * @return
     */
    default BetRegion cover(String bet) {
        return new BetNumRegion();
    }

    /**
     * 号码覆盖率
     * @param bet
     * @return
     */
    default double coverRate(String bet) {
        return 0;
    }

    /**
     * 中奖判断
     * @param winNumber
     * @param bet
     * @return
     */
    WinResultGroup winJudge(String winNumber, String bet);

    /**
     * 中奖判断
     * @param winNumber
     * @param bet
     * @param formatter
     * @return
     */
    WinResultGroup winJudge(String winNumber, String bet, BetFormatter formatter);

}
