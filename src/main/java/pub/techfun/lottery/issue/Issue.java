package pub.techfun.lottery.issue;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Issue {

    /** 奖期号 */
    private String issueNo;
    /** 奖期开始时间 */
    private LocalDateTime startTime;
    /** 奖期截止时间 */
    private LocalDateTime endTime;
    /** 奖期周期时间（秒）*/
    private long cycleTime;
    /** 销售开始时间 */
    private LocalDateTime sellStartTime;
    /** 销售截止时间 */
    private LocalDateTime sellEndTime;
    /** 销售周期时间（秒）*/
    private long sellCycleTime;
    /** 销售截止后多少秒开奖 */
    private long drawDelay;

}
