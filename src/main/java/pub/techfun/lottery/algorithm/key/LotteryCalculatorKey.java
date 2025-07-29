package pub.techfun.lottery.algorithm.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class LotteryCalculatorKey {

    /** 国家编码 */
    private Integer countryCode;
    /** 彩种类型编码 */
    private Integer lotteryTypeCode;
    /** 玩法编码 */
    private Integer methodCode;

}
