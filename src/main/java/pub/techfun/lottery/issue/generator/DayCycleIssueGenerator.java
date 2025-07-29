package pub.techfun.lottery.issue.generator;

import cn.hutool.core.date.LocalDateTimeUtil;
import pub.techfun.lottery.issue.Issue;
import pub.techfun.lottery.issue.base.config.IssueConfig;
import pub.techfun.lottery.issue.base.consts.Cycle;
import pub.techfun.lottery.issue.base.consts.GenerateType;
import pub.techfun.lottery.issue.helper.GenerateHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 周期 {@link Cycle} 为天(Day)的奖期生成器
 */
public class DayCycleIssueGenerator {

    /**
     * 执行奖期生成
     *
     * @param config     奖期配置
     */
    public static List<Issue> processGenerate(IssueConfig config, LocalDate date) {
        List<Issue> issueList = new ArrayList<>();
        if (config.getGenerateType() == GenerateType.EveryDay) {
            generateEveryDay(issueList, config, date);
        }
        return issueList;
    }

    /**
     * 每天生成
     */
    private static void generateEveryDay(List<Issue> issueList, IssueConfig config, LocalDate date) {
        config.getSegmentList().forEach(segment -> {
            String prefix = LocalDateTimeUtil.format(date, config.getDateFormat());
            issueList.addAll(GenerateHelper.generateOneSegment(config, segment, prefix));
        });
    }

}
