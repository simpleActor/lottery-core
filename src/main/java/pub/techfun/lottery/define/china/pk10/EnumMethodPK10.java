package pub.techfun.lottery.define.china.pk10;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.define.LotteryMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumMethodPK10 implements LotteryMethod {

    ;

    final Integer code;
    final String name;
    final Calculator calculator;

    private static final Map<Integer, LotteryMethod> METHOD_MAP = new LinkedHashMap<>();

    static {
        for (EnumMethodPK10 item : EnumMethodPK10.values()) {
            METHOD_MAP.put(item.getCode(), item);
        }
    }

    public static Map<Integer, LotteryMethod> getLotteryMethodMap() {
        return METHOD_MAP;
    }

    public  static LotteryMethod get(Integer code) {
        return METHOD_MAP.get(code);
    }

}
