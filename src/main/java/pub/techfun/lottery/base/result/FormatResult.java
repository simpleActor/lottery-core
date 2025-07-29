package pub.techfun.lottery.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FormatResult {

    /** 是否有效 */
    private boolean valid = true;
    /** 错误信息 */
    private String error;
    /** 从注单提取的索引 */
    private int[] indexes;
    /** 格式化后的注单 */
    private String bet;

    public FormatResult(boolean valid, String error) {
        this.valid = valid;
        this.error = error;
    }

    public FormatResult(String bet) {
        this.bet = bet;
    }

    public FormatResult(String bet, int[] indexes) {
        this.bet = bet;
        this.indexes = indexes;
    }

}
