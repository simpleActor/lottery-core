package pub.techfun.lottery.issue.client;

import pub.techfun.lottery.issue.Issue;
import pub.techfun.lottery.issue.base.config.IssueConfig;
import pub.techfun.lottery.issue.generator.IssueGenerator;

import java.util.List;

public class SelfIssueGenerator {

    /**
     * 自营彩奖期生成
     */
    public static List<Issue> generate(long cycleTime, int cycleCount) {
        IssueConfig config = IssueConfig.getNormalConfig(cycleTime);
        return IssueGenerator.generateByCycle(config, cycleCount);
    }

    /**
     * 自营彩奖期生成
     */
    public static List<Issue> generate(String seqFormat, long cycleTime, int cycleCount) {
        IssueConfig config = IssueConfig.getNormalConfig(seqFormat, cycleTime);
        return IssueGenerator.generateByCycle(config, cycleCount);
    }

}
