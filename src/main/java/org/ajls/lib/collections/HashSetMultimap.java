package org.ajls.lib.collections;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 基于 HashMap + HashSet 的 SetMultimap 实现。
 *
 * 非线程安全。
 *
 * 支持 null key 和 null value，因为底层 HashMap / HashSet 支持。
 */
public class HashSetMultimap<K, V> implements SetMultimap<K, V> {

    private final Map<K, Set<V>> map;
    private int size;

    public HashSetMultimap() {
        this.map = new HashMap<>();
        this.size = 0;
    }

    @Override
    public boolean put(K key, V value) {
        Set<V> values = map.computeIfAbsent(key, k -> new HashSet<>());

        boolean added = values.add(value);
        if (added) {
            size++;
        }

        return added;
    }

    @Override
    public Set<V> get(K key) {
        Set<V> values = map.get(key);
        if (values == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(values);
    }

    @Override
    public boolean remove(K key, V value) {
        Set<V> values = map.get(key);
        if (values == null) {
            return false;
        }

        boolean removed = values.remove(value);
        if (!removed) {
            return false;
        }

        size--;

        if (values.isEmpty()) {
            map.remove(key);
        }

        return true;
    }

    @Override
    public Set<V> removeAll(K key) {
        Set<V> removed = map.remove(key);
        if (removed == null) {
            return Collections.emptySet();
        }

        size -= removed.size();

        return Collections.unmodifiableSet(new HashSet<>(removed));
    }

    @Override
    public boolean containsEntry(K key, V value) {
        Set<V> values = map.get(key);
        return values != null && values.contains(value);
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        for (Set<V> values : map.values()) {
            if (values.contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        Set<Map.Entry<K, V>> result = new HashSet<>();

        for (Map.Entry<K, Set<V>> entry : map.entrySet()) {
            K key = entry.getKey();
            for (V value : entry.getValue()) {
                result.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
            }
        }

        return Collections.unmodifiableSet(result);
    }

    @Override
    public Map<K, Set<V>> asMap() {
        return immutableMapCopy(map);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int keySize() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        map.clear();
        size = 0;
    }

    @Override
    public String toString() {
        return asMap().toString();
    }

    private static <K, V> Map<K, Set<V>> immutableMapCopy(Map<K, Set<V>> source) {
        Map<K, Set<V>> result = new HashMap<>();

        for (Map.Entry<K, Set<V>> entry : source.entrySet()) {
            result.put(
                    entry.getKey(),
                    Collections.unmodifiableSet(new HashSet<>(entry.getValue()))
            );
        }

        return Collections.unmodifiableMap(result);
    }
}
