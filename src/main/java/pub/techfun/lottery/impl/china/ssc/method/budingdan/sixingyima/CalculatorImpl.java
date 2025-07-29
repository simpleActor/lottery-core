package pub.techfun.lottery.impl.china.ssc.method.budingdan.sixingyima;

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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    private static final double odds = SSCConfig.ALL_FACTOR / 10 / 3439;

    public CalculatorImpl(int... indexes) {
        this.indexes = indexes;
    }

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> lists = CoverUtil.generateCombinations(4);
        lists.forEach(list -> {
            for (Integer num : betWrapper.getBet().get(0).getNumList()) {
                if (list.contains(num)) {
                    result.add(list);
                    break;
                }
            }
        });
        List<BetSection> fullSectionList = CoverUtil.getFullCoverByNumLists(this.indexes, result)
                .stream().distinct().collect(Collectors.toList());
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
        return List.of(new SSCWinResult(odds));
    }

    @Override
    protected void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result) {
        int winCount = 0;
        for (Integer num : betWrapper.getBet().get(0).getNumList()) {
            if(winNumber.first().getNumList().contains(num)) {
                winCount++;
            }
        }
        result.first().setWinCount(winCount);
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl(0, 1, 2, 3);
        int betCount = 10;
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
