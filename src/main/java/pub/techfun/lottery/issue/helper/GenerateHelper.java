package pub.techfun.lottery.issue.helper;

import cn.hutool.core.date.LocalDateTimeUtil;
import pub.techfun.lottery.issue.Issue;
import pub.techfun.lottery.issue.base.config.IssueConfig;
import pub.techfun.lottery.issue.base.config.SegmentConfig;

import java.util.ArrayList;
import java.util.List;

public class GenerateHelper {

    public static List<Issue> generateOneSegment(IssueConfig config, SegmentConfig segment, String prefix) {
        List<Issue> issueList = new ArrayList<>();
        int x = 0;
        for (int i = segment.getBeginSeq(); i <= segment.getEndSeq(); i += segment.getSeqStep()) {
            String seq = config.isFillZero() ? String.format("%0" + config.getSeqFormat().length() + "d", i) : i + "";
            Issue issue = new Issue();
            issue.setIssueNo(prefix + config.getDelimiter() + seq);
            issue.setStartTime(segment.getDate().atTime(segment.getStartTime()).plusSeconds(x * segment.getCycleTime()));
            issue.setEndTime(issue.getStartTime().plusSeconds(segment.getCycleTime()));
            issue.setCycleTime(segment.getCycleTime());
            issue.setSellStartTime(issue.getStartTime());
            issue.setSellEndTime(issue.getEndTime().minusSeconds(config.getSellEndBefore()));
            issue.setSellCycleTime(LocalDateTimeUtil.between(issue.getSellStartTime(), issue.getSellEndTime()).getSeconds());
            issue.setDrawDelay(config.getDrawDelay());
            issueList.add(issue);
            x++;
        }
        return issueList;
    }

}
