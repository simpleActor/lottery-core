package pub.techfun.lottery.algorithm.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pub.techfun.lottery.algorithm.consts.EnumMode;
import pub.techfun.lottery.algorithm.consts.DefaultConfig;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigParam {

    private EnumMode mode = DefaultConfig.mode;
    private double sampleRate = DefaultConfig.sampleRate;

}
