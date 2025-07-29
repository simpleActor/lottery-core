package pub.techfun.lottery.impl.china.ssc.common;

import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.calculator.Calculator;
import pub.techfun.lottery.base.consts.EnumCommonError;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.result.WinResult;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.impl.china.ssc.consts.SSCConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class SSCAbstractCalculator<T extends BetWrapper> implements Calculator<SSCAbstractBetWrapper, SSCWinNumberGroup> {

    protected int[] indexes = new int[]{0, 1, 2, 3, 4};

    public SSCAbstractCalculator() {
    }

    public SSCAbstractCalculator(int... indexes) {
        this.indexes = indexes;
    }

    @Override
    public WinResultGroup winJudge(String winNum, String bet) {
        BetWrapper betWrapper = newBetWrapper(bet);
        WinNumberGroup winNumber = initWinNumber(winNum, betWrapper);
        SSCWinResultGroup result = errorHandle(winNumber, betWrapper);
        if (!result.valid()) {
            return result;
        }
        result.setCount(betWrapper.getCount());
        result.addAll(newWinResultList());
        processWinJudge(winNumber, betWrapper, result);
        return result;
    }

    @Override
    public WinResultGroup winJudge(String winNum, String bet, BetFormatter formatter) {
        BetWrapper betWrapper = newBetWrapper(bet, formatter);
        WinNumberGroup winNumber = initWinNumber(winNum, betWrapper);
        SSCWinResultGroup result = errorHandle(winNumber, betWrapper);
        if (!result.valid()) {
            return result;
        }
        result.setCount(betWrapper.getCount());
        result.addAll(newWinResultList());
        processWinJudge(winNumber, betWrapper, result);
        return result;
    }

    private WinNumberGroup initWinNumber(String winNum, BetWrapper betWrapper) {
        WinNumberGroup winNumber;
        //有些玩法需要从注单里面提取索引位置
        if (betWrapper.getIndexes() != null && betWrapper.getIndexes().length > 0) {
            winNumber = newWinNumber(winNum, betWrapper.getIndexes());
        } else {
            winNumber = newWinNumber(winNum, indexes);
        }
        return winNumber;
    }

    private WinNumberGroup newWinNumber(String winNum, int... indexes) {
        if(indexes == null || indexes.length == 0) {
            throw new RuntimeException("indexes is null or empty");
        }
        SSCWinNumberGroup winNumber = new SSCWinNumberGroup(winNum);
        if (!winNumber.valid()) {
            return winNumber;
        }
        if (indexes.length == SSCConfig.WIN_NUMBER_SIZE) {
            return winNumber;
        } else if (indexes.length < SSCConfig.WIN_NUMBER_SIZE) {
            List<Integer> numList = new ArrayList<>(winNumber.first().getNumList());
            winNumber.first().clear();
            for (int index : indexes) {
                winNumber.first().add(numList.get(index));
            }
        } else {
            winNumber.setValid(false);
            winNumber.setError(EnumCommonError.WIN_NUMBER_INDEX_ERROR.getMsg());
        }
        return winNumber;
    }

    private SSCWinResultGroup errorHandle(WinNumberGroup winNumber, BetWrapper betWrapper) {
        SSCWinResultGroup result = new SSCWinResultGroup();
        if (!betWrapper.valid()) {
            result.setValid(betWrapper.valid());
            result.setError(betWrapper.error());
            return result;
        }
        if (!winNumber.valid()) {
            result.setValid(winNumber.valid());
            result.setError(winNumber.error());
            return result;
        }
        return result;
    }

    protected abstract SSCAbstractBetWrapper newBetWrapper(String bet);

    protected abstract SSCAbstractBetWrapper newBetWrapper(String bet, BetFormatter formatter);

    protected abstract List<WinResult> newWinResultList();

    protected abstract void processWinJudge(WinNumberGroup winNumber, BetWrapper betWrapper, WinResultGroup result);

}
