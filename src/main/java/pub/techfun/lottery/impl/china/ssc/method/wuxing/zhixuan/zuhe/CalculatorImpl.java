package pub.techfun.lottery.impl.china.ssc.method.wuxing.zhixuan.zuhe;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.FuShiCombinationGenerator;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.result.WinResult;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractCalculator;
import pub.techfun.lottery.impl.china.ssc.common.SSCWinNumberGroup;
import pub.techfun.lottery.impl.china.ssc.common.SSCWinResult;
import pub.techfun.lottery.impl.china.ssc.consts.SSCConfig;

import java.util.ArrayList;
import java.util.List;

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    private static final double odds1 = SSCConfig.ALL_FACTOR;
    private static final double odds2 = SSCConfig.ALL_FACTOR / 10;
    private static final double odds3 = SSCConfig.ALL_FACTOR / 100;
    private static final double odds4 = SSCConfig.ALL_FACTOR / 1000;
    private static final double odds5 = SSCConfig.ALL_FACTOR / 10000;

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<BetSection> sectionList = FuShiCombinationGenerator.generateAll(betWrapper.getBet().getSectionList());
        return new BetNumRegion().addAll(sectionList);
    }

    @Override
    public double coverRate(String bet) {
        //5星组合注数乘以5，这里先除以5得到实际注数
        return newBetWrapper(bet).getCount() / 5d / SSCConfig.ALL_FACTOR;
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
        return List.of(new SSCWinResult(odds1), new SSCWinResult(odds2), new SSCWinResult(odds3), new SSCWinResult(odds4), new SSCWinResult(odds5));
    }

    @Override
    protected void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result) {
        for (int i = betWrapper.getBet().size() - 1; i >= 0; i--) {
            //从最小奖级开始判断，如果最小奖级都没有中，直接退出
            boolean win = judge(winNumber, betWrapper, result, i);
            if(!win) break;
        }
    }

    private static boolean judge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result, int i) {
        if (betWrapper.getBet().getSectionList().get(i).contains(winNumber.first().getNumList().get(i))) {
            result.get(i).setWinCount(1);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
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
