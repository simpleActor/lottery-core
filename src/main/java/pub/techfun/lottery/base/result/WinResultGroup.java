package pub.techfun.lottery.base.result;

import java.util.List;

public interface WinResultGroup {

    /**
     * 是否有效
     * @return
     */
    boolean valid();

    /**
     * 错误信息
     * @return
     */
    String error();

    /**
     * 注数
     * @return
     */
    long count();

    WinResult first();

    WinResult get(int i);

    List<WinResult> getWinResultList();

    WinResultGroup add(WinResult winResult);

    WinResultGroup addAll(List<WinResult> winResultList);

}
