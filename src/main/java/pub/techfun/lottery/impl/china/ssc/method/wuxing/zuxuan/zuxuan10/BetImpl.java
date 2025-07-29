package pub.techfun.lottery.impl.china.ssc.method.wuxing.zuxuan.zuxuan10;

import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;

class BetImpl extends SSCAbstractBetWrapper {

    /**
     * region 约束
     */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(2, 2, true);
    /**
     * section 约束
     */
    public static final SectionParam SECTION_CONSTRAINT = new SectionParam(0, 9, 1, 10, false, false);

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
        for (Integer repeatNum : getBet().get(0).getNumList()) {
            if(getBet().get(1).contains(repeatNum)) {
                this.count += getBet().get(1).size() - 1;
            } else {
                this.count += getBet().get(1).size();
            }
        }
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {

    }

}
