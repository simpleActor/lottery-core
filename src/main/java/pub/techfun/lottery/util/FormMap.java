package pub.techfun.lottery.util;

import pub.techfun.lottery.base.consts.EnumForm;

import java.util.LinkedHashMap;

public class FormMap extends LinkedHashMap<EnumForm, Integer> {

    /**
     * 根据形态获取次数
     */
    public Integer getByForm(EnumForm form) {
        return get(form);
    }

}
