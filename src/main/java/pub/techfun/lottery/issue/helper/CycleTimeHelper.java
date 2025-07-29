package pub.techfun.lottery.issue.helper;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import pub.techfun.lottery.issue.base.consts.IssueConsts;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CycleTimeHelper {

    public static List<Long> getBySpecifiedDayOfWeek(List<DayOfWeek> specifiedDays) {
        List<Long> cycleTimeList = new ArrayList<>();
        for (int i = 0; i < specifiedDays.size(); i++) {
            if (i < specifiedDays.size() - 1) {
                long days = specifiedDays.get(i + 1).getValue() - specifiedDays.get(i).getValue();
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            } else if (i == specifiedDays.size() - 1) {
                long days = specifiedDays.getFirst().getValue() + 7 - specifiedDays.get(i).getValue();
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            }
        }
        return cycleTimeList;
    }

    public static List<Long> getBySpecifiedDayOfMonth(List<Integer> specifiedDays, Date date) {
        List<Long> cycleTimeList = new ArrayList<>();
        for (int i = 0; i < specifiedDays.size(); i++) {
            if (i < specifiedDays.size() - 1) {
                long days = specifiedDays.get(i + 1) - specifiedDays.get(i);
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            } else if (i == specifiedDays.size() - 1) {
                long endDayOfMonth = DateUtil.dayOfMonth(DateUtil.endOfMonth(date));
                long days = endDayOfMonth - specifiedDays.get(i) + specifiedDays.getFirst();
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            }
        }
        return cycleTimeList;
    }

    public static List<Long> getBySpecifiedDayOfMonth(List<Integer> specifiedDays, LocalDate localDate) {
        return getBySpecifiedDayOfMonth(specifiedDays, DateHelper.toDate(localDate));
    }

    public static List<Long> getBySpecifiedDayOfYear(List<Integer> specifiedDays, Date date) {
        List<Long> cycleTimeList = new ArrayList<>();
        for (int i = 0; i < specifiedDays.size(); i++) {
            if (i < specifiedDays.size() - 1) {
                long days = specifiedDays.get(i + 1) - specifiedDays.get(i);
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            } else if (i == specifiedDays.size() - 1) {
                long endDayOfYear = DateUtil.dayOfYear(DateUtil.endOfYear(date));
                long days = endDayOfYear - specifiedDays.get(i) + specifiedDays.getFirst();
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            }
        }
        return cycleTimeList;
    }

    public static List<Long> getBySpecifiedDayOfYear(List<Integer> specifiedDays, LocalDate localDate) {
        return getBySpecifiedDayOfYear(specifiedDays, DateHelper.toDate(localDate));
    }

    public static List<Long> getBySpecifiedDay(List<LocalDate> specifiedDays) {
        List<Long> cycleTimeList = new ArrayList<>();
        for (int i = 0; i < specifiedDays.size(); i++) {
            if (i < specifiedDays.size() - 1) {
                long days = DateUtil.between(DateHelper.toDate(specifiedDays.get(i + 1)), DateHelper.toDate(specifiedDays.get(i)), DateUnit.DAY);
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            } else if (i == specifiedDays.size() - 1) {
                Date nextYearDay = DateUtil.offsetYear(DateHelper.toDate(specifiedDays.getFirst()), 1);
                long days = DateUtil.between(nextYearDay, DateHelper.toDate(specifiedDays.get(i)), DateUnit.DAY);
                cycleTimeList.add(IssueConsts.DAY_SECONDS * days);
            }
        }
        return cycleTimeList;
    }

    public static void main(String[] args) {
        System.out.println(getBySpecifiedDayOfWeek(List.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)));
        System.out.println(getBySpecifiedDayOfMonth(List.of(2, 15, 25), new Date()));
        System.out.println(getBySpecifiedDayOfYear(List.of(2, 15, 25), new Date()));
        System.out.println(getBySpecifiedDay(List.of(
                LocalDate.of(2025,5,19),
                LocalDate.of(2025,5,22),
                LocalDate.of(2025,5,25))));
    }

}
