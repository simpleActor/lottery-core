package pub.techfun.lottery.impl.vietnam.middlesouth.common;

import pub.techfun.lottery.base.Level;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.result.WinResult;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumber;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MiddleSouthAbstractCalculator<T extends BetWrapper> implements Calculator<MiddleSouthAbstractBetWrapper, MiddleSouthWinNumberGroup> {

    @Override
    public WinResultGroup winJudge(String winNumber, String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        WinNumberGroup winNumberGroup = newWinNumberGroup(winNumber);
        MiddleSouthWinResultGroup result = errorHandle(winNumberGroup, betWrapper);
        if (!result.valid()) {
            return result;
        }
        process(result, winNumberGroup, betWrapper);
        return result;
    }

    @Override
    public WinResultGroup winJudge(String winNumber, String bet, BetFormatter formatter) {
        BetWrapper betWrapper = newBetWrapper(bet, formatter);
        WinNumberGroup winNumberGroup = newWinNumberGroup(winNumber);
        MiddleSouthWinResultGroup result = errorHandle(winNumberGroup, betWrapper);
        if (!result.valid()) {
            return result;
        }
        process(result, winNumberGroup, betWrapper);
        return result;
    }

    private void process(MiddleSouthWinResultGroup result, WinNumberGroup winNumberGroup, BetWrapper betWrapper) {
        result.setCount(betWrapper.getCount());
        Map<Level, List<WinNumber>> groupByMap = winNumberGroup.getWinNumberList().stream()
                .collect(Collectors.groupingBy(WinNumber::level, LinkedHashMap::new, Collectors.toList()));
        groupByMap.forEach((level, winNumberList) -> {
            WinResult winResult = processWinJudge(level, winNumberList, betWrapper);
            if(winResult != null) {
                result.add(winResult);
            }
        });
    }

    private WinNumberGroup newWinNumberGroup(String winNumber) {
        return new MiddleSouthWinNumberGroup(winNumber);
    }

    private MiddleSouthWinResultGroup errorHandle(WinNumberGroup winNumberGroup, BetWrapper betWrapper) {
        MiddleSouthWinResultGroup result = new MiddleSouthWinResultGroup();
        if (!betWrapper.valid()) {
            result.setValid(betWrapper.valid());
            result.setError(betWrapper.error());
            return result;
        }
        if (!winNumberGroup.valid()) {
            result.setValid(winNumberGroup.valid());
            result.setError(winNumberGroup.error());
            return result;
        }
        return result;
    }

    protected abstract MiddleSouthAbstractBetWrapper newBetWrapper(String bet);

    protected abstract MiddleSouthAbstractBetWrapper newBetWrapper(String bet, BetFormatter formatter);

    protected abstract WinResult processWinJudge(Level level, List<WinNumber> winNumberList, BetWrapper betWrapper);

}
