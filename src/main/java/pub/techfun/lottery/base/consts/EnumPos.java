package pub.techfun.lottery.base.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pub.techfun.lottery.base.Pos;

import java.util.HashMap;
import java.util.Map;

/**
 * 数字彩票的开奖号码位置,从左往右,从0开始
 * 如：时时彩位置就是0|1|2|3|4（表示万|千|百|十|个）
 * 目前已知最长的 <快乐8> 开奖号码位置是 0-19
 *
 */
@Getter
@AllArgsConstructor
public enum EnumPos implements Pos {
	
	zero(0),one(1),two(2),three(3),four(4),
	five(5), six(6),seven(7),eight(8),nine(9),
	ten(10), eleven(11),twelve(12),thirteen(13),fourteen(14),
	fifteen(15), sixteen(16),seventeen(17),eighteen(18),nineteen(19),
	
	;
	
	final int pos;

	private static final Map<Integer, EnumPos> POS_MAP = new HashMap<>();

	static {
		for (EnumPos item : EnumPos.values()) {
			POS_MAP.put(item.pos, item);
		}
	}

	public static EnumPos get(Integer pos) {
		return POS_MAP.get(pos);
	}
	
	@Override
	public int pos() {
		return this.pos;
	}

	@Override
	public String value() {
		return this.name();
	}

}
