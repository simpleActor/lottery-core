package pub.techfun.lottery.impl.china.ssc.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.Pos;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum EnumSSCPos implements Pos {

	WAN(0, "万"),
	QIAN(1, "千"),
	BAI(2, "百"),
	SHI(3, "十"),
	GE(4, "个"),
	;
	
	final int pos;
	final String value;

	private static final Map<Integer, EnumSSCPos> POS_MAP = new HashMap<>();

	static {
		for (EnumSSCPos item : EnumSSCPos.values()) {
			POS_MAP.put(item.pos, item);
		}
	}

	public static EnumSSCPos get(Integer pos) {
		return POS_MAP.get(pos);
	}

	@Override
	public int pos() {
		return this.pos;
	}

	@Override
	public String value() {
		return this.value;
	}

}
