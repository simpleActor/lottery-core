package pub.techfun.lottery.base.bet;

import cn.hutool.core.collection.CollUtil;
import lombok.NoArgsConstructor;
import pub.techfun.lottery.base.consts.Delimiter;
import pub.techfun.lottery.base.consts.Splitter;

import java.util.*;

@NoArgsConstructor
public class BetNumSection implements BetSection {

    private final List<Integer> numList = new ArrayList<>();

    public BetNumSection(String section) {
        Arrays.stream(section.split(Splitter.NumSplitter)).forEach(a -> {
            this.numList.add(Integer.valueOf(a));
        });
    }

    public BetNumSection(List<Integer> numList) {
        this.numList.addAll(numList);
    }

    public BetNumSection(Integer... nums) {
        this.numList.addAll(Arrays.stream(nums).toList());
    }

    @Override
    public List<Integer> getNumList() {
        return this.numList;
    }

    @Override
    public BetSection add(Integer num) {
        this.numList.add(num);
        return this;
    }

    @Override
    public BetSection addAll(List<Integer> numList) {
        this.numList.addAll(numList);
        return this;
    }

    @Override
    public BetSection clear() {
        this.numList.clear();
        return this;
    }

    @Override
    public int size() {
        return this.numList.size();
    }

    @Override
    public Integer get(int i) {
        return this.numList.get(i);
    }

    @Override
    public boolean contains(Integer num) {
        return new HashSet<>(this.numList).contains(num);
    }

    @Override
    public boolean contains(Integer... nums) {
        return new HashSet<>(this.numList).containsAll(Arrays.stream(nums).toList());
    }

    @Override
    public boolean contains(Collection<Integer> numList) {
        return new HashSet<>(this.numList).containsAll(numList);
    }

    @Override
    public boolean containsPosMatch(Integer num) {
        return this.numList.getFirst().equals(num);
    }

    @Override
    public boolean containsPosMatch(Integer... nums) {
        if(size() < nums.length) {
            return false;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!this.numList.get(i).equals(nums[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsPosMatch(List<Integer> numList) {
        if(size() < numList.size()) {
            return false;
        }
        for (int i = 0; i < numList.size(); i++) {
            if (!this.numList.get(i).equals(numList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return CollUtil.join(this.numList, Delimiter.NumDelimiter);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BetNumSection that)) return false;
        return Objects.equals(this.numList, that.numList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numList);
    }

}
