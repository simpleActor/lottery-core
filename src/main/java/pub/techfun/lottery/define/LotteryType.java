package pub.techfun.lottery.define;

import pub.techfun.lottery.base.generator.WinNumberGenerator;

import java.util.Map;

/**
 * 彩种类型
 */
public interface LotteryType {

    Integer getCode();

    Map<Integer, LotteryMethod> getMethodMap();

    LotteryMethod getMethod(Integer code);

    WinNumberGenerator getGenerator();

}
