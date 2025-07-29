package pub.techfun.lottery.client;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.define.EnumCountry;
import pub.techfun.lottery.define.LotteryMethod;
import pub.techfun.lottery.define.LotteryType;
import pub.techfun.lottery.define.china.EnumLotteryTypeChina;
import pub.techfun.lottery.define.china.ssc.EnumMethodSSC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LotteryJudgeManager {

    public static WinResultGroup winJudge(EnumCountry country, LotteryType lotteryType, LotteryMethod method, String winNumber, String bet) {
        return winJudge(country.getCode(), lotteryType.getCode(), method.getCode(), winNumber, bet);
    }

    public static WinResultGroup winJudge(EnumCountry country, LotteryType lotteryType, LotteryMethod method, String winNumber, String bet, BetFormatter formatter) {
        return winJudge(country.getCode(), lotteryType.getCode(), method.getCode(), winNumber, bet, formatter);
    }

    public static WinResultGroup winJudge(Integer countryCode, Integer lotteryTypeCode, Integer methodCode, String winNumber, String bet) {
        Calculator calculator = getCalculator(countryCode, lotteryTypeCode, methodCode);
        if (calculator == null) {
            throw new RuntimeException("未找到对应玩法");
        }
        return calculator.winJudge(winNumber, bet);
    }

    public static WinResultGroup winJudge(Integer countryCode, Integer lotteryTypeCode, Integer methodCode, String winNumber, String bet, BetFormatter formatter) {
        Calculator calculator = getCalculator(countryCode, lotteryTypeCode, methodCode);
        if (calculator == null) {
            throw new RuntimeException("未找到对应玩法");
        }
        return calculator.winJudge(winNumber, bet, formatter);
    }

    public static Map<Integer, LotteryType> getLotteryTypeMap(EnumCountry country) {
        return EnumCountry.getLotteryTypeMap(country.getCode());
    }

    public static Map<Integer, LotteryType> getLotteryTypeMap(Integer countryCode) {
        return EnumCountry.getLotteryTypeMap(countryCode);
    }

    public static Map<Integer, LotteryMethod> getLotteryMethodMap(EnumCountry country, LotteryType lotteryType) {
        return EnumCountry.getLotteryTypeMap(country.getCode()).get(lotteryType.getCode()).getMethodMap();
    }

    public static Map<Integer, LotteryMethod> getLotteryMethodMap(Integer countryCode, Integer lotteryTypeCode) {
        return EnumCountry.getLotteryTypeMap(countryCode).get(lotteryTypeCode).getMethodMap();
    }

    public static Calculator getCalculator(EnumCountry country, LotteryType lotteryType, LotteryMethod method) {
        return EnumCountry.getLotteryTypeMap(country.getCode()).get(lotteryType.getCode()).getMethod(method.getCode()).getCalculator();
    }

    public static Calculator getCalculator(Integer countryCode, Integer lotteryTypeCode, Integer methodCode) {
        return EnumCountry.getLotteryTypeMap(countryCode).get(lotteryTypeCode).getMethod(methodCode).getCalculator();
    }

    public static void main(String[] args) {
        Calculator calculator = getCalculator(EnumCountry.China, EnumLotteryTypeChina.SSC, EnumMethodSSC.WU_XING_ZHI_XUAN_FU_SHI);
        System.out.println(calculator.getClass().getName());
        int betCount = 1000;
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        List<BetRegion> betList = new ArrayList<>();
        RegionParam regionParam = new RegionParam(5, 5, true);
        SectionParam sectionParam = new SectionParam(0, 9, 1, 10, false, false);
        for (int i = 0; i < betCount; i++) {
            betList.add(RandomBetGenerator.generate(regionParam, sectionParam));
        }
        long start = System.currentTimeMillis();
        List<CompletableFuture<WinResultGroup>> futures = new ArrayList<>();
        for (BetRegion bet : betList) {
            futures.add(CompletableFuture.supplyAsync(() -> calculator.winJudge("1,2,3,4,5", bet.toString()), executor));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            List<WinResultGroup> resultList = futures.stream().map(CompletableFuture::join).toList();
            System.out.println(JSON.toJSONString(resultList));
            System.out.printf("测试注单数:%s, 耗时:%s毫秒", betCount, (System.currentTimeMillis() - start));
        }).join();
    }

}
