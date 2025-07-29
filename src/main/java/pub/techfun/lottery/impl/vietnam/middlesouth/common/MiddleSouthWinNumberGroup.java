package pub.techfun.lottery.impl.vietnam.middlesouth.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import pub.techfun.lottery.base.Level;
import pub.techfun.lottery.base.consts.Delimiter;
import pub.techfun.lottery.base.consts.EnumCommonError;
import pub.techfun.lottery.base.consts.EnumPrizeLevel;
import pub.techfun.lottery.base.consts.Splitter;
import pub.techfun.lottery.base.generator.RandomWinNumberGenerator;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.winnumber.WinNumber;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.impl.vietnam.middlesouth.consts.MiddleSouthConfig;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MiddleSouthWinNumberGroup implements WinNumberGroup {

    /** 中奖号码 约束 */
    private static final SectionParam CONSTRAINT_SPECIAL = new SectionParam(0, 9, 6, 6, true, true);
    private static final SectionParam CONSTRAINT_ONE_TO_FOUR = new SectionParam(0, 9, 5, 5, true, true);
    private static final SectionParam CONSTRAINT_FIVE_SIX = new SectionParam(0, 9, 4, 4, true, true);
    private static final SectionParam CONSTRAINT_SEVEN = new SectionParam(0, 9, 3, 3, true, true);
    private static final SectionParam CONSTRAINT_EIGHT = new SectionParam(0, 9, 2, 2, true, true);
    /** 中奖号码列表 */
    private final List<WinNumber> winNumberList = new ArrayList<>();
    /** 是否有效 */
    private boolean valid = true;
    /** 错误信息 */
    private String error;

    public MiddleSouthWinNumberGroup(String winNumber) {
        init(winNumber);
    }

    public void init(String winNumber) {
        if(StrUtil.isEmpty(winNumber)) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_NULL_ERROR.getMsg();
            return;
        }
        String[] array = winNumber.split(Splitter.SectionSplitter);
        List<String> winNumberList = Arrays.asList(array);
        if (CollUtil.isEmpty(winNumberList)) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_NULL_ERROR.getMsg();
            return;
        }
        if (winNumberList.size() != MiddleSouthConfig.WIN_NUMBER_GROUP_SIZE) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_GROUP_SIZE_ERROR.getMsg();
            return;
        }
        //TODO 没有做section重复检测，暂时不确定同奖级（或不同奖级）的多组中奖号码之间是否可以重复
        for (int i = 0; i < winNumberList.size(); i++) {
            if (i == 0) {
                process(winNumberList.get(i), CONSTRAINT_SPECIAL, EnumPrizeLevel.special);
            } else if (i == 1) {
                process(winNumberList.get(i), CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.one);
            } else if (i == 2) {
                process(winNumberList.get(i), CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.two);
            } else if (i == 3 || i == 4) {
                process(winNumberList.get(i), CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.three);
            } else if (i < 12) {
                process(winNumberList.get(i), CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.four);
            } else if (i == 12) {
                process(winNumberList.get(i), CONSTRAINT_FIVE_SIX, EnumPrizeLevel.five);
            } else if (i < 16) {
                process(winNumberList.get(i), CONSTRAINT_FIVE_SIX, EnumPrizeLevel.six);
            } else if (i == 16) {
                process(winNumberList.get(i), CONSTRAINT_SEVEN, EnumPrizeLevel.seven);
            } else {
                process(winNumberList.get(i), CONSTRAINT_EIGHT, EnumPrizeLevel.eight);
            }
        }
    }

    private void process(String winNumber, SectionParam constraint, Level level) {
        String[] array = winNumber.split(Splitter.NumSplitter);
        if (array.length < constraint.getMinSize() || array.length > constraint.getMaxSize()) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_SIZE_ERROR.getMsg();
            return;
        }
        List<Integer> numList = new ArrayList<>();
        for (String s : array) {
            int num = Integer.parseInt(s);
            if (num > MiddleSouthConfig.MAX_NUM || num < MiddleSouthConfig.MIN_NUM) {
                valid = false;
                error = EnumCommonError.WIN_NUMBER_NUM_RANGE_ERROR.getMsg();
                return;
            }
            numList.add(num);
        }
        if (!constraint.isRepeatable() && numList.size() != new LinkedHashSet<>(numList).size()) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_REPEAT_ERROR.getMsg();
            return;
        }
        MiddleSouthWinNumber winNumberItem = new MiddleSouthWinNumber();
        winNumberItem.setLevel(level);
        winNumberItem.addAll(numList);
        this.winNumberList.add(winNumberItem);
    }

    @Override
    public boolean valid() {
        return valid;
    }

    @Override
    public String error() {
        return error;
    }

    @Override
    public WinNumber first() {
        return winNumberList.getFirst();
    }

    @Override
    public WinNumber get(int i) {
        return winNumberList.get(i);
    }

    @Override
    public List<WinNumber> getWinNumberList() {
        return winNumberList;
    }

    @Override
    public WinNumberGroup add(WinNumber winNumber) {
        winNumberList.add(winNumber);
        return this;
    }

    @Override
    public WinNumberGroup addAll(List<WinNumber> list) {
        winNumberList.addAll(list);
        return this;
    }

    @Override
    public String toString() {
        return this.winNumberList.stream().map(WinNumber::toString).collect(Collectors.joining(Delimiter.SectionDelimiter));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MiddleSouthWinNumberGroup that)) return false;
        return Objects.equals(this.winNumberList, that.winNumberList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(winNumberList);
    }

    public static WinNumberGroup generate() {
        WinNumberGroup winNumberGroup = new MiddleSouthWinNumberGroup();
        for (int i = 0; i < MiddleSouthConfig.WIN_NUMBER_GROUP_SIZE; i++) {
            if (i == 0) {
                processGenerate(winNumberGroup, CONSTRAINT_SPECIAL, EnumPrizeLevel.special);
            } else if (i == 1) {
                processGenerate(winNumberGroup, CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.one);
            } else if (i == 2) {
                processGenerate(winNumberGroup, CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.two);
            } else if (i == 3 || i == 4) {
                processGenerate(winNumberGroup, CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.three);
            } else if (i < 12) {
                processGenerate(winNumberGroup, CONSTRAINT_ONE_TO_FOUR, EnumPrizeLevel.four);
            } else if (i == 12) {
                processGenerate(winNumberGroup, CONSTRAINT_FIVE_SIX, EnumPrizeLevel.five);
            } else if (i < 16) {
                processGenerate(winNumberGroup, CONSTRAINT_FIVE_SIX, EnumPrizeLevel.six);
            } else if (i == 16) {
                processGenerate(winNumberGroup, CONSTRAINT_SEVEN, EnumPrizeLevel.seven);
            } else {
                processGenerate(winNumberGroup, CONSTRAINT_EIGHT, EnumPrizeLevel.eight);
            }
        }
        return winNumberGroup;
    }

    private static void processGenerate(WinNumberGroup winNumberGroup, SectionParam constraint, Level level) {
        List<Integer> numList = RandomWinNumberGenerator.generate(constraint);
        MiddleSouthWinNumber winNumber = new MiddleSouthWinNumber();
        winNumber.setLevel(level);
        winNumber.addAll(numList);
        winNumberGroup.add(winNumber);
    }

}
