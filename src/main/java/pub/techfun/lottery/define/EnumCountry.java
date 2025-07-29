package pub.techfun.lottery.define;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.define.china.EnumLotteryTypeChina;
import pub.techfun.lottery.define.vietnam.EnumLotteryTypeVietnam;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumCountry {

    China(1, "中国", EnumLotteryTypeChina.getLotteryTypeMap()),
    Vietnam(2, "越南", EnumLotteryTypeVietnam.getLotteryTypeMap()),
    ;

    final Integer code;
    final String name;
    final Map<Integer, LotteryType> lotteryTypeMap;

    private static final Map<Integer, EnumCountry> COUNTRY_MAP = new LinkedHashMap<>();

    static {
        for (EnumCountry item : EnumCountry.values()) {
            COUNTRY_MAP.put(item.getCode(), item);
        }
    }

    public static Map<Integer, LotteryType> getLotteryTypeMap(Integer countryCode) {
        return COUNTRY_MAP.get(countryCode).getLotteryTypeMap();
    }

}
