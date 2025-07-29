package pub.techfun.lottery.impl.china.ssc.consts;

public interface SSCConfig {

    /** 所有开奖号码可能数 */
    double ALL_FACTOR = 100000;
    /** 开奖号码长度 */
    int WIN_NUMBER_SIZE = 5;
    /** 数字最小值 */
    int MIN_NUM = 0;
    /** 数字最大值 */
    int MAX_NUM = 9;
    /** 开奖号码数字是否可重复 */
    boolean REPEATABLE = true;
    /** 开奖号码数字顺序是否有关 */
    boolean POS_RELATED = true;

}
