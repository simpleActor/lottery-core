package pub.techfun.lottery.define;

import pub.techfun.lottery.base.calculator.Calculator;

/**
 * 玩法
 */
public interface LotteryMethod {

    Integer getCode();

    Calculator getCalculator();

}
