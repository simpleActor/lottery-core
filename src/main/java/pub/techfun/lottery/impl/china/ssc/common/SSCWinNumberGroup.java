package pub.techfun.lottery.impl.china.ssc.common;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import pub.techfun.lottery.base.consts.Delimiter;
import pub.techfun.lottery.base.consts.EnumCommonError;
import pub.techfun.lottery.base.consts.Splitter;
import pub.techfun.lottery.base.generator.RandomWinNumberGenerator;
import pub.techfun.lottery.base.generator.SectionParam;
import pub.techfun.lottery.base.winnumber.WinNumber;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.impl.china.ssc.consts.SSCConfig;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class SSCWinNumberGroup implements WinNumberGroup {

    /** 中奖号码约束 */
    private static final SectionParam WIN_NUMBER_CONSTRAINT = new SectionParam(0, 9, 5, 5, true, true);
    /** 中奖号码列表 */
    private final List<WinNumber> winNumberList = new ArrayList<>();
    /** 是否有效 */
    private boolean valid = true;
    /** 错误信息 */
    private String error;

    public SSCWinNumberGroup(String winNumber) {
        init(winNumber);
    }

    private void init(String winNumber) {
        if(StrUtil.isBlank(winNumber)) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_NULL_ERROR.getMsg();
            return;
        }
        String[] array = winNumber.split(Splitter.NumSplitter);
        if(array.length != SSCConfig.WIN_NUMBER_SIZE) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_SIZE_ERROR.getMsg();
            return;
        }
        List<Integer> numList = new ArrayList<>();
        for (String s : array) {
            int num = Integer.parseInt(s);
            if(num > SSCConfig.MAX_NUM || num < SSCConfig.MIN_NUM) {
                valid = false;
                error = EnumCommonError.WIN_NUMBER_NUM_RANGE_ERROR.getMsg();
                return;
            }
            numList.add(num);
        }
        if(!SSCConfig.REPEATABLE && numList.size() != new LinkedHashSet<>(numList).size()) {
            valid = false;
            error = EnumCommonError.WIN_NUMBER_REPEAT_ERROR.getMsg();
            return;
        }
        this.winNumberList.add(new SSCWinNumber().addAll(numList));
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
        if (!(o instanceof SSCWinNumberGroup that)) return false;
        return Objects.equals(this.winNumberList, that.winNumberList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(winNumberList);
    }

    public static WinNumberGroup generate() {
        List<Integer> numList = RandomWinNumberGenerator.generate(WIN_NUMBER_CONSTRAINT);
        SSCWinNumberGroup winNumberGroup = new SSCWinNumberGroup();
        winNumberGroup.add(new SSCWinNumber().addAll(numList));
        return winNumberGroup;
    }

}
