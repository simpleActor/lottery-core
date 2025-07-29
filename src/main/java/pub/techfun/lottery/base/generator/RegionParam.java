package pub.techfun.lottery.base.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionParam {

    /** 按域"|"分隔后的最小长度 */
    private int minSize;
    /** 按域"|"分隔后的最大长度 */
    private int maxSize;
    /** section是否可重复，整个section和另一个section比较 */
    private boolean repeatable;

}
