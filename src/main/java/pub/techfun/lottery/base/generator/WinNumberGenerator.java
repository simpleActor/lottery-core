package pub.techfun.lottery.base.generator;

import pub.techfun.lottery.base.winnumber.WinNumberGroup;

@FunctionalInterface
public interface WinNumberGenerator {

    WinNumberGroup generate();

}
