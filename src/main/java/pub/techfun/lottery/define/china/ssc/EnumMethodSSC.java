package pub.techfun.lottery.define.china.ssc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.define.LotteryMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumMethodSSC implements LotteryMethod {

    WU_XING_ZHI_XUAN_DAN_SHI(1, "五星直选单式", new pub.techfun.lottery.impl.china.ssc.method.wuxing.zhixuan.danshi.CalculatorImpl()),
    WU_XING_ZHI_XUAN_FU_SHI(2, "五星直选复式", new pub.techfun.lottery.impl.china.ssc.method.wuxing.zhixuan.fushi.CalculatorImpl()),
    ;

    final Integer code;
    final String name;
    final Calculator calculator;

    private static final Map<Integer, LotteryMethod> METHOD_MAP = new LinkedHashMap<>();

    static {
        for (EnumMethodSSC item : EnumMethodSSC.values()) {
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
