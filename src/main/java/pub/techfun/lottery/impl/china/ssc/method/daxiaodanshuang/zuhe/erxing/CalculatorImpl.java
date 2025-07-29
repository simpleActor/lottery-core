package pub.techfun.lottery.impl.china.ssc.method.daxiaodanshuang.zuhe.erxing;

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

    private static final double odds = 4;

    public CalculatorImpl(int... indexes) {
        this.indexes = indexes;
    }

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<List<Integer>> lists = CoverUtil.generateCombinations(2);
        List<List<Integer>> result = lists.stream().filter(list -> {
            //第一位
            if (betWrapper.getBet().get(0).get(0) == 1) {
                //大
                return list.getFirst() > 4;
            }
            if (betWrapper.getBet().get(0).get(1) == 1) {
                //小
                return list.getFirst() < 5;
            }
            if (betWrapper.getBet().get(0).get(2) == 1) {
                //单
                return list.getFirst() % 2 != 0;
            }
            if (betWrapper.getBet().get(0).get(3) == 1) {
                //双
                return list.getFirst() % 2 == 0;
            }
            //第二位
            if (betWrapper.getBet().get(1).get(0) == 1) {
                //大
                return list.get(1) > 4;
            }
            if (betWrapper.getBet().get(1).get(1) == 1) {
                //小
                return list.get(1) < 5;
            }
            if (betWrapper.getBet().get(1).get(2) == 1) {
                //单
                return list.get(1) % 2 != 0;
            }
            if (betWrapper.getBet().get(1).get(3) == 1) {
                //双
                return list.get(1) % 2 == 0;
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
        return List.of(new SSCWinResult(odds));
    }

    @Override
    protected void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result) {
        int match1 = 0, match2 = 0;
        //第一位
        if (betWrapper.getBet().get(0).get(0) == 1 && winNumber.first().get(0) > 4) {
            //大
            match1++;
        }
        if (betWrapper.getBet().get(0).get(1) == 1 && winNumber.first().get(0) < 5) {
            //小
            match1++;
        }
        if (betWrapper.getBet().get(0).get(2) == 1 && winNumber.first().get(0) % 2 != 0) {
            //单
            match1++;
        }
        if (betWrapper.getBet().get(0).get(3) == 1 && winNumber.first().get(0) % 2 == 0) {
            //双
            match1++;
        }
        //第二位
        if (betWrapper.getBet().get(1).get(0) == 1 && winNumber.first().get(1) > 4) {
            //大
            match2++;
        }
        if (betWrapper.getBet().get(1).get(1) == 1 && winNumber.first().get(1) < 5) {
            //小
            match2++;
        }
        if (betWrapper.getBet().get(1).get(2) == 1 && winNumber.first().get(1) % 2 != 0) {
            //单
            match2++;
        }
        if (betWrapper.getBet().get(1).get(3) == 1 && winNumber.first().get(1) % 2 == 0) {
            //双
            match2++;
        }
        result.first().setWinCount(match1 * match2);
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl(0, 1);
        int betCount = 1000;
        List<BetRegion> betList = new ArrayList<>();
        for (int i = 0; i < betCount; i++) {
            BetRegion bet = RandomBetGenerator.generate(BetImpl.REGION_CONSTRAINT, BetImpl.SECTION_CONSTRAINT);
            //排除错误注单
            if(!bet.get(0).contains(1) || !bet.get(1).contains(1)) continue;
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
