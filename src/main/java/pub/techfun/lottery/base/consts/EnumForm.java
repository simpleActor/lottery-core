package pub.techfun.lottery.base.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.Color;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumForm implements Color {
	
	BIG("大"),
	SMALL("小"),
	ODD("单"),
	EVEN("双"),
	;

	final String value;

	private static final Map<String, EnumForm> MAP = new HashMap<>();

	static {
		for (EnumForm item : EnumForm.values()) {
			MAP.put(item.value, item);
		}
	}

	public static EnumForm get(String name) {
		return MAP.get(name);
	}
	
	@Override
	public String value() {
		return this.value;
	}

}
