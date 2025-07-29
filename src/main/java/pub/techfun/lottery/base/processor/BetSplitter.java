package pub.techfun.lottery.base.processor;

import pub.techfun.lottery.base.result.BetSplitResult;


/**
 * 注单拆单处理器
 */
@FunctionalInterface
public interface BetSplitter {

    BetSplitResult split(String bet);

}
