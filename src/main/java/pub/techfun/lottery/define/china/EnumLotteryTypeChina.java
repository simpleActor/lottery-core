package pub.techfun.lottery.define.china;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.generator.WinNumberGenerator;
import pub.techfun.lottery.define.LotteryMethod;
import pub.techfun.lottery.define.LotteryType;
import pub.techfun.lottery.define.china.pk10.EnumMethodPK10;
import pub.techfun.lottery.define.china.ssc.EnumMethodSSC;
import pub.techfun.lottery.define.china.t11xuan5.EnumMethod11Xuan5;
import pub.techfun.lottery.impl.china.ssc.common.SSCWinNumberGroup;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumLotteryTypeChina implements LotteryType {

    SSC(1, "时时彩", SSCWinNumberGroup::generate, EnumMethodSSC.getLotteryMethodMap()),
    T_11_XUAN_5(2, "11选5", SSCWinNumberGroup::generate, EnumMethod11Xuan5.getLotteryMethodMap()),
    PK10(3, "PK10", SSCWinNumberGroup::generate, EnumMethodPK10.getLotteryMethodMap()),
    ;

    final Integer code;
    final String name;
    final WinNumberGenerator generator;
    final Map<Integer, LotteryMethod> methodMap;

    private static final Map<Integer, LotteryType> LOTTERY_TYPE_MAP = new LinkedHashMap<>();

    static {
        for (EnumLotteryTypeChina item : EnumLotteryTypeChina.values()) {
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
