package pub.techfun.lottery.impl.vietnam.middlesouth.common;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import pub.techfun.lottery.base.Level;
import pub.techfun.lottery.base.consts.Delimiter;
import pub.techfun.lottery.base.winnumber.WinNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class MiddleSouthWinNumber implements WinNumber {

    /** 中奖号码数字列表 */
    private List<Integer> numList = new ArrayList<>();
    /** 奖级 */
    private Level level;

    @Override
    public WinNumber add(Integer num) {
        this.numList.add(num);
        return this;
    }

    @Override
    public WinNumber addAll(List<Integer> numList) {
        this.numList.addAll(numList);
        return this;
    }

    @Override
    public void clear() {
        this.numList.clear();
    }

    @Override
    public int size() {
        return numList.size();
    }

    @Override
    public Integer get(int index) {
        return numList.get(index);
    }

    @Override
    public List<Integer> getNumList() {
        return this.numList;
    }

    @Override
    public Level level() {
        return this.level;
    }

    @Override
    public String toString() {
        return CollUtil.join(this.numList, Delimiter.NumDelimiter);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MiddleSouthWinNumber that)) return false;
        return Objects.equals(numList, that.numList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numList);
    }

}
