package pub.techfun.lottery.impl.vietnam.middlesouth.common;

import lombok.Data;
import pub.techfun.lottery.base.result.WinResult;
import pub.techfun.lottery.base.result.WinResultGroup;

import java.util.ArrayList;
import java.util.List;

@Data
public class MiddleSouthWinResultGroup implements WinResultGroup {

    /** 是否有效 */
    private boolean valid = true;
    /** 错误信息 */
    private String error;
    /** 注数 */
    private long count;
    /** 中奖结果列表 */
    private final List<WinResult> winResultList = new ArrayList<>();

    @Override
    public boolean valid() {
        return valid;
    }

    @Override
    public String error() {
        return error;
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public WinResult first() {
        return winResultList.getFirst();
    }

    @Override
    public WinResult get(int i) {
        return winResultList.get(i);
    }

    @Override
    public List<WinResult> getWinResultList() {
        return winResultList;
    }

    @Override
    public WinResultGroup add(WinResult winResult) {
        winResultList.add(winResult);
        return this;
    }

    @Override
    public WinResultGroup addAll(List<WinResult> winResults) {
        winResultList.addAll(winResults);
        return this;
    }

}
