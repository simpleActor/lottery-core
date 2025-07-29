package pub.techfun.lottery.base.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectionParam {

    /** 最小数字 */
    private int minNum;
    /** 最大数字 */
    private int maxNum;
    /** 单个section按","分割后最小长度 */
    private int minSize;
    /** 单个section按","分割后最大长度 */
    private int maxSize;
    /** 数字是否可重复 */
    private boolean repeatable;
    /** 是否有位置关系 */
    private boolean posRelated;

}
