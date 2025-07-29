package pub.techfun.lottery.impl.china.ssc.method.daxiaodanshuang.geshu.danshuang.sixing;

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

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    //全单或全双
    private static final double odds_quan = 16;
    //3单1双或3双1单
    private static final double odds_3_1 = 4;
    //2单2双
    private static final double odds_2_2 = SSCConfig.ALL_FACTOR / 37500;

    public CalculatorImpl(int... indexes) {
        this.indexes = indexes;
    }

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<List<Integer>> lists = CoverUtil.generateCombinations(4);
        List<List<Integer>> result = lists.stream().filter(list -> {
            if (betWrapper.getBet().get(0).get(0) == 1) {
                //全单
                return list.stream().allMatch(num -> num % 2 != 0);
            }
            if (betWrapper.getBet().get(0).get(1) == 1) {
                //3单1双
                return list.stream().filter(num -> num % 2 != 0).count() == 3;
            }
            if (betWrapper.getBet().get(0).get(2) == 1) {
                //2单2双
                return list.stream().filter(num -> num % 2 != 0).count() == 2;
            }
            if (betWrapper.getBet().get(0).get(3) == 1) {
                //1单3双
                return list.stream().filter(num -> num % 2 != 0).count() == 1;
            }
            if (betWrapper.getBet().get(0).get(4) == 1) {
                //全双
                return list.stream().noneMatch(num -> num % 2 != 0);
            }
            return false;
        }).toList();
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
        return List.of(new SSCWinResult(0));
    }

    @Override
    protected void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result) {
        if (betWrapper.getBet().get(0).get(0) == 1
                && winNumber.first().getNumList().stream().allMatch(num -> num % 2 != 0)) {
            //全单
            result.first().setWinCount(1);
            result.first().setOdds(odds_quan);
            return;
        }
        if (betWrapper.getBet().get(0).get(1) == 1
                && winNumber.first().getNumList().stream().filter(num -> num % 2 != 0).count() == 3) {
            //3单1双
            result.first().setWinCount(1);
            result.first().setOdds(odds_3_1);
            return;
        }
        if (betWrapper.getBet().get(0).get(2) == 1
                && winNumber.first().getNumList().stream().filter(num -> num % 2 != 0).count() == 2) {
            //2单2双
            result.first().setWinCount(1);
            result.first().setOdds(odds_2_2);
            return;
        }
        if (betWrapper.getBet().get(0).get(3) == 1
                && winNumber.first().getNumList().stream().filter(num -> num % 2 != 0).count() == 1) {
            //1单3双
            result.first().setWinCount(1);
            result.first().setOdds(odds_3_1);
            return;
        }
        if (betWrapper.getBet().get(0).get(4) == 1
                && winNumber.first().getNumList().stream().noneMatch(num -> num % 2 != 0)) {
            //全双
            result.first().setWinCount(1);
            result.first().setOdds(odds_quan);
        }
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl(0, 1, 2, 3);
        int betCount = 1000;
        List<BetRegion> betList = new ArrayList<>();
        for (int i = 0; i < betCount; i++) {
            BetRegion bet = RandomBetGenerator.generate(BetImpl.REGION_CONSTRAINT, BetImpl.SECTION_CONSTRAINT);
            //排除错误注单
            if (!bet.get(0).contains(1)) continue;
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
        System.out.printf("测试注单数:%s, 耗时:%s毫秒", betList.size(), (end - start));
    }

}
