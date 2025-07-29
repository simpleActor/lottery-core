package pub.techfun.lottery.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 拆单结果
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BetSplitResult {

    /** 是否有效 */
    private boolean valid = true;
    /** 错误信息 */
    private String error;
    /** 从注单提取的索引 */
    private List<SplittedBet> betList = new ArrayList<>();

    public BetSplitResult(boolean valid, String error) {
        this.valid = valid;
        this.error = error;
    }

    public BetSplitResult(SplittedBet bet) {
        this.betList.add(bet);
    }

    public BetSplitResult(List<SplittedBet> betList) {
        this.betList.addAll(betList);
    }

    /**
     * 拆单后的注单
     */
    @Data
    @AllArgsConstructor
    public static class SplittedBet {

        private String bet;

        private int[] indexes;

    }

}
