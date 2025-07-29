package pub.techfun.lottery.base.processor;

import pub.techfun.lottery.base.result.BetCheckResult;

/**
 * 注单检查处理器
 */
@FunctionalInterface
public interface BetChecker {

    BetCheckResult check(String bet);

}
