package pub.techfun.lottery.issue.client;

import pub.techfun.lottery.issue.Issue;
import pub.techfun.lottery.issue.generator.IssueGenerator;
import pub.techfun.lottery.issue.officialconfig.china.CqSSCIssueConfig;
import pub.techfun.lottery.issue.officialconfig.china.QiXingCaiIssueConfig;

import java.util.List;

/**
 * 官彩奖期生成器
 * TODO 生成的第一个奖期如果要和上一个奖期连续，可能需要特殊处理一下
 */
public class OfficialIssueGenerator {

    /**
     * 重庆时时彩
     */
    public static List<Issue> cqSSC(int cycleCount) {
        return IssueGenerator.generateByCycle(CqSSCIssueConfig.get(), cycleCount);
    }

    public static List<Issue> cqSSC(long sellEndBefore, int cycleCount) {
        return IssueGenerator.generateByCycle(CqSSCIssueConfig.get(sellEndBefore), cycleCount);
    }

    /**
     * 七星彩
     */
    public static List<Issue> qiXingCai(int cycleCount) {
        return IssueGenerator.generateByCycle(QiXingCaiIssueConfig.get(), cycleCount);
    }

    public static List<Issue> qiXingCai(long sellEndBefore, int cycleCount) {
        return IssueGenerator.generateByCycle(QiXingCaiIssueConfig.get(sellEndBefore), cycleCount);
    }

}
