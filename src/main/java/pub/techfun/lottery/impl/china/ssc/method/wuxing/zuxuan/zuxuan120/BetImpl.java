package pub.techfun.lottery.impl.china.ssc.method.wuxing.zuxuan.zuxuan120;

import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;
import pub.techfun.lottery.util.ACUtil;

class BetImpl extends SSCAbstractBetWrapper {

    /**
     * region 约束
     */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(1, 1, false);
    /**
     * section 约束
     */
    public static final SectionParam SECTION_CONSTRAINT = new SectionParam(0, 9, 5, 10, false, false);

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
        this.count = ACUtil.countC(getBet().get(0).size(), 5);
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {

    }

}
