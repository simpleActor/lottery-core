package pub.techfun.lottery.issue.generator;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import pub.techfun.lottery.issue.Issue;
import pub.techfun.lottery.issue.base.config.IssueConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 奖期生成器
 * TODO 暂时先假设只有天和年为周期的奖期
 */
public class IssueGenerator {

    public static List<Issue> generateByCycle(IssueConfig config, int cycleCount) {
        List<Issue> issueList = new ArrayList<>();
        switch (config.getCycle()) {
            case Day -> {
                //从当天开始生成奖期
                LocalDate date = LocalDate.now();
                for (int i = 0; i < cycleCount; i++) {
                    config.resetDate(date);
                    issueList.addAll(DayCycleIssueGenerator.processGenerate(config, date));
                    date = date.plusDays(1);
                }
            }
            case Year -> {
                //从今年第一天开始
                Date now = new Date();
                Date beginOfYear = DateUtil.beginOfYear(now);
                Date endOfYear = DateUtil.endOfYear(now);
                LocalDate beginDate = LocalDateTimeUtil.of(beginOfYear).toLocalDate();
                for (int i = 0; i < cycleCount; i++) {
                    int oneCycleDays = (int) DateUtil.between(beginOfYear, endOfYear, DateUnit.DAY) + 1;
                    issueList.addAll(YearCycleIssueGenerator.processGenerate(config, beginDate, oneCycleDays));
                    beginOfYear = DateUtil.offsetYear(beginOfYear, 1);
                    endOfYear = DateUtil.endOfYear(beginOfYear);
                    beginDate = LocalDateTimeUtil.of(beginOfYear).toLocalDate();
                }
            }
            case Week, Month -> {
                //TODO 暂时没有发现周期为（Week和 Month）的奖期
            }
        }
        return issueList;
    }

}
