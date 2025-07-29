package pub.techfun.lottery.base;

public interface Num extends Comparable<Num> {

    /**
     * 值
     * @return
     */
    Integer value();

    /**
     * 位置
     * @return
     */
    Pos pos();

    /**
     * 颜色
     * @return
     */
    Color color();

}

