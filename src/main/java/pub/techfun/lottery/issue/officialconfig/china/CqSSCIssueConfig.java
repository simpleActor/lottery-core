package pub.techfun.lottery.issue.officialconfig.china;

import pub.techfun.lottery.issue.base.config.IssueConfig;
import pub.techfun.lottery.issue.base.config.SegmentConfig;

import java.time.LocalTime;

/**
 * 重庆时时彩奖期配置
 */
public class CqSSCIssueConfig {

    public static IssueConfig get() {
        return get(5);
    }

    public static IssueConfig get(long sellEndBefore) {
        IssueConfig config = new IssueConfig();
        config.setSeqFormat("000");
        config.setSellEndBefore(sellEndBefore);
        SegmentConfig segment1 = new SegmentConfig();
        segment1.setStartTime(LocalTime.of(0, 0, 0));
        segment1.setEndTime(LocalTime.of(2, 0, 0));
        segment1.setBeginSeq(1);
        segment1.setEndSeq(24);
        segment1.setCount(24);
        segment1.setCycleTime(5 * 60);
        config.addSegment(segment1);
        SegmentConfig segment2 = new SegmentConfig();
        segment2.setStartTime(LocalTime.of(10, 0, 0));
        segment2.setEndTime(LocalTime.of(22, 0, 0));
        segment2.setBeginSeq(25);
        segment2.setEndSeq(96);
        segment2.setCount(72);
        segment2.setCycleTime(10 * 60);
        config.addSegment(segment2);
        SegmentConfig segment3 = new SegmentConfig();
        segment3.setStartTime(LocalTime.of(22, 0, 0));
        segment3.setEndTime(LocalTime.of(0, 0, 0));
        segment3.setBeginSeq(97);
        segment3.setEndSeq(120);
        segment3.setCount(24);
        segment3.setCycleTime(5 * 60);
        config.addSegment(segment3);
        return config;
    }

}
