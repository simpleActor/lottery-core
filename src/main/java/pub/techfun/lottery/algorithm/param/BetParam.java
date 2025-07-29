package pub.techfun.lottery.algorithm.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pub.techfun.lottery.define.EnumCountry;
import pub.techfun.lottery.define.LotteryMethod;
import pub.techfun.lottery.define.LotteryType;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BetParam {

    /** 国家编码 */
    private Integer countryCode;
    /** 彩种类型编码 */
    private Integer lotteryTypeCode;
    /** 玩法编码 */
    private Integer methodCode;
    /** 具体彩种ID */
    private String lotteryId;
    /** 奖期 */
    private String issue;
    /** 注单 */
    private String bet;

    public BetParam(EnumCountry country, LotteryType lotteryType, LotteryMethod lotteryMethod, String lotteryId, String issue, String bet) {
        this.countryCode = country.getCode();
        this.lotteryTypeCode = lotteryType.getCode();
        this.methodCode = lotteryMethod.getCode();
        this.lotteryId = lotteryId;
        this.issue = issue;
        this.bet = bet;
    }

}
