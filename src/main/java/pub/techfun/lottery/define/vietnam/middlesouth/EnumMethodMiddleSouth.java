package pub.techfun.lottery.define.vietnam.middlesouth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.define.LotteryMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumMethodMiddleSouth implements LotteryMethod {

    HOU_ER_ZHI_XUAN_DAN_SHI(1, "后二直选单式", new pub.techfun.lottery.impl.vietnam.middlesouth.method.houer.zhixuan.danshi.CalculatorImpl()),
    ;

    final Integer code;
    final String name;
    final Calculator calculator;

    private static final Map<Integer, LotteryMethod> METHOD_MAP = new LinkedHashMap<>();

    static {
        for (EnumMethodMiddleSouth item : EnumMethodMiddleSouth.values()) {
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
