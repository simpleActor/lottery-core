package pub.techfun.lottery.impl.china.liuhecai.common;

import pub.techfun.lottery.base.Color;
import pub.techfun.lottery.base.consts.EnumColor;

import java.util.HashMap;
import java.util.Map;

public class LiuHeCaiColorMapping {

    private final static Map<Integer, Color> map = new HashMap<>();

    static {
        map.put(1, EnumColor.RED);
        map.put(2, EnumColor.RED);
        map.put(3, EnumColor.BLUE);
        map.put(4, EnumColor.BLUE);
        map.put(5, EnumColor.GREEN);
        map.put(6, EnumColor.GREEN);
        map.put(7, EnumColor.RED);
        map.put(8, EnumColor.RED);
        map.put(9, EnumColor.BLUE);
        map.put(10, EnumColor.BLUE);

        map.put(11, EnumColor.GREEN);
        map.put(12, EnumColor.RED);
        map.put(13, EnumColor.RED);
        map.put(14, EnumColor.BLUE);
        map.put(15, EnumColor.BLUE);
        map.put(16, EnumColor.GREEN);
        map.put(17, EnumColor.GREEN);
        map.put(18, EnumColor.RED);
        map.put(19, EnumColor.RED);
        map.put(20, EnumColor.BLUE);

        map.put(21, EnumColor.GREEN);
        map.put(22, EnumColor.GREEN);
        map.put(23, EnumColor.RED);
        map.put(24, EnumColor.RED);
        map.put(25, EnumColor.BLUE);
        map.put(26, EnumColor.BLUE);
        map.put(27, EnumColor.GREEN);
        map.put(28, EnumColor.GREEN);
        map.put(29, EnumColor.RED);
        map.put(30, EnumColor.RED);

        map.put(31, EnumColor.BLUE);
        map.put(32, EnumColor.GREEN);
        map.put(33, EnumColor.GREEN);
        map.put(34, EnumColor.RED);
        map.put(35, EnumColor.RED);
        map.put(36, EnumColor.BLUE);
        map.put(37, EnumColor.BLUE);
        map.put(38, EnumColor.GREEN);
        map.put(39, EnumColor.GREEN);
        map.put(40, EnumColor.RED);

        map.put(41, EnumColor.BLUE);
        map.put(42, EnumColor.BLUE);
        map.put(43, EnumColor.GREEN);
        map.put(44, EnumColor.GREEN);
        map.put(45, EnumColor.RED);
        map.put(46, EnumColor.RED);
        map.put(47, EnumColor.BLUE);
        map.put(48, EnumColor.BLUE);
        map.put(49, EnumColor.GREEN);
    }

    public static Color get(Integer num) {
        return map.get(num);
    }

}
