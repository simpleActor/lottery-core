package pub.techfun.lottery.issue.base.config;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * 特殊规则的奖期增加额外配置
 */
@Data
public class IssueConfigExtra {

    /** 指定一周的某几天 */
    private List<DayOfWeek> specifiedDayOfWeek;
    /** 指定一月的某几天(从1开始) */
    private List<Integer> specifiedDayOfMonth;
    /** 指定一年的某几天(从1开始) */
    private List<Integer> specifiedDayOfYear;
    /** 指定具体日期 */
    private List<LocalDate> specifiedDay;

}
