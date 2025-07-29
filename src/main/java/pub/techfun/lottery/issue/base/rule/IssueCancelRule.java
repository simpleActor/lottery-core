package pub.techfun.lottery.issue.base.rule;

import lombok.Data;

/**
 * 奖期取消规则
 */
@Data
public class IssueCancelRule {

    /** 优先级 */
    private int priority;
    /** 延迟开奖原因 */
    private String reason;
    /** 开始奖期号(包含) */
    private String startIssueNo;
    /** 结束奖期号(包含) */
    private String endIssueNo;
    /** 下一奖期号 */
    private String nextIssueNo;
    /** 下一奖期序列号 */
    private int nextSeq;

}
