package pub.techfun.lottery.issue.base.rule;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 奖期休市规则
 */
@Data
public class IssueClosedRule {

    /** 优先级 */
    private int priority;
    /** 休市原因 */
    private String reason;
    /** 休市开始时间 */
    private LocalDateTime closeStartTime;
    /** 休市结束时间 */
    private LocalDateTime closeEndTime;
    /**
     * 奖期号是否连续（指休市前最后一个奖期和休市后第一个开始的奖期号之间是否连续）
     * 如果连续，则需要重新生成奖期，不连续则自动从休市完成后的下一个奖期开始
     */
    private boolean seqContinuous;
    /** 休市开始前上一期奖期号 */
    private String lastIssueNo;
    /** 休市完成后下一期奖期号 */
    private String nextIssueNo;

}
