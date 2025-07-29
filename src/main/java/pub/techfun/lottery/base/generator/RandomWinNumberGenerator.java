package pub.techfun.lottery.base.generator;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.*;

@Slf4j
public class RandomWinNumberGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static List<Integer> generate(SectionParam sectionParam) {
        check(sectionParam);
        return processGenerate(sectionParam);
    }

    private static List<Integer> processGenerate(SectionParam sectionParam) {
        int sectionNumSize = random.nextInt(sectionParam.getMinSize(), sectionParam.getMaxSize() + 1);
        List<Integer> numList = new ArrayList<>();
        for (int x = 0; x < sectionNumSize; x++) {
            int num = random.nextInt(sectionParam.getMinNum(), sectionParam.getMaxNum() + 1);
            numList.add(num);
        }
        if (!sectionParam.isRepeatable()) {
            Set<Integer> numSet = new LinkedHashSet<>(numList);
            while (numSet.size() < sectionParam.getMinSize()) {
                int num = random.nextInt(sectionParam.getMinNum(), sectionParam.getMaxNum() + 1);
                numSet.add(num);
            }
            numList.clear();
            numList.addAll(numSet);
            if (!sectionParam.isPosRelated()) {
                Collections.sort(numList);
            }
        }
        return numList;
    }

    private static void check(SectionParam sectionParam) {
        if (sectionParam.getMaxSize() < sectionParam.getMinSize()) {
            throw new RuntimeException("sectionParam size error");
        }
        if (sectionParam.getMaxNum() < sectionParam.getMinNum()) {
            throw new RuntimeException("sectionParam num range error");
        }
    }

}
