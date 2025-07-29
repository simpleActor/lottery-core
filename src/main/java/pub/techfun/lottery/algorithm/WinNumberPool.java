package pub.techfun.lottery.algorithm;

import lombok.extern.slf4j.Slf4j;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.define.EnumCountry;
import pub.techfun.lottery.define.LotteryType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WinNumberPool {

    private static final Map<String, WinNumberGroupWrapper> WIN_NUMBER_POOL = new ConcurrentHashMap<>();
    private static final int initSize = 30000;

    static {
        init();
    }

    public static void init() {
        for (EnumCountry country : EnumCountry.values()) {
            for (LotteryType lotteryType : country.getLotteryTypeMap().values()) {
                WinNumberGroupWrapper wrapper = new WinNumberGroupWrapper(lotteryType, initSize);
                WIN_NUMBER_POOL.put(country.getCode() + ":" + lotteryType.getCode(), wrapper);
            }
        }
    }

    public static List<WinNumberGroup> getWinNumberList(Integer countryCode, Integer lotteryTypeCode, int count) {
        return WIN_NUMBER_POOL.get(countryCode + ":" + lotteryTypeCode).get(count);
    }

    static class WinNumberGroupWrapper {

        private final LinkedList<WinNumberGroup> winNumberList = new LinkedList<>();
        private final int initSize;
        private final LotteryType lotteryType;
        private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        public WinNumberGroupWrapper(LotteryType lotteryType, int initSize) {
            this.lotteryType = lotteryType;
            this.initSize = initSize;
            init(initSize);
        }

        private void init(int initSize) {
            for (int i = 0; i < initSize; i++) {
                add(lotteryType.getGenerator().generate());
            }
            startGenerator();
        }

        private void startGenerator() {
            scheduler.scheduleAtFixedRate(() -> add(lotteryType.getGenerator().generate()), 0, 1, TimeUnit.SECONDS);
        }

        synchronized List<WinNumberGroup> get(int count) {
            if (winNumberList.size() < count) {
                init(90);
            }
            Collections.shuffle(winNumberList);
            Set<WinNumberGroup> set = new LinkedHashSet<>();
            int x = 60;
            while (set.size() < count && x > 0) {
                set.add(winNumberList.pop());
                x--;
            }
            if(set.size() < count) {
                log.warn("winNumber repeat, real winNumber count={}", set.size());
            }
            return new ArrayList<>(set.stream().toList());
        }

        synchronized void add(WinNumberGroup winNumber) {
            if (winNumberList.size() < initSize) {
                winNumberList.add(winNumber);
            }
        }

        int size() {
            return winNumberList.size();
        }

    }

}
