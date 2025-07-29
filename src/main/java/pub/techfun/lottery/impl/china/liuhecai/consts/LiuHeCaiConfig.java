package pub.techfun.lottery.impl.china.liuhecai.consts;

public interface LiuHeCaiConfig {

    /** 所有开奖号码可能数 */
    double WIN_NUMBER_COUNT = 601303088;
    /** 开奖号码长度 */
    int WIN_NUMBER_SIZE = 7;
    /** 数字最小值 */
    int MIN_NUM = 1;
    /** 数字最大值 */
    int MAX_NUM = 49;
    /** 开奖号码数字是否可重复 */
    boolean REPEATABLE = false;

}
