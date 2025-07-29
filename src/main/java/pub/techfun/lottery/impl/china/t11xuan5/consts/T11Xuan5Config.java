package pub.techfun.lottery.impl.china.t11xuan5.consts;

public interface T11Xuan5Config {

    /** 所有开奖号码可能数 */
    double WIN_NUMBER_COUNT = 55440;
    /** 开奖号码长度*/
    int WIN_NUMBER_SIZE = 5;
    /** 数字最小值 */
    int MIN_NUM = 1;
    /** 数字最大值 */
    int MAX_NUM = 11;
    /** 开奖号码数字是否可重复 */
    boolean REPEATABLE = false;
    /** 开奖号码数字是否有序 */
    boolean POS_RELATED = false;

}
