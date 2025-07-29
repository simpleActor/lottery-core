package pub.techfun.lottery.base;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumLottery implements Num {

    private Integer value;
    private Pos pos;
    private Color color;

    public NumLottery(Integer value) {
        this.value = value;
    }

    public NumLottery(Integer value, Pos pos) {
        this.value = value;
        this.pos = pos;
    }

    public NumLottery(Integer value, Color color) {
        this.value = value;
        this.color = color;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public Pos pos() {
        return pos;
    }

    @Override
    public Color color() {
        return color;
    }

    /**
     * 默认升序
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Num o) {
        return this.value() - o.value();
    }

}
