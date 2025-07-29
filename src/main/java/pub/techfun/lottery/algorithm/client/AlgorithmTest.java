package pub.techfun.lottery.algorithm.client;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pub.techfun.lottery.algorithm.AlgorithmResult;
import pub.techfun.lottery.algorithm.param.BetParam;
import pub.techfun.lottery.algorithm.param.WinNumberGetParam;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.client.LotteryJudgeManager;
import pub.techfun.lottery.define.EnumCountry;
import pub.techfun.lottery.define.LotteryType;
import pub.techfun.lottery.define.china.EnumLotteryTypeChina;
import pub.techfun.lottery.define.china.ssc.EnumMethodSSC;
import pub.techfun.lottery.impl.china.ssc.common.SSCWinNumberGroup;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AlgorithmTest {

    public static void main(String[] args) {
        List<Amount> betAmountRandomList = new ArrayList<>();
        List<Amount> winAmountRandomList = new ArrayList<>();
        List<Amount> betAmountList = new ArrayList<>();
        List<Amount> winAmountList = new ArrayList<>();
        int testIssueCount = 1000, perIssueBetCount = 1000;
        LotteryType testLotteryType = EnumLotteryTypeChina.SSC;
        String lotteryId = "tenant:1|platform:1|lotteryId:1";
        String issue = "20250419001";

        for (int i = 0; i < testIssueCount; i++) {
            Amount betAmountRandom = new Amount();
            Amount winAmountRandom = new Amount();
            Amount betAmount = new Amount();
            Amount winAmount = new Amount();
            betAmountRandomList.add(betAmountRandom);
            winAmountRandomList.add(winAmountRandom);
            betAmountList.add(betAmount);
            winAmountList.add(winAmount);
            List<BetRegion> betList = generateBetList(testLotteryType, perIssueBetCount);
            randomWinJudge(betAmountRandom, winAmountRandom, betList);
            List<BetParam> betParamList = toBetParam(lotteryId, issue, betList);
            algorithmWinJudge(betAmount, winAmount, betParamList);
            System.out.println("Random bet=" + betAmountRandom.amount + ", win=" + winAmountRandom.amount + ", rate=" +
                    NumberUtil.roundStr((betAmountRandom.amount - winAmountRandom.amount) / betAmountRandom.amount, 2));
            System.out.println("Algorithm bet=" + betAmount.amount + ", win=" + winAmount.amount + ", rate=" +
                    NumberUtil.roundStr((betAmount.amount - winAmount.amount) / betAmount.amount, 2));
            System.out.println("---------------------------------------------------");
        }
        double betRandomTotal = betAmountRandomList.stream().mapToDouble(Amount::getAmount).sum();
        double winRandomTotal = winAmountRandomList.stream().mapToDouble(Amount::getAmount).sum();
        double betTotal = betAmountList.stream().mapToDouble(Amount::getAmount).sum();
        double winTotal = winAmountList.stream().mapToDouble(Amount::getAmount).sum();
        System.out.println("Random total profit rate=" + NumberUtil.roundStr((betRandomTotal - winRandomTotal) / betRandomTotal, 2));
        System.out.println("Random total bet=" + NumberUtil.toBigDecimal(betRandomTotal).toPlainString());
        System.out.println("Random total win=" + NumberUtil.toBigDecimal(winRandomTotal).toPlainString());
        System.out.println("---------------------------------------------------");
        System.out.println("Algorithm total profit rate=" + NumberUtil.roundStr((betTotal - winTotal) / betTotal, 2));
        System.out.println("Algorithm total bet=" + NumberUtil.toBigDecimal(betTotal).toPlainString());
        System.out.println("Algorithm total win=" + NumberUtil.toBigDecimal(winTotal).toPlainString());
    }

    public static void randomWinJudge(Amount betAmountRandom, Amount winAmountRandom, List<BetRegion> betList) {
        for (BetRegion bet : betList) {
            WinResultGroup result = LotteryJudgeManager.winJudge(EnumCountry.China, EnumLotteryTypeChina.SSC,
                    EnumMethodSSC.WU_XING_ZHI_XUAN_FU_SHI, SSCWinNumberGroup.generate().toString(), bet.toString());
            betAmountRandom.amount += result.count();
            result.getWinResultList().forEach(item -> {
                if (item.win()) {
                    winAmountRandom.amount += item.winCount() * item.odds();
                }
            });
        }
    }

    public static synchronized void algorithmWinJudge(Amount betAmount, Amount winAmount, List<BetParam> betList) {
        AlgorithmManager.receiveBet(betList);
        WinNumberGetParam param = new WinNumberGetParam(EnumCountry.China, EnumLotteryTypeChina.SSC, EnumMethodSSC.WU_XING_ZHI_XUAN_FU_SHI,
                betList.getFirst().getLotteryId(), betList.getFirst().getIssue());
        AlgorithmResult result = AlgorithmManager.getResult(param);
        if (result != null) {
            betAmount.amount += result.getBetAmount();
            winAmount.amount += result.getWinAmount();
        }
    }

    public static List<BetRegion> generateBetList(LotteryType lotteryType, int betCount) {
        //TODO 可以在这里添加多玩法注单进行测试
        List<BetRegion> betList = new ArrayList<>();
        RegionParam regionParam = new RegionParam(5, 5, false);
        SectionParam sectionParam = new SectionParam(0, 9, 1, 10, false, false);
        for (int i = 0; i < betCount; i++) {
            betList.add(RandomBetGenerator.generate(regionParam, sectionParam));
        }
        return betList;
    }

    public static List<BetParam> toBetParam(String lotteryId, String issue, List<BetRegion> betList) {
        List<BetParam> betParamList = new ArrayList<>();
        for (BetRegion bet : betList) {
            BetParam betParam = new BetParam(EnumCountry.China, EnumLotteryTypeChina.SSC, EnumMethodSSC.WU_XING_ZHI_XUAN_FU_SHI,
                    lotteryId, issue, bet.toString());
            betParamList.add(betParam);
        }
        return betParamList;
    }

    @Data
    public static class Amount {
        public double amount = 0;
    }

}
