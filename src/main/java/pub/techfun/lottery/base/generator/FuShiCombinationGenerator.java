package pub.techfun.lottery.base.generator;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.base.bet.BetNumSection;
import pub.techfun.lottery.base.bet.BetRegion;
import pub.techfun.lottery.base.bet.BetSection;

import java.util.ArrayList;
import java.util.List;

public class FuShiCombinationGenerator {

    public static void main(String[] args) {
        RegionParam regionParam = new RegionParam(4, 4, false);
        SectionParam sectionParam = new SectionParam(0, 9, 1, 10, false, false);
        BetRegion betRegion = RandomBetGenerator.generate(regionParam, sectionParam);
        System.out.println("betRegion: " + betRegion);
        List<BetSection> cover = generateAll(betRegion.getSectionList());
        System.out.println("size: " + cover.size());
        System.out.println(JSON.toJSONString(cover));
    }

    public static List<BetSection> generateByNumsList(List<List<Integer>> numsList) {
        List<BetSection> result = new ArrayList<>();
        generateByNumsList(numsList, 0, new ArrayList<>(), result);
        return result;
    }

    private static void generateByNumsList(List<List<Integer>> numsList,
                                           int depth,
                                           List<Integer> currentNums,
                                           List<BetSection> result) {
        if (depth == numsList.size()) {
            BetNumSection section = new BetNumSection();
            section.addAll(currentNums);
            result.add(section);
            return;
        }

        List<Integer> numList = numsList.get(depth);
        for (Integer num : numList) {
            currentNums.addLast(num);
            generateByNumsList(numsList, depth + 1, currentNums, result);
            currentNums.removeLast();
        }
    }

    public static List<BetSection> generateAll(List<BetSection> sectionList) {
        List<BetSection> result = new ArrayList<>();
        generate(sectionList, 0, new ArrayList<>(), result);
        return result;
    }

    private static void generate(List<BetSection> sectionList,
                                 int depth,
                                 List<Integer> currentNums,
                                 List<BetSection> result) {
        if (depth == sectionList.size()) {
            BetNumSection section = new BetNumSection();
            section.addAll(currentNums);
            result.add(section);
            return;
        }

        BetSection section = sectionList.get(depth);
        for (Integer num : section.getNumList()) {
            currentNums.addLast(num);
            generate(sectionList, depth + 1, currentNums, result);
            currentNums.removeLast();
        }
    }

}

