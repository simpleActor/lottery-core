package pub.techfun.lottery.base.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.Level;

@Getter
@AllArgsConstructor
public enum EnumPrizeLevel implements Level {

    special(0, "特等奖"),
    one(1, "一等奖"),
    two(2, "二等奖"),
    three(3, "三等奖"),
    four(4, "四等奖"),
    five(5, "五等奖"),
    six(6, "六等奖"),
    seven(7, "七等奖"),
    eight(8, "八等奖"),
    nine(9, "九等奖"),
    ;

    final int value;
    final String name;

    @Override
    public int value() {
        return 0;
    }
}
