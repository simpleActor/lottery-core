package pub.techfun.lottery.base.processor;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.consts.EnumCommonError;
import pub.techfun.lottery.base.result.FormatResult;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * 注单格式化处理器
 */
@FunctionalInterface
public interface BetFormatter {

    Pattern PATTERN = Pattern.compile("^\\[[01]+][0-9,|]+$");

    FormatResult format(String bet);

    static FormatResult processFormat(String bet) {
        FormatResult result = new FormatResult();
        if (!PATTERN.matcher(bet).matches()) {
            result.setValid(false).setError(EnumCommonError.BET_FORMAT_ERROR.getMsg());
            return result;
        }
        String[] arr = bet.split("]");
        if (arr.length != 2) {
            throw new RuntimeException("Invalid bet format: " + bet);
        }
        String head = arr[0].replace("[", "");
        int[] indexes = IntStream.range(0, head.length())
                .filter(i -> head.charAt(i) == '1')
                .toArray();
        result.setBet(arr[1]);
        result.setIndexes(indexes);
        return result;
    }

}
