package pub.techfun.lottery.algorithm.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumMode {

    AUTO(5),
    STRONG(10),
    WEAK(3),
    SAVAGE(30),
    MANUAL(0),
    RELEASE(5),
    ;

    final int count;

}
