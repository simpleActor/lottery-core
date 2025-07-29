package pub.techfun.lottery.impl.china.ssc.method.sanxing.zhixuan.hezhi;

import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;
import pub.techfun.lottery.util.SumACUtil;

class BetImpl extends SSCAbstractBetWrapper {

    /**
     * region 约束
     */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(1, 1, true);
    /**
     * section 约束
     */
    public static final SectionParam SECTION_CONSTRAINT = new SectionParam(0, 27, 1, 28, false, false);

    BetImpl(String bet) {
        super(bet);
    }

    BetImpl(String bet, BetFormatter formatter) {
        super(bet, formatter);
    }

    @Override
    protected RegionParam getRegionConstraint() {
        return REGION_CONSTRAINT;
    }

    @Override
    protected SectionParam getSectionConstraint() {
        return SECTION_CONSTRAINT;
    }

    @Override
    protected void calculateCount() {
        this.count = getBet().get(0).getNumList().stream().map(val -> SumACUtil.getSumNCountA(3, val))
                .reduce(0L, Long::sum);
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {

    }

}
