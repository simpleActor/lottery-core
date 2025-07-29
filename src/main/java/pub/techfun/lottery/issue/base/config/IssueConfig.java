package pub.techfun.lottery.issue.base.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pub.techfun.lottery.issue.base.consts.Cycle;
import pub.techfun.lottery.issue.base.consts.GenerateType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 奖期基础配置
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class IssueConfig {

    private final List<SegmentConfig> segmentList = new ArrayList<>();

    /**
     * 日期格式
     */
    private String dateFormat = "yyyyMMdd";
    /**
     * 日期和seq分隔符
     */
    private String delimiter = "";
    /**
     * seq格式
     */
    private String seqFormat = "0000";
    /**
     * seq是否需要补0
     */
    private boolean fillZero = true;
    /**
     * 销售提前x秒截止
     */
    private long sellEndBefore = 0;
    /**
     * 销售截止后多少秒开奖
     */
    private long drawDelay = 0;
    /**
     * 周期
     */
    private Cycle cycle = Cycle.Day;
    /**
     * 奖期生成类型
     */
    private GenerateType generateType = GenerateType.EveryDay;
    /**
     * 奖期附加配置
     */
    private IssueConfigExtra extraConfig;

    public void addSegment(SegmentConfig segment) {
        this.segmentList.add(segment);
    }

    public void clear() {
        this.segmentList.clear();
    }

    public void resetDate(Date date) {
        this.segmentList.forEach(segment -> {
            segment.resetDate(date);
        });
    }

    public void resetDate(int index, Date date) {
        this.getSegmentList().get(index).resetDate(date);
    }

    public void resetDate(LocalDate date) {
        this.segmentList.forEach(segment -> {
            segment.resetDate(date);
        });
    }

    public void resetDate(int index, LocalDate date) {
        this.getSegmentList().get(index).resetDate(date);
    }

    public void resetTime(LocalTime startTime, LocalTime endTime) {
        this.segmentList.forEach(segment -> {
            segment.resetTime(startTime, endTime);
        });
    }

    public void resetTime(int index, LocalTime startTime, LocalTime endTime) {
        this.getSegmentList().get(index).resetTime(startTime, endTime);
    }

    public void resetCycleTime(long cycleTime) {
        this.segmentList.forEach(segment -> {
            segment.resetCycleTime(cycleTime);
        });
    }

    public void resetCycleTime(int index, long cycleTime) {
        this.getSegmentList().get(index).resetCycleTime(cycleTime);
    }

    public void resetSeq(int beginSeq, int endSeq) {
        this.segmentList.forEach(segment -> {
            segment.resetSeq(beginSeq, endSeq);
        });
    }

    public void resetBeginSeq(int beginSeq) {
        this.segmentList.forEach(segment -> {
            segment.resetBeginSeq(beginSeq);
        });
    }

    public void resetEndSeq(int endSeq) {
        this.segmentList.forEach(segment -> {
            segment.resetEndSeq(endSeq);
        });
    }

    public static IssueConfig getNormalConfig(long cycleTime) {
        IssueConfig config = new IssueConfig();
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(long sellEndBefore, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setSellEndBefore(sellEndBefore);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(long sellEndBefore, long drawDelay, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setSellEndBefore(sellEndBefore)
                .setDrawDelay(drawDelay);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(String seqFormat, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setSeqFormat(seqFormat);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(String seqFormat, long sellEndBefore, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setSeqFormat(seqFormat)
                .setSellEndBefore(sellEndBefore);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(String seqFormat, long sellEndBefore, long drawDelay, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setSeqFormat(seqFormat)
                .setSellEndBefore(sellEndBefore)
                .setDrawDelay(drawDelay);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(String dateFormat, String seqFormat, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setDateFormat(dateFormat)
                .setSeqFormat(seqFormat);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(String dateFormat, String seqFormat, long sellEndBefore, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setDateFormat(dateFormat)
                .setSeqFormat(seqFormat)
                .setSellEndBefore(sellEndBefore);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

    public static IssueConfig getNormalConfig(String dateFormat, String seqFormat, long sellEndBefore, long drawDelay, long cycleTime) {
        IssueConfig config = new IssueConfig()
                .setDateFormat(dateFormat)
                .setSeqFormat(seqFormat)
                .setSellEndBefore(sellEndBefore)
                .setDrawDelay(drawDelay);
        config.getSegmentList().add(SegmentConfig.getNormalConfig(cycleTime));
        return config;
    }

}
