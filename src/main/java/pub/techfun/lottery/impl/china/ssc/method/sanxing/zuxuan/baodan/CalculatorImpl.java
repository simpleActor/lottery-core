package pub.techfun.lottery.impl.china.ssc.method.sanxing.zuxuan.baodan;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.processor.BetFormatter;
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
import pub.techfun.lottery.util.NumSet;
import pub.techfun.lottery.util.NumStatsUtil;

import java.util.ArrayList;
import java.util.List;

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    private static final double odds_zu3 = 1000d / 54;
    private static final double odds_zu6 = 1000d / 216;

    public CalculatorImpl(int... indexes) {
        super(indexes);
    }

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> lists = CoverUtil.generateCombinations(3);
        lists.forEach(list -> {
            //排除豹子号
            if(list.get(0).equals(list.get(1)) && list.get(1).equals(list.get(2))) return;
            for (Integer num : betWrapper.getBet().get(0).getNumList()) {
                if(list.contains(num)) {
                    result.add(list);
                    break;
                }
            }
        });
        List<BetSection> fullSectionList = CoverUtil.getFullCoverByNumLists(this.indexes, result);
        return new BetNumRegion().addAll(fullSectionList);
    }

    @Override
    public double coverRate(String bet) {
        return cover(bet).size() / SSCConfig.ALL_FACTOR;
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
        return new ArrayList<>();
    }

    @Override
    protected void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result) {
        CountMap countMap = NumStatsUtil.getCountMap(winNumber.first().getNumList());
        NumSet repeatNum = countMap.getByCount(2);
        NumSet singleNum = countMap.getByCount(1);
        if(repeatNum == null && singleNum != null && singleNum.size() == 3) {
            //组6
            betWrapper.getBet().get(0).getNumList().forEach(num -> {
                if(singleNum.contains(num)) {
                    WinResult resultItem = new SSCWinResult(odds_zu6);
                    resultItem.setWinCount(36);
                    result.add(resultItem);
                }
            });
            return;
        }
        if(repeatNum != null && repeatNum.size() == 1 && singleNum != null && singleNum.size() == 1) {
            //组3
            betWrapper.getBet().get(0).getNumList().forEach(num -> {
                if(singleNum.contains(num) || repeatNum.contains(num)) {
                    WinResult resultItem = new SSCWinResult(odds_zu3);
                    resultItem.setWinCount(18);
                    result.add(resultItem);
                }
            });
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
