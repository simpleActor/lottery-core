package pub.techfun.lottery.issue.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper {

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
