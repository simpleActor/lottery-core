package pub.techfun.lottery.impl.vietnam.middlesouth.method.houer.zhixuan.danshi;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.Level;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.consts.EnumPrizeLevel;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RandomBetGenerator;
import pub.techfun.lottery.base.result.WinResult;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumber;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.impl.vietnam.middlesouth.common.MiddleSouthAbstractBetWrapper;
import pub.techfun.lottery.impl.vietnam.middlesouth.common.MiddleSouthAbstractCalculator;
import pub.techfun.lottery.impl.vietnam.middlesouth.common.MiddleSouthWinNumberGroup;
import pub.techfun.lottery.impl.vietnam.middlesouth.common.MiddleSouthWinResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculatorImpl extends MiddleSouthAbstractCalculator<BetImpl> {

    private static final double odds = 100;

    @Override
    public BetRegion cover(String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        return betWrapper.getBet();
    }

    @Override
    public double coverRate(String bet) {
        return newBetWrapper(bet).getCount() / odds;
    }

    @Override
    public MiddleSouthAbstractBetWrapper newBetWrapper(String bet) {
        return new BetImpl(bet);
    }

    @Override
    public MiddleSouthAbstractBetWrapper newBetWrapper(String bet, BetFormatter formatter) {
        return new BetImpl(bet, formatter);
    }

    @Override
    public WinResult processWinJudge(Level level, List<WinNumber> winNumberList, BetWrapper betWrapper) {
        if(level == EnumPrizeLevel.special) {
            MiddleSouthWinResult winResult = new MiddleSouthWinResult(odds);
            winResult.setLevel(level);
            for (BetSection section : betWrapper.getBet().getSectionList()) {
                if(Objects.equals(section.getNumList().get(0), winNumberList.getFirst().getNumList().get(4))
                    && Objects.equals(section.getNumList().get(1), winNumberList.getFirst().getNumList().get(5))) {
                    winResult.setWinCount(1);
                    break;
                }
            }
            return winResult;
        }
        return null;
    }

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        int betCount = 1000;
        List<BetRegion> betList = new ArrayList<>();
        WinNumberGroup winNumber = MiddleSouthWinNumberGroup.generate();
        for (int i = 0; i < betCount; i++) {
            BetRegion bet = RandomBetGenerator.generate(BetImpl.REGION_CONSTRAINT, BetImpl.SECTION_CONSTRAINT);
            betList.add(bet);
        }
        long start = System.currentTimeMillis();
        for (BetRegion bet : betList) {
            WinResultGroup result = calculator.winJudge(winNumber.toString(), bet.toString());
            if (!result.valid()) {
                System.out.println("bet=" + bet);
                System.out.println("winNumber=" + winNumber);
                System.out.println(JSON.toJSONString(result));
                throw new RuntimeException();
            }
            System.out.println(JSON.toJSONString(result));
        }
        long end = System.currentTimeMillis();
        System.out.printf("测试注单数:%s, 耗时:%s毫秒", betCount, (end - start));
    }

}
