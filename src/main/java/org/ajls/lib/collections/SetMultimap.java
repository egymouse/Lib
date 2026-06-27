package org.ajls.lib.collections;

import java.util.Map;
import java.util.Set;

/**
 * 一个 key 可以对应多个不重复 value 的 Map。
 *
 * 语义类似：
 *
 * Map<K, Set<V>>
 *
 * 注意：
 * - size() 返回的是 entry 数量，不是 key 数量。
 * - get(key) 返回只读 Set。
 */
public interface SetMultimap<K, V> {

    /**
     * 添加一个 key-value 关系。
     *
     * @return 如果之前不存在该关系，返回 true；否则返回 false。
     */
    boolean put(K key, V value);

    /**
     * 批量添加一个 key 对应的多个 value。
     */
    default boolean putAll(K key, Iterable<? extends V> values) {
        boolean changed = false;
        for (V value : values) {
            changed |= put(key, value);
        }
        return changed;
    }

    /**
     * 获取 key 对应的 values。
     *
     * 返回只读 Set。若 key 不存在，返回空 Set。
     */
    Set<V> get(K key);

    /**
     * 删除一个 key-value 关系。
     *
     * @return 如果之前存在该关系，返回 true；否则返回 false。
     */
    boolean remove(K key, V value);

    /**
     * 删除某个 key 对应的所有 values。
     *
     * @return 被删除的 values，只读 Set。
     */
    Set<V> removeAll(K key);

    /**
     * 是否包含某个 key-value 关系。
     */
    boolean containsEntry(K key, V value);

    /**
     * 是否包含某个 key。
     */
    boolean containsKey(K key);

    /**
     * 是否包含某个 value。
     */
    boolean containsValue(V value);

    /**
     * 所有 key。
     *
     * 返回只读 Set。
     */
    Set<K> keySet();

    /**
     * 所有 key-value entry。
     *
     * 返回只读快照。
     */
    Set<Map.Entry<K, V>> entries();

    /**
     * 返回 Map<K, Set<V>> 形式的只读快照。
     */
    Map<K, Set<V>> asMap();

    /**
     * key-value 关系数量。
     *
     * 例如：
     *
     * a -> 1
     * a -> 2
     * b -> 1
     *
     * size() == 3
     */
    int size();

    /**
     * key 的数量。
     */
    int keySize();

    /**
     * 是否为空。
     */
    boolean isEmpty();

    /**
     * 清空。
     */
    void clear();
}
