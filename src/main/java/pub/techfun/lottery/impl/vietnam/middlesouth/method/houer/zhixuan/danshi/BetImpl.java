package pub.techfun.lottery.impl.vietnam.middlesouth.method.houer.zhixuan.danshi;

import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.vietnam.middlesouth.common.MiddleSouthAbstractBetWrapper;

class BetImpl extends MiddleSouthAbstractBetWrapper {

    /** region 约束 */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(1, 100, false);
    /** section 约束 */
    public static final SectionParam SECTION_CONSTRAINT = new SectionParam(0, 9, 2, 2, true, true);

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
        this.count = getBet().getSectionList().size();
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {

    }

}
