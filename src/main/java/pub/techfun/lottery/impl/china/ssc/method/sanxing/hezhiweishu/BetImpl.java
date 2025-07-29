package pub.techfun.lottery.impl.china.ssc.method.sanxing.hezhiweishu;

import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.impl.china.ssc.CoverUtil;
import pub.techfun.lottery.impl.china.ssc.common.SSCAbstractBetWrapper;
import pub.techfun.lottery.issue.helper.GenerateHelper;
import pub.techfun.lottery.util.ACUtil;
import pub.techfun.lottery.util.CountMap;
import pub.techfun.lottery.util.NumSet;
import pub.techfun.lottery.util.NumStatsUtil;

import java.util.*;

class BetImpl extends SSCAbstractBetWrapper {

    /**
     * region 约束
     */
    public static final RegionParam REGION_CONSTRAINT = new RegionParam(1, 1, false);
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
        this.count = getBet().get(0).size();
    }

    @Override
    public void check(String bet, BetCheckResult checkResult) {

    }

    public static void main(String[] args) {
        List<List<Integer>> lists = CoverUtil.generateCombinations(3);
        int x = 0;
        List<List<Integer>> zu3NumList = new ArrayList<>();
        List<List<Integer>> zu6NumList = new ArrayList<>();
        for (List<Integer> list : lists) {
            int sum = list.get(0) + list.get(1) + list.get(2);
            int weishu = sum % 10;
            if(weishu == 1) {
                x++;
            }
        }
        System.out.println(x);
    }

}
