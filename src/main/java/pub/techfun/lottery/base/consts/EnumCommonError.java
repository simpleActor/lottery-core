package pub.techfun.lottery.base.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCommonError {

    BET_FORMAT_ERROR("注单格式化出错"),
    BET_NULL_ERROR("注单不能为空"),
    BET_CHARACTER_ERROR("注单包含非法字符"),
    BET_NUM_RANGE_ERROR("注单数字取值范围错误"),
    BET_REGION_SIZE_ERROR("注单region长度错误"),
    BET_SECTION_SIZE_ERROR("注单section长度错误"),
    BET_SECTION_NULL_ERROR("注单包含空section错误"),
    WIN_NUMBER_NULL_ERROR("开奖号码不能为空"),
    WIN_NUMBER_NUM_RANGE_ERROR("开奖号码数字取值范围错误"),
    WIN_NUMBER_SIZE_ERROR("开奖号码长度错误"),
    WIN_NUMBER_REPEAT_ERROR("开奖号码数字不能重复"),
    WIN_NUMBER_INDEX_ERROR("计算器开奖号码索引错误"),
    WIN_NUMBER_GROUP_SIZE_ERROR("开奖号码组数错误"),
    ;

    final String msg;

}
