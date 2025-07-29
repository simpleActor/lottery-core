package pub.techfun.lottery.algorithm.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class AlgorithmCalculatorKey {

    /** 具体彩种ID(唯一) */
    private String lotteryId;
    /** 奖期 */
    private String issue;

}
