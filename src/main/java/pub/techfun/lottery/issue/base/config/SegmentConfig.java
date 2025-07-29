package pub.techfun.lottery.issue.base.config;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pub.techfun.lottery.issue.base.consts.IssueConsts;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * 分段奖期配置
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SegmentConfig {

    /** 日期 */
    private LocalDate date = LocalDate.now();
    /** 开始时间 */
    private LocalTime startTime = LocalTime.of(0, 0, 0);
    /** 结束时间 */
    private LocalTime endTime = LocalTime.of(0, 0, 0);
    /** 周期时间(秒) */
    private long cycleTime;
    /** 期数 */
    private int count;
    /** seq开始值 */
    private int beginSeq = 1;
    /** seq结束值 */
    private int endSeq;
    /** seq步长 */
    private int seqStep = 1;

    public SegmentConfig(long cycleTime) {
        this.cycleTime = cycleTime;
        this.count = (int) (IssueConsts.DAY_SECONDS / cycleTime);
        this.endSeq = count;
    }

    public void resetDate(Date date) {
        this.date = LocalDateTimeUtil.of(date).toLocalDate();
    }

    public void resetDate(LocalDate date) {
        this.date = date;
    }

    public void resetTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void resetCycleTime(long cycleTime) {
        this.cycleTime = cycleTime;
    }

    public void resetSeq(int beginSeq, int endSeq) {
        this.beginSeq = beginSeq;
        this.endSeq = endSeq;
    }

    public void resetBeginSeq(int beginSeq) {
        this.beginSeq = beginSeq;
    }

    public void resetEndSeq(int endSeq) {
        this.endSeq = endSeq;
    }

    public static SegmentConfig getNormalConfig(long cycleTime) {
        return new SegmentConfig(cycleTime);
    }

}
