package pub.techfun.lottery.algorithm.client;

import pub.techfun.lottery.algorithm.AlgorithmCalculator;
import pub.techfun.lottery.algorithm.AlgorithmResult;
import pub.techfun.lottery.algorithm.key.AlgorithmCalculatorKey;
import pub.techfun.lottery.algorithm.key.AlgorithmConfigKey;
import pub.techfun.lottery.algorithm.key.LotteryCalculatorKey;
import pub.techfun.lottery.algorithm.param.BetParam;
import pub.techfun.lottery.algorithm.param.ConfigParam;
import pub.techfun.lottery.algorithm.param.WinNumberGetParam;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.define.EnumCountry;
import pub.techfun.lottery.define.china.EnumLotteryTypeChina;
import pub.techfun.lottery.define.china.ssc.EnumMethodSSC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AlgorithmManager {

    private static final Map<LotteryCalculatorKey, AlgorithmCalculatorHolder> MAP = new ConcurrentHashMap<>();

    private static final Map<AlgorithmConfigKey, ConfigParam> CONFIG_MAP = new ConcurrentHashMap<>();

    public static synchronized void initConfig(AlgorithmConfigKey key, ConfigParam config) {
        CONFIG_MAP.put(key, config);
    }

    public static synchronized AlgorithmResult getResult(WinNumberGetParam param) {
        LotteryCalculatorKey key1 = new LotteryCalculatorKey(param.getCountryCode(), param.getLotteryTypeCode(), param.getMethodCode());
        Map<AlgorithmCalculatorKey, AlgorithmCalculator> holder = MAP.get(key1);
        if (holder == null) {
            throw new RuntimeException("LotteryCalculator not exists");
        }
        AlgorithmCalculatorKey key2 = new AlgorithmCalculatorKey(param.getLotteryId(), param.getIssue());
        AlgorithmCalculator calculator = holder.get(key2);
        if (calculator == null) {
            throw new RuntimeException("AlgorithmCalculator not exists");
        }
        AlgorithmResult result = calculator.getResult();
        holder.remove(key2);
        return result;
    }

    public static synchronized WinNumberGroup getWinNUmber(WinNumberGetParam param) {
        return getResult(param).getWinNumber();
    }

    public static void receiveBet(BetParam bet) {
        LotteryCalculatorKey key1 = newLotteryCalculatorKey(bet);
        AlgorithmCalculatorKey key2 = newAlgorithmCalculatorKey(bet);
        AlgorithmCalculatorHolder holder = MAP.computeIfAbsent(key1, k -> new AlgorithmCalculatorHolder());
        AlgorithmCalculator calculator = getAlgorithmCalculator(holder, key2, bet);
        calculator.calculate(key1, bet.getBet());
    }

    public static void receiveBet(List<BetParam> betList) {
        BetParam betParam = betList.getFirst();
        betList.forEach(bet -> {
            if(!betParam.getCountryCode().equals(bet.getCountryCode())) {
                throw new RuntimeException("Country code mismatch");
            }
            if(!betParam.getLotteryTypeCode().equals(bet.getLotteryTypeCode())) {
                throw new RuntimeException("Lottery type code mismatch");
            }
            if(!betParam.getLotteryId().equals(bet.getLotteryId())) {
                throw new RuntimeException("Lottery id mismatch");
            }
            if(!betParam.getIssue().equals(bet.getIssue())) {
                throw new RuntimeException("Issue mismatch");
            }
        });
        Map<Integer, List<BetParam>> groupByMap = betList.stream().collect(Collectors.groupingBy(BetParam::getMethodCode));
        groupByMap.forEach((k, v) -> {
            LotteryCalculatorKey key1 = newLotteryCalculatorKey(v.getFirst());
            AlgorithmCalculatorKey key2 = newAlgorithmCalculatorKey(v.getFirst());
            AlgorithmCalculatorHolder holder = MAP.computeIfAbsent(key1, key -> new AlgorithmCalculatorHolder());
            AlgorithmCalculator calculator = getAlgorithmCalculator(holder, key2, v.getFirst());
            List<String> betStrList = v.stream().map(BetParam::getBet).toList();
            calculator.calculate(key1, betStrList);
        });
    }

    private static AlgorithmCalculatorKey newAlgorithmCalculatorKey(BetParam bet) {
        return new AlgorithmCalculatorKey(bet.getLotteryId(), bet.getIssue());
    }

    private static LotteryCalculatorKey newLotteryCalculatorKey(BetParam bet) {
        return new LotteryCalculatorKey(bet.getCountryCode(), bet.getLotteryTypeCode(), bet.getMethodCode());
    }

    private static AlgorithmCalculator getAlgorithmCalculator(AlgorithmCalculatorHolder holder, AlgorithmCalculatorKey key, BetParam betParam) {
        ConfigParam config = getConfig(betParam);
        return holder.computeIfAbsent(key, k ->
                new AlgorithmCalculator(betParam.getCountryCode(), betParam.getLotteryTypeCode(), config));
    }

    private static ConfigParam getConfig(BetParam param) {
        AlgorithmConfigKey configKey = new AlgorithmConfigKey(param.getCountryCode(), param.getLotteryTypeCode(), param.getLotteryId());
        return CONFIG_MAP.computeIfAbsent(configKey, k -> new ConfigParam());
    }

    public static void main(String[] args) {
        int betCount = 1000;
        String lotteryId = "tenant:1|platform:1|lotteryId:1";
        String issue = "20250419001";
        List<BetRegion> betList = new ArrayList<>();
        RegionParam regionParam = new RegionParam(5, 5, true);
        SectionParam sectionParam = new SectionParam(0, 9, 1, 10, false, false);
        for (int i = 0; i < betCount; i++) {
            betList.add(RandomBetGenerator.generate(regionParam, sectionParam));
        }
        List<BetParam> betParamList = new ArrayList<>();
        for (BetRegion bet : betList) {
            BetParam betParam = new BetParam(EnumCountry.China, EnumLotteryTypeChina.SSC, EnumMethodSSC.WU_XING_ZHI_XUAN_FU_SHI,
                    lotteryId, issue, bet.toString());
            betParamList.add(betParam);
        }
        long start = System.currentTimeMillis();
        receiveBet(betParamList);
        WinNumberGetParam param = new WinNumberGetParam(EnumCountry.China, EnumLotteryTypeChina.SSC, EnumMethodSSC.WU_XING_ZHI_XUAN_FU_SHI,
                lotteryId, issue);
        WinNumberGroup winNumber = getWinNUmber(param);
        long end = System.currentTimeMillis();
        System.out.printf("测试注单数:%s, 耗时:%s毫秒, winNumber:%s", betCount, (end - start), winNumber);
    }

    static class AlgorithmCalculatorHolder extends ConcurrentHashMap<AlgorithmCalculatorKey, AlgorithmCalculator> {
    }

}
