package pub.techfun.lottery.impl.china.ssc.method.niuniu;

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
import pub.techfun.lottery.util.NiuNiuUtil;

import java.util.ArrayList;
import java.util.List;

public class CalculatorImpl extends SSCAbstractCalculator<BetImpl> {

    private static final double odds_none_niu = SSCConfig.ALL_FACTOR / 35649;
    private static final double odds_niu_13579 = SSCConfig.ALL_FACTOR / 6101;
    private static final double odds_niu_2468 = SSCConfig.ALL_FACTOR / 6379;
    private static final double odds_niu_niu = SSCConfig.ALL_FACTOR / 8330;
    private static final double odds_big = SSCConfig.ALL_FACTOR / 33290;
    private static final double odds_small = SSCConfig.ALL_FACTOR / 31061;
    private static final double odds_odd = SSCConfig.ALL_FACTOR / 66154;
    private static final double odds_even = SSCConfig.ALL_FACTOR / 33846;

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        List<List<Integer>> lists = CoverUtil.generateCombinations(5);
        List<List<Integer>> result = lists.stream().filter(list -> {
            if (betWrapper.getBet().get(0).get(0) == 1) {
                //无牛
                return NiuNiuUtil.niuNum(list) == -1;
            }
            if (betWrapper.getBet().get(0).get(1) == 1) {
                //牛1
                return NiuNiuUtil.niuNum(list) == 1;
            }
            if (betWrapper.getBet().get(0).get(2) == 1) {
                //牛2
                return NiuNiuUtil.niuNum(list) == 2;
            }
            if (betWrapper.getBet().get(0).get(3) == 1) {
                //牛3
                return NiuNiuUtil.niuNum(list) == 3;
            }
            if (betWrapper.getBet().get(0).get(4) == 1) {
                //牛4
                return NiuNiuUtil.niuNum(list) == 4;
            }
            if (betWrapper.getBet().get(0).get(5) == 1) {
                //牛5
                return NiuNiuUtil.niuNum(list) == 5;
            }
            if (betWrapper.getBet().get(0).get(6) == 1) {
                //牛6
                return NiuNiuUtil.niuNum(list) == 6;
            }
            if (betWrapper.getBet().get(0).get(7) == 1) {
                //牛7
                return NiuNiuUtil.niuNum(list) == 7;
            }
            if (betWrapper.getBet().get(0).get(8) == 1) {
                //牛8
                return NiuNiuUtil.niuNum(list) == 8;
            }
            if (betWrapper.getBet().get(0).get(9) == 1) {
                //牛9
                return NiuNiuUtil.niuNum(list) == 9;
            }
            if (betWrapper.getBet().get(0).get(10) == 1) {
                //牛牛
                return NiuNiuUtil.niuNum(list) == 0;
            }
            if (betWrapper.getBet().get(0).get(11) == 1) {
                //牛大
                int niuNum = NiuNiuUtil.niuNum(list);
                return niuNum > 5 || niuNum == 0;
            }
            if (betWrapper.getBet().get(0).get(12) == 1) {
                //牛小
                int niuNum = NiuNiuUtil.niuNum(list);
                return niuNum > 0 && niuNum < 6;
            }
            if (betWrapper.getBet().get(0).get(13) == 1) {
                //牛单
                int niuNum = NiuNiuUtil.niuNum(list);
                return niuNum % 2 != 0;
            }
            if (betWrapper.getBet().get(0).get(14) == 1) {
                //牛双
                int niuNum = NiuNiuUtil.niuNum(list);
                return niuNum % 2 == 0;
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
        if (betWrapper.getBet().get(0).get(0) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == -1) {
            //无牛
            result.first().setWinCount(1);
            result.first().setOdds(odds_none_niu);
            return;
        }
        if (betWrapper.getBet().get(0).get(1) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 1) {
            //牛1
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_13579);
            return;
        }
        if (betWrapper.getBet().get(0).get(2) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 2) {
            //牛2
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_2468);
            return;
        }
        if (betWrapper.getBet().get(0).get(3) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 3) {
            //牛3
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_13579);
            return;
        }
        if (betWrapper.getBet().get(0).get(4) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 4) {
            //牛4
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_2468);
            return;
        }
        if (betWrapper.getBet().get(0).get(5) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 5) {
            //牛5
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_13579);
            return;
        }
        if (betWrapper.getBet().get(0).get(6) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 6) {
            //牛6
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_2468);
            return;
        }
        if (betWrapper.getBet().get(0).get(7) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 7) {
            //牛7
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_13579);
            return;
        }
        if (betWrapper.getBet().get(0).get(8) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 8) {
            //牛8
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_2468);
            return;
        }
        if (betWrapper.getBet().get(0).get(9) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 9) {
            //牛9
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_13579);
            return;
        }
        if (betWrapper.getBet().get(0).get(10) == 1 && NiuNiuUtil.niuNum(winNumber.first().getNumList()) == 0) {
            //牛牛
            result.first().setWinCount(1);
            result.first().setOdds(odds_niu_niu);
            return;
        }
        if (betWrapper.getBet().get(0).get(11) == 1) {
            //牛大
            int niuNum = NiuNiuUtil.niuNum(winNumber.first().getNumList());
            if (niuNum > 5 || niuNum == 0) {
                result.first().setWinCount(1);
                result.first().setOdds(odds_big);
            }
            return;
        }
        if (betWrapper.getBet().get(0).get(12) == 1) {
            //牛小
            int niuNum = NiuNiuUtil.niuNum(winNumber.first().getNumList());
            if (niuNum > 0 && niuNum < 6) {
                result.first().setWinCount(1);
                result.first().setOdds(odds_small);
            }
            return;
        }
        if (betWrapper.getBet().get(0).get(13) == 1) {
            //牛单
            int niuNum = NiuNiuUtil.niuNum(winNumber.first().getNumList());
            if (niuNum % 2 != 0) {
                result.first().setWinCount(1);
                result.first().setOdds(odds_odd);
            }
            return;
        }
        if (betWrapper.getBet().get(0).get(14) == 1) {
            //牛双
            int niuNum = NiuNiuUtil.niuNum(winNumber.first().getNumList());
            if (niuNum % 2 == 0) {
                result.first().setWinCount(1);
                result.first().setOdds(odds_even);
            }
        }


    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
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
