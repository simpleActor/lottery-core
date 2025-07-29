package pub.techfun.lottery.base.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.Color;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumColor implements Color {
	
	RED("红"),
	GREEN("绿"),
	BLUE("蓝"),
	;

	final String value;

	private static final Map<String, EnumColor> COLOR_MAP = new HashMap<>();

	static {
		for (EnumColor item : EnumColor.values()) {
			COLOR_MAP.put(item.value, item);
		}
	}

	public static EnumColor get(String name) {
		return COLOR_MAP.get(name);
	}
	
	@Override
	public String value() {
		return this.value;
	}

}
