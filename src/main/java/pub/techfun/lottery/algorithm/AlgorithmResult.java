package pub.techfun.lottery.algorithm;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;

@Data
public class AlgorithmResult implements Comparable<AlgorithmResult> {

    public double winAmount = 0;
    public double betAmount = 0;
    private final WinNumberGroup winNumber;

    public AlgorithmResult(WinNumberGroup winNumber) {
        this.winNumber = winNumber;
    }

    @Override
    public int compareTo(AlgorithmResult o) {
        return (int) (this.winAmount - o.winAmount);
    }

    @Override
    public String toString() {
        return "AlgorithmResult{" +
                "winAmount=" + NumberUtil.roundStr(winAmount, 4) +
                ", betAmount=" + NumberUtil.roundStr(betAmount, 4) +
                ", winNumber=" + winNumber.toString() +
                '}';
    }
}
