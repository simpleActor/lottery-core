package pub.techfun.lottery.impl.china.ssc.method.sanxing.zuxuan.hezhi;

import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;
import pub.techfun.lottery.util.SumACUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BetImpl extends SSCAbstractBetWrapper {

    /**
     * region 约束
     */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(1, 1, false);
    /**
     * section 约束
     */
    public static final SectionParam SECTION_CONSTRAINT = new SectionParam(1, 26, 1, 26, false, false);

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
        List<List<Integer>> result = new ArrayList<>();
        getBet().get(0).getNumList().forEach(val -> {
            List<List<Integer>> lists = SumACUtil.getSumNListsA(3, val).stream()
                    .filter(list -> !list.getFirst().equals(list.get(1)) || !list.get(1).equals(list.get(2))).toList();
            lists.forEach(Collections::sort);
            result.addAll(lists);
        });
        this.count = result.stream().distinct().count();
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {

    }

}
