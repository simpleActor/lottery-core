package pub.techfun.lottery.issue.officialconfig.china;

import pub.techfun.lottery.issue.base.config.IssueConfig;
import pub.techfun.lottery.issue.base.config.IssueConfigExtra;
import pub.techfun.lottery.issue.base.config.SegmentConfig;
import pub.techfun.lottery.issue.base.consts.Cycle;
import pub.techfun.lottery.issue.base.consts.GenerateType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * 七星彩奖期配置
 */
public class QiXingCaiIssueConfig {

    public static IssueConfig get() {
        return get(5 * 60);
    }

    public static IssueConfig get(long sellEndBefore) {
        IssueConfig config = new IssueConfig();
        config.setCycle(Cycle.Year);
        config.setSeqFormat("000");
        config.setDateFormat("yy");
        config.setGenerateType(GenerateType.SpecifiedDayOfWeek);
        config.setSellEndBefore(sellEndBefore);
        IssueConfigExtra configExtra = new IssueConfigExtra();
        configExtra.setSpecifiedDayOfWeek(List.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY));
        config.setExtraConfig(configExtra);
        SegmentConfig segment = new SegmentConfig();
        segment.setStartTime(LocalTime.of(21, 25, 0));
        segment.setEndTime(LocalTime.of(21, 25, 0));
        config.addSegment(segment);
        return config;
    }

}
