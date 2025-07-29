package pub.techfun.lottery.util;

import cn.hutool.core.collection.CollUtil;

import java.util.*;

public class CollectionUtil {

    /**
     * 两个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最少的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c]，此结果中只保留了两个c
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 交集的集合，返回 {@link ArrayList}
     */
    public static <T> List<T> intersection(Collection<T> coll1, Collection<T> coll2) {
        return (List<T>) CollUtil.intersection(coll1, coll2);
    }

    /**
     * 多个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最少的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c]，此结果中只保留了两个c
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 交集的集合，返回 {@link ArrayList}
     */
    @SafeVarargs
    public static <T> List<T> intersection(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        return (List<T>) CollUtil.intersection(coll1, coll2, otherColls);
    }

    /**
     * 多个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，只保留一个<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c]，此结果中只保留了一个c
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 交集的集合，返回 {@link LinkedHashSet}
     * @since 5.3.9
     */
    @SafeVarargs
    public static <T> Set<T> intersectionDistinct(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        return CollUtil.intersectionDistinct(coll1, coll2, otherColls);
    }

    /**
     * 两个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 并集的集合，返回 {@link ArrayList}
     */
    public static <T> List<T> union(Collection<T> coll1, Collection<T> coll2) {
        return (List<T>) CollUtil.union(coll1, coll2);
    }

    /**
     * 多个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 并集的集合，返回 {@link ArrayList}
     */
    @SafeVarargs
    public static <T> List<T> union(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        return (List<T>) CollUtil.union(coll1, coll2, otherColls);
    }

    /**
     * 多个集合的非重复并集，类似于SQL中的“UNION DISTINCT”<br>
     * 针对一个集合中存在多个相同元素的情况，只保留一个<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c]，此结果中只保留了一个c
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 并集的集合，返回 {@link LinkedHashSet}
     */
    @SafeVarargs
    public static <T> Set<T> unionDistinct(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        return CollUtil.unionDistinct(coll1, coll2, otherColls);
    }

    /**
     * 多个集合的完全并集，类似于SQL中的“UNION ALL”<br>
     * 针对一个集合中存在多个相同元素的情况，保留全部元素<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c, a, b, c, c]
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 并集的集合，返回 {@link ArrayList}
     */
    @SafeVarargs
    public static <T> List<T> unionAll(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        return CollUtil.unionAll(coll1, coll2, otherColls);
    }

    /**
     * 计算集合的单差集，即只返回【集合1】中有，但是【集合2】中没有的元素，例如：
     *
     * <pre>
     *     subtractToList([1,2,3,4],[2,3,4,5]) -》 [1]
     * </pre>
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @param <T>   元素类型
     * @return 单差集
     * @since 5.3.5
     */
    public static <T> List<T> subtract(Collection<T> coll1, Collection<T> coll2) {
        return CollUtil.subtractToList(coll1, coll2);
    }

    /**
     * 两个集合的对称差集 (A-B)∪(B-A)<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留两个集合中此元素个数差的个数<br>
     * 例如：
     *
     * <pre>
     *     disjunction([a, b, c, c, c], [a, b, c, c]) -》 [c]
     *     disjunction([a, b], [])                    -》 [a, b]
     *     disjunction([a, b, c], [b, c, d])          -》 [a, d]
     * </pre>
     * 任意一个集合为空，返回另一个集合<br>
     * 两个集合无差集则返回空集合
     *
     * @param <T>   集合元素类型
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 差集的集合，返回 {@link ArrayList}
     */
    public static <T> List<T> disjunction(Collection<T> coll1, Collection<T> coll2) {
        return (List<T>) CollUtil.disjunction(coll1, coll2);
    }

}
