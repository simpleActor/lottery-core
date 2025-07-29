package pub.techfun.lottery.impl.china.ssc.method.daxiaodanshuang.geshu.daxiao.sanxing;

import pub.techfun.lottery.base.consts.EnumCommonError;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;

class BetImpl extends SSCAbstractBetWrapper {

    /**
     * region 约束
     */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(1, 1, true);
    /**
     * section 约束
     */
    public static final SectionParam SECTION_CONSTRAINT = new SectionParam(0, 1, 4, 4, true, true);

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
        this.count = getBet().get(0).getNumList().stream().filter(num -> num == 1).count();
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {
        if(!bet.contains("1")) {
            checkResult.setValid(false);
            checkResult.setError(EnumCommonError.BET_SECTION_NULL_ERROR.getMsg());
        }
    }

}
