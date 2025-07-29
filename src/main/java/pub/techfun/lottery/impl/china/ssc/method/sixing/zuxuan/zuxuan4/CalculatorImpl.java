package pub.techfun.lottery.impl.china.ssc.method.sixing.zuxuan.zuxuan4;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.bet.*;
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
import pub.techfun.lottery.util.ACUtil;
import pub.techfun.lottery.util.CountMap;
import pub.techfun.lottery.util.NumStatsUtil;
import pub.techfun.lottery.util.NumSet;

import java.util.ArrayList;
import java.util.List;

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    private static final double odds = SSCConfig.ALL_FACTOR / 10 / 4;

    public CalculatorImpl(int... indexes) {
        super(indexes);
    }

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<BetSection> sectionList = new ArrayList<>();
        betWrapper.getBet().get(0).getNumList().forEach(repeatNum -> {
            //单号任选1
            betWrapper.getBet().get(1).getNumList().forEach(singleNum -> {
                if (singleNum.equals(repeatNum)) return;
                List<Integer> numList = new ArrayList<>();
                //重号
                numList.add(repeatNum);
                numList.add(repeatNum);
                numList.add(repeatNum);
                //单号
                numList.add(singleNum);
                //求排列并去重
                List<List<Integer>> aList = ACUtil.A(numList, 4).stream().distinct().toList();
                aList.forEach(nums -> sectionList.add(new BetNumSection(nums)));
            });
        });
        BetRegion result = new BetNumRegion();
        result.addAll(CoverUtil.getFullCover(this.indexes, sectionList));
        return result;
    }

    @Override
    public double coverRate(String bet) {
        //4星组选4，先乘以4得到4星复式注数，再乘以10转换为5星注数
        return newBetWrapper(bet).getCount() * 4 * 10 / SSCConfig.ALL_FACTOR;
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
        CountMap countMap = NumStatsUtil.getCountMap(winNumber.first().getNumList());
        NumSet repeatNum = countMap.getByCount(3);
        NumSet singleNum = countMap.getByCount(1);
        if (repeatNum == null || singleNum == null || repeatNum.size() != 1 || singleNum.size() != 1) return;
        boolean win = false;
        for (Integer num : betWrapper.getBet().get(0).getNumList()) {
            if (repeatNum.contains(num)) {
                win = true;
                break;
            }
        }
        if (!win) return;
        win = false;
        for (Integer num : betWrapper.getBet().get(1).getNumList()) {
            if (singleNum.contains(num)) {
                win = true;
                break;
            }
        }
        if (win) {
            result.first().setWinCount(1);
        }
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl(0, 1, 2, 3);
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
