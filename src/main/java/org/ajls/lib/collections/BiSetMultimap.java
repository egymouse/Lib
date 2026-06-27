package org.ajls.lib.collections;

import java.util.Map;
import java.util.Set;

/**
 * 双向 SetMultimap。
 *
 * 语义类似：
 *
 * forward:
 * Map<K, Set<V>>
 *
 * backward:
 * Map<V, Set<K>>
 */
public interface BiSetMultimap<K, V> extends SetMultimap<K, V> {

    /**
     * 根据 value 反查所有 key。
     *
     * 返回只读 Set。
     */
    Set<K> getKeys(V value);

    /**
     * 删除某个 value 对应的所有 key-value 关系。
     *
     * 例如：
     *
     * a -> 1
     * b -> 1
     *
     * removeAllByValue(1) 会删除 a -> 1 和 b -> 1。
     *
     * @return 被删除的 keys，只读 Set。
     */
    Set<K> removeAllByValue(V value);

    /**
     * 所有 value。
     *
     * 返回只读 Set。
     */
    Set<V> valueSet();

    /**
     * 返回反向 Map<V, Set<K>> 的只读快照。
     */
    Map<V, Set<K>> inverseAsMap();

    /**
     * 返回反向视图。
     *
     * 注意：这是 live view，对 inverse() 的修改会影响原对象。
     */
    BiSetMultimap<V, K> inverse();
}
