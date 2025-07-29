package pub.techfun.lottery.define.vietnam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.generator.WinNumberGenerator;
import pub.techfun.lottery.define.LotteryMethod;
import pub.techfun.lottery.define.LotteryType;
import pub.techfun.lottery.define.vietnam.middlesouth.EnumMethodMiddleSouth;
import pub.techfun.lottery.impl.vietnam.middlesouth.common.MiddleSouthWinNumberGroup;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumLotteryTypeVietnam implements LotteryType {

    MiddleSouth(1, "中南部彩", MiddleSouthWinNumberGroup::generate, EnumMethodMiddleSouth.getLotteryMethodMap()),
    ;

    final Integer code;
    final String name;
    final WinNumberGenerator generator;
    final Map<Integer, LotteryMethod> methodMap;

    private static final Map<Integer, LotteryType> LOTTERY_TYPE_MAP = new LinkedHashMap<>();

    static {
        for (EnumLotteryTypeVietnam item : EnumLotteryTypeVietnam.values()) {
            LOTTERY_TYPE_MAP.put(item.getCode(), item);
        }
    }

    public static Map<Integer, LotteryType> getLotteryTypeMap() {
        return LOTTERY_TYPE_MAP;
    }

    public static LotteryType get(Integer code) {
        return LOTTERY_TYPE_MAP.get(code);
    }

    @Override
    public LotteryMethod getMethod(Integer code) {
        return methodMap.get(code);
    }

}
