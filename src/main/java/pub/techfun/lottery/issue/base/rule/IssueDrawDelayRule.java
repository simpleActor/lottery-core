package pub.techfun.lottery.issue.base.rule;

import lombok.Data;

/**
 * 延迟开奖规则
 */
@Data
public class IssueDrawDelayRule {

    /** 优先级 */
    private int priority;
    /** 延迟开奖原因 */
    private String reason;
    /** 延迟开奖时间（秒） */
    private long delaySeconds;
    /** 开始奖期号(包含) */
    private String startIssueNo;
    /** 结束奖期号(包含) */
    private String endIssueNo;

}
