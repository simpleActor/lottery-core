package pub.techfun.lottery.base.winnumber;

import java.util.List;

public interface WinNumberGroup {

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

    WinNumber first();

    WinNumber get(int i);

    List<WinNumber> getWinNumberList();

    WinNumberGroup add(WinNumber winNumber);

    WinNumberGroup addAll(List<WinNumber> list);

}
