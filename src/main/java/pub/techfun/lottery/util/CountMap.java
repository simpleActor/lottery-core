package pub.techfun.lottery.util;

import java.util.LinkedHashMap;

public class CountMap extends LinkedHashMap<Integer, NumSet> {

    public NumSet getByCount(int count) {
        return get(count);
    }

}
