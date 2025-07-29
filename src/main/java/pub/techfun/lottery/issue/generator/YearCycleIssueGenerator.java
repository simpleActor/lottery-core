package pub.techfun.lottery.issue.generator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import pub.techfun.lottery.issue.Issue;
import pub.techfun.lottery.issue.base.config.IssueConfig;
import pub.techfun.lottery.issue.base.config.SegmentConfig;
import pub.techfun.lottery.issue.base.consts.Cycle;
import pub.techfun.lottery.issue.helper.CycleTimeHelper;
import pub.techfun.lottery.issue.helper.GenerateHelper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 周期 {@link Cycle} 为年(Year)的奖期生成器
 */
public class YearCycleIssueGenerator {

    /**
     * 执行奖期生成
     *
     * @param config       奖期配置
     * @param oneCycleDays 一个周期有多少天
     */
    public static List<Issue> processGenerate(IssueConfig config, LocalDate beginDate, int oneCycleDays) {
        List<Issue> issueList = new ArrayList<>();
        LocalDate date = beginDate;
        Sequence seq = new Sequence();
        switch (config.getGenerateType()) {
            case EveryDay -> {
                //天为周期的，一个周期就是1天
                generateEveryDay(issueList, config, date);
            }
            case SpecifiedDayOfWeek -> {
                List<DayOfWeek> specifiedDayOfWeek = config.getExtraConfig().getSpecifiedDayOfWeek();
                if (CollUtil.isEmpty(specifiedDayOfWeek)) {
                    throw new RuntimeException("specifiedDayOfWeek is empty");
                }
                List<Long> cycleTimeList = CycleTimeHelper.getBySpecifiedDayOfWeek(config.getExtraConfig().getSpecifiedDayOfWeek());
                for (int i = 0; i < oneCycleDays; i++) {
                    config.resetDate(date);
                    generateSpecifiedDayOfWeek(issueList, config, date, cycleTimeList, seq);
                    date = date.plusDays(1);
                }
            }
            case SpecifiedDayOfMonth -> {
                List<Integer> specifiedDayOfMonth = config.getExtraConfig().getSpecifiedDayOfMonth();
                if (CollUtil.isEmpty(specifiedDayOfMonth)) {
                    throw new RuntimeException("specifiedDayOfMonth is empty");
                }
                List<Long> cycleTimeList = CycleTimeHelper.getBySpecifiedDayOfMonth(config.getExtraConfig().getSpecifiedDayOfMonth(), date);
                for (int i = 0; i < oneCycleDays; i++) {
                    config.resetDate(date);
                    generateSpecifiedDayOfMonth(issueList, config, date, cycleTimeList, seq);
                    date = date.plusDays(1);
                }
            }
            case SpecifiedDayOfYear -> {
                List<Integer> specifiedDayOfYear = config.getExtraConfig().getSpecifiedDayOfYear();
                if (CollUtil.isEmpty(specifiedDayOfYear)) {
                    throw new RuntimeException("specifiedDayOfYear is empty");
                }
                List<Long> cycleTimeList = CycleTimeHelper.getBySpecifiedDayOfYear(config.getExtraConfig().getSpecifiedDayOfYear(), date);
                for (int i = 0; i < oneCycleDays; i++) {
                    generateSpecifiedDayOfYear(issueList, config, date, cycleTimeList, seq);
                    date = date.plusDays(1);
                    config.resetDate(date);
                }
            }
            case SpecifiedDay -> {
                List<LocalDate> specifiedDays = config.getExtraConfig().getSpecifiedDay();
                if (CollUtil.isEmpty(specifiedDays)) {
                    throw new RuntimeException("specifiedDays is empty");
                }
                List<Long> cycleTimeList = CycleTimeHelper.getBySpecifiedDay(config.getExtraConfig().getSpecifiedDay());
                generateSpecifiedDay(issueList, config, cycleTimeList, seq);
            }
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

    /**
     * 指定一周的某几天生成
     */
    private static void generateSpecifiedDayOfWeek(List<Issue> issueList, IssueConfig config, LocalDate date,
                                                   List<Long> cycleTimeList, Sequence seq) {
        for (SegmentConfig segment : config.getSegmentList()) {
            List<DayOfWeek> specifiedDays = config.getExtraConfig().getSpecifiedDayOfWeek();
            for (int i = 0; i < specifiedDays.size(); i++) {
                config.resetCycleTime(cycleTimeList.get(i));
                if (specifiedDays.get(i) == date.getDayOfWeek()) {
                    config.resetBeginSeq(seq.val);
                    config.resetEndSeq(seq.val);
                    String prefix = LocalDateTimeUtil.format(date, config.getDateFormat());
                    issueList.addAll(GenerateHelper.generateOneSegment(config, segment, prefix));
                    seq.val++;
                    break;
                }
            }
        }
    }

    /**
     * 指定一月的某几天生成
     */
    private static void generateSpecifiedDayOfMonth(List<Issue> issueList, IssueConfig config, LocalDate date,
                                                    List<Long> cycleTimeList, Sequence seq) {
        for (SegmentConfig segment : config.getSegmentList()) {
            List<Integer> specifiedDays = config.getExtraConfig().getSpecifiedDayOfMonth();
            for (int i = 0; i < specifiedDays.size(); i++) {
                config.resetCycleTime(cycleTimeList.get(i));
                if (specifiedDays.get(i) == date.getDayOfMonth()) {
                    config.resetBeginSeq(seq.val);
                    config.resetEndSeq(seq.val);
                    String prefix = LocalDateTimeUtil.format(date, config.getDateFormat());
                    issueList.addAll(GenerateHelper.generateOneSegment(config, segment, prefix));
                    seq.val++;
                    break;
                }
            }
        }
    }

    /**
     * 指定一年的某几天生成
     */
    private static void generateSpecifiedDayOfYear(List<Issue> issueList, IssueConfig config, LocalDate date,
                                                   List<Long> cycleTimeList, Sequence seq) {
        for (SegmentConfig segment : config.getSegmentList()) {
            List<Integer> specifiedDays = config.getExtraConfig().getSpecifiedDayOfYear();
            for (int i = 0; i < specifiedDays.size(); i++) {
                config.resetCycleTime(cycleTimeList.get(i));
                if (specifiedDays.get(i) == date.getDayOfYear()) {
                    config.resetBeginSeq(seq.val);
                    config.resetEndSeq(seq.val);
                    String prefix = LocalDateTimeUtil.format(date, config.getDateFormat());
                    issueList.addAll(GenerateHelper.generateOneSegment(config, segment, prefix));
                    seq.val++;
                    break;
                }
            }
        }
    }

    /**
     * 指定具体日期生成
     */
    private static void generateSpecifiedDay(List<Issue> issueList, IssueConfig config,
                                             List<Long> cycleTimeList, Sequence seq) {
        for (SegmentConfig segment : config.getSegmentList()) {
            List<LocalDate> specifiedDays = config.getExtraConfig().getSpecifiedDay();
            for (int i = 0; i < specifiedDays.size(); i++) {
                config.resetDate(specifiedDays.get(i));
                config.resetCycleTime(cycleTimeList.get(i));
                config.resetBeginSeq(seq.val);
                config.resetEndSeq(seq.val);
                String prefix = LocalDateTimeUtil.format(specifiedDays.get(i), config.getDateFormat());
                issueList.addAll(GenerateHelper.generateOneSegment(config, segment, prefix));
                seq.val++;
            }
        }
    }

    static class Sequence {
        public int val = 1;
    }

}
