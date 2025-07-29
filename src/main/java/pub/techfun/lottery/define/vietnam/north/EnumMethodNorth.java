package pub.techfun.lottery.define.vietnam.north;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.define.LotteryMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumMethodNorth implements LotteryMethod {

    ;

    final Integer code;
    final String name;
    final Calculator calculator;

    private static final Map<Integer, LotteryMethod> METHOD_MAP = new LinkedHashMap<>();

    static {
        for (EnumMethodNorth item : EnumMethodNorth.values()) {
            METHOD_MAP.put(item.getCode(), item);
        }
    }

    public static Map<Integer, LotteryMethod> getLotteryMethodMap() {
        return METHOD_MAP;
    }

    public static LotteryMethod get(Integer code) {
        return METHOD_MAP.get(code);
    }

}
