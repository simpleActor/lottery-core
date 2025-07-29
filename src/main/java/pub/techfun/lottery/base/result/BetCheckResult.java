package pub.techfun.lottery.base.result;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class BetCheckResult {

    private boolean valid = true;

    private String error;

    public BetCheckResult() {}

    public BetCheckResult(boolean valid) {
        this.valid = valid;
    }

    public BetCheckResult(boolean valid, String error) {
        this.valid = valid;
        this.error = error;
    }

    public boolean valid() {
        return valid;
    }

    public String error() {
        return error;
    }

}

