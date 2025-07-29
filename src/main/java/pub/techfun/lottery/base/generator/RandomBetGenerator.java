package pub.techfun.lottery.base.generator;

import lombok.extern.slf4j.Slf4j;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetSection;
import pub.techfun.lottery.base.bet.BetNumRegion;
import pub.techfun.lottery.base.bet.BetNumSection;

import java.util.*;

@Slf4j
public class RandomBetGenerator {

    private static final Random random = new Random();

    public static BetRegion generate(RegionParam regionParam, SectionParam sectionParam) {
        check(regionParam, sectionParam);
        BetNumRegion region = processGenerate(regionParam, sectionParam);
        repeatableFix(regionParam, sectionParam, region);
        return region;
    }

    private static void repeatableFix(RegionParam regionParam, SectionParam sectionParam, BetNumRegion region) {
        if (!sectionParam.isRepeatable()) {
            for (BetSection section : region.getSectionList()) {
                int x = 100;
                Set<Integer> numSet = new LinkedHashSet<>(section.getNumList());
                while (numSet.size() < sectionParam.getMinSize() && x > 0) {
                    int num = random.nextInt(sectionParam.getMinNum(), sectionParam.getMaxNum() + 1);
                    numSet.add(num);
                    x--;
                }
                if (numSet.size() < sectionParam.getMinSize()) {
                    log.warn("section repeatable fix failed, maybe you can try again...");
                }
                if (!sectionParam.isPosRelated()) {
                    section.clear().addAll(numSet.stream().sorted().toList());
                } else {
                    section.clear().addAll(numSet.stream().toList());
                }
            }
        }
        if (!regionParam.isRepeatable()) {
            Set<BetSection> sectionSet = new LinkedHashSet<>();
            if (!sectionParam.isPosRelated()) {
                sectionSet.addAll(region.getSectionList().stream().peek(
                        section -> Collections.sort(section.getNumList())).toList());
            } else {
                sectionSet.addAll(region.getSectionList());
            }
            int x = 100;
            while (sectionSet.size() < regionParam.getMinSize() && x > 0) {
                BetNumSection section = generateSection(sectionParam);
                if (!sectionParam.isPosRelated()) {
                    Collections.sort(section.getNumList());
                }
                sectionSet.add(section);
                x--;
            }
            if(sectionSet.size() < regionParam.getMinSize()) {
                log.warn("region repeatable fix failed, maybe you can try again...");
            }
            region.clear().addAll(sectionSet.stream().toList());
        }
    }

    private static BetNumRegion processGenerate(RegionParam regionParam, SectionParam sectionParam) {
        BetNumRegion region = new BetNumRegion();
        int sectionCount = random.nextInt(regionParam.getMinSize(), regionParam.getMaxSize() + 1);
        for (int i = 0; i < sectionCount; i++) {
            BetNumSection section = generateSection(sectionParam);
            region.add(section);
        }
        return region;
    }

    private static BetNumSection generateSection(SectionParam sectionParam) {
        int size = random.nextInt(sectionParam.getMinSize(), sectionParam.getMaxSize() + 1);
        BetNumSection section = new BetNumSection();
        for (int x = 0; x < size; x++) {
            int num = random.nextInt(sectionParam.getMinNum(), sectionParam.getMaxNum() + 1);
            section.add(num);
        }
        return section;
    }

    private static void check(RegionParam regionParam, SectionParam sectionParam) {
        if (regionParam.getMaxSize() < regionParam.getMinSize()) {
            throw new RuntimeException("regionParam size error");
        }
        if (sectionParam.getMaxSize() < sectionParam.getMinSize()) {
            throw new RuntimeException("sectionParam size error");
        }
        if (sectionParam.getMaxNum() < sectionParam.getMinNum()) {
            throw new RuntimeException("sectionParam num range error");
        }
    }

}
