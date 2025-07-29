package pub.techfun.lottery.issue.base.consts;

import java.time.format.DateTimeFormatter;

public interface IssueConsts {

    //一天多少秒
    long DAY_SECONDS = 24 * 60 * 60;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
