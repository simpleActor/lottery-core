package pub.techfun.lottery.impl.china.ssc.method.sanxing.zuxuan.hezhi;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.result.WinResult;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.impl.china.ssc.CoverUtil;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractCalculator;
import pub.techfun.lottery.impl.china.ssc.common.SSCWinNumberGroup;
import pub.techfun.lottery.impl.china.ssc.common.SSCWinResult;
import pub.techfun.lottery.impl.china.ssc.consts.SSCConfig;
import pub.techfun.lottery.util.CountMap;
import pub.techfun.lottery.util.NumStatsUtil;
import pub.techfun.lottery.util.NumSet;
import pub.techfun.lottery.util.SumACUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    private static final double odds_zu3 = SSCConfig.ALL_FACTOR / 300;
    private static final double odds_zu6 = SSCConfig.ALL_FACTOR / 600;

    public CalculatorImpl(int... indexes) {
        super(indexes);
    }

    @Override
    public BetRegion cover(String bet) {
        List<List<Integer>> result = getSanXingCover(bet);
        List<BetSection> fullSectionList = CoverUtil.getFullCoverByNumLists(this.indexes, result);
        return new BetNumRegion().addAll(fullSectionList);
    }

    @Override
    public double coverRate(String bet) {
        List<List<Integer>> result = getSanXingCover(bet);
        return result.size() * 100 / SSCConfig.ALL_FACTOR;
    }

    private List<List<Integer>> getSanXingCover(String bet) {
        List<List<Integer>> lists = new ArrayList<>();
        BetWrapper betWrapper = newBetWrapper(bet);
        betWrapper.getBet().get(0).getNumList().forEach(sum -> {
            lists.addAll(SumACUtil.getSumNListsA(3, sum));
        });
        List<List<Integer>> result = lists.stream().filter(list -> {
            //过滤掉3个号码相同的组合
            CountMap countMap = NumStatsUtil.getCountMap(list);
            return countMap.getByCount(3) == null;
        }).peek(Collections::sort).distinct().toList();
        return result;
    }

    @Override
    protected SSCAbstractBetWrapper newBetWrapper(String bet) {
        return new BetImpl(bet);
    }

    @Override
    protected SSCAbstractBetWrapper newBetWrapper(String bet, BetFormatter formatter) {
        return new BetImpl(bet, formatter);
    }

    @Override
    protected List<WinResult> newWinResultList() {
        return List.of(new SSCWinResult(0));
    }

    @Override
    protected void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result) {
        int sum = winNumber.first().getNumList().stream().mapToInt(Integer::intValue).sum();
        CountMap countMap = NumStatsUtil.getCountMap(winNumber.first().getNumList());
        NumSet repeatNum = countMap.getByCount(2);
        NumSet singleNum = countMap.getByCount(1);
        if (singleNum != null && singleNum.size() == 3
                && betWrapper.getBet().get(0).contains(sum)) {
            //组6
            result.first().setOdds(odds_zu6);
            result.first().setWinCount(1);
            return;
        }
        if (repeatNum != null && repeatNum.size() == 1
                && singleNum != null && singleNum.size() == 1
                && betWrapper.getBet().get(0).contains(sum)) {
            //组3
            result.first().setOdds(odds_zu3);
            result.first().setWinCount(1);
        }
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl(0, 1, 2);
        int betCount = 1000;
        List<BetRegion> betList = new ArrayList<>();
        for (int i = 0; i < betCount; i++) {
            BetRegion bet = RandomBetGenerator.generate(BetImpl.REGION_CONSTRAINT, BetImpl.SECTION_CONSTRAINT);
            betList.add(bet);
        }
        long start = System.currentTimeMillis();
        for (BetRegion bet : betList) {
            WinNumberGroup winNumber = SSCWinNumberGroup.generate();
            WinResultGroup result = calculator.winJudge(winNumber.toString(), bet.toString());
            System.out.println("bet=" + bet);
            System.out.println("winNumber=" + winNumber);
            System.out.println("result=" + JSON.toJSONString(result));
            if (!result.valid()) {
                throw new RuntimeException();
            }
        }
        long end = System.currentTimeMillis();
        System.out.printf("测试注单数:%s, 耗时:%s毫秒", betCount, (end - start));
    }

}
