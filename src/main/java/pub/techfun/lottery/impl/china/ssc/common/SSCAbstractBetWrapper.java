package pub.techfun.lottery.impl.china.ssc.common;

import cn.hutool.core.util.StrUtil;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.processor.BetFormatter;
import pub.techfun.lottery.base.bet.BetWrapper;
import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.result.FormatResult;
import pub.techfun.lottery.base.generator.RegionParam;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.result.BetCheckResult;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.consts.EnumCommonError;

import java.util.regex.Pattern;

public abstract class SSCAbstractBetWrapper implements BetWrapper {

    private static final Pattern PATTERN = Pattern.compile("^[0-9,|]+$");
    private BetRegion region;
    private int[] indexes;
    protected long count;
    protected BetCheckResult checkResult = new BetCheckResult();

    public SSCAbstractBetWrapper(String bet) {
        init(bet);
    }

    public SSCAbstractBetWrapper(String bet, BetFormatter formatter) {
        FormatResult formatResult = formatter.format(bet);
        if (formatResult.isValid()) {
            indexes = formatResult.getIndexes();
        }
        init(formatResult.getBet());
    }

    private void init(String bet) {
        if (StrUtil.isBlank(bet)) {
            checkResult.setValid(false).setError(EnumCommonError.BET_NULL_ERROR.getMsg());
            return;
        }
        if (!PATTERN.matcher(bet).matches()) {
            checkResult.setValid(false).setError(EnumCommonError.BET_CHARACTER_ERROR.getMsg());
            return;
        }
        //基础注单检测
        baseCheck(bet);
        if (!checkResult.valid()) {
            return;
        }
        //注数计算
        calculateCount();
    }

    @Override
    public void baseCheck(String bet) {
        region = new BetNumRegion(bet);
        RegionParam regionParam = getRegionConstraint();
        if (region.getSectionList().isEmpty() || region.getSectionList().size() > regionParam.getMaxSize() || region.getSectionList().size() < regionParam.getMinSize()) {
            checkResult.setValid(false).setError(EnumCommonError.BET_REGION_SIZE_ERROR.getMsg());
            return;
        }
        //TODO 这里不做Region重复检测，如果需要可在子类实现
        SectionParam sectionParam = getSectionConstraint();
        for (BetSection section : region.getSectionList()) {
            //TODO 这里不做Section重复检测，如果需要可在子类实现
            if (section.getNumList().isEmpty() || section.size() > sectionParam.getMaxSize() || section.size() < sectionParam.getMinSize()) {
                checkResult.setValid(false).setError(EnumCommonError.BET_SECTION_SIZE_ERROR.getMsg());
                return;
            }
            for (Integer num : section.getNumList()) {
                if (num > sectionParam.getMaxNum() || num < sectionParam.getMinNum()) {
                    checkResult.setValid(false).setError(EnumCommonError.BET_NUM_RANGE_ERROR.getMsg());
                    return;
                }
            }
        }
        //调用子类实现的注单检测
        check(bet, checkResult);
    }

    @Override
    public boolean valid() {
        return checkResult.valid();
    }

    @Override
    public String error() {
        return checkResult.error();
    }

    @Override
    public BetRegion getBet() {
        return region;
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public int[] getIndexes() {
        return indexes;
    }

    public void clear() {
        this.region.clear();
    }

    /**
     * 获取region约束
     * @return
     */
    protected abstract RegionParam getRegionConstraint();

    /**
     * 获取section约束
     * @return
     */
    protected abstract SectionParam getSectionConstraint();

    /**
     * 计算注数
     */
    protected abstract void calculateCount();

}
