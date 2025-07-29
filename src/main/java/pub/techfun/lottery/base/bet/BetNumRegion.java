package pub.techfun.lottery.base.bet;

import lombok.NoArgsConstructor;
import pub.techfun.lottery.base.consts.Delimiter;
import pub.techfun.lottery.base.consts.Splitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
public class BetNumRegion implements BetRegion {

    private final List<BetSection> sectionList = new ArrayList<>();

    public BetNumRegion(String bet) {
        Arrays.stream(bet.split(Splitter.SectionSplitter)).forEach(a -> {
            this.sectionList.add(new BetNumSection(a));
        });
    }

    public BetNumRegion(List<List<Integer>> bet) {
        bet.forEach(section -> {
            this.sectionList.add(new BetNumSection(section));
        });
    }

    @Override
    public List<BetSection> getSectionList() {
        return this.sectionList;
    }

    @Override
    public BetRegion add(BetSection section) {
        this.sectionList.add(section);
        return this;
    }

    @Override
    public BetRegion addAll(List<BetSection> sectionList) {
        this.sectionList.addAll(sectionList);
        return this;
    }

    @Override
    public BetRegion clear() {
        this.sectionList.clear();
        return this;
    }

    @Override
    public int size() {
        return this.sectionList.size();
    }

    @Override
    public BetSection get(int i) {
        return this.sectionList.get(i);
    }

    @Override
    public boolean contains(BetSection section) {
        return this.sectionList.stream().anyMatch(sec -> sec.contains(section.getNumList()));
    }

    @Override
    public boolean contains(BetSection... sections) {
        for (BetSection section : sections) {
            boolean contains = this.sectionList.stream().anyMatch(sec -> sec.contains(section.getNumList()));
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(List<BetSection> sectionList) {
        for (BetSection section : sectionList) {
            boolean contains = this.sectionList.stream().anyMatch(sec -> sec.contains(section.getNumList()));
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsPosMatch(BetSection section) {
        return this.sectionList.getFirst().containsPosMatch(section.getNumList());
    }

    @Override
    public boolean containsPosMatch(BetSection... sections) {
        if(size() < sections.length) {
            return false;
        }
        for (int i = 0; i < sections.length; i++) {
            if (!this.sectionList.get(i).containsPosMatch(sections[i].getNumList())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsPosMatch(List<BetSection> sectionList) {
        if(size() < sectionList.size()) {
            return false;
        }
        for (int i = 0; i < sectionList.size(); i++) {
            if (!this.sectionList.get(i).containsPosMatch(sectionList.get(i).getNumList())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.sectionList.stream().map(Object::toString).collect(Collectors.joining(Delimiter.SectionDelimiter));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BetNumRegion that)) return false;
        return Objects.equals(this.sectionList, that.sectionList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sectionList);
    }

}
