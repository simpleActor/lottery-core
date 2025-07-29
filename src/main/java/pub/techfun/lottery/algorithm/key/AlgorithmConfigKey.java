package pub.techfun.lottery.algorithm.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class AlgorithmConfigKey {

    /** 国家编码 */
    private Integer countryCode;
    /** 彩种类型编码 */
    private Integer lotteryTypeCode;
    /** 具体彩种ID(唯一) */
    private String lotteryId;

}
