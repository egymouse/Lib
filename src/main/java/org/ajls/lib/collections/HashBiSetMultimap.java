package org.ajls.lib.collections;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 基于两个 HashMap + HashSet 的双向 SetMultimap。
 *
 * 内部维护：
 *
 * forward:
 * Map<K, Set<V>>
 *
 * backward:
 * Map<V, Set<K>>
 *
 * 非线程安全。
 *
 * 支持 null key 和 null value，因为底层 HashMap / HashSet 支持。
 */
public class HashBiSetMultimap<K, V> implements BiSetMultimap<K, V> {

    private final Map<K, Set<V>> forward;
    private final Map<V, Set<K>> backward;
    private final Counter counter;

    public HashBiSetMultimap() {
        this.forward = new HashMap<>();
        this.backward = new HashMap<>();
        this.counter = new Counter();
    }

    private HashBiSetMultimap(
            Map<K, Set<V>> forward,
            Map<V, Set<K>> backward,
            Counter counter
    ) {
        this.forward = forward;
        this.backward = backward;
        this.counter = counter;
    }

    @Override
    public boolean put(K key, V value) {
        Set<V> values = forward.computeIfAbsent(key, k -> new HashSet<>());

        boolean added = values.add(value);
        if (!added) {
            return false;
        }

        backward.computeIfAbsent(value, v -> new HashSet<>()).add(key);
        counter.size++;

        return true;
    }

    @Override
    public Set<V> get(K key) {
        Set<V> values = forward.get(key);
        if (values == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(values);
    }

    @Override
    public Set<K> getKeys(V value) {
        Set<K> keys = backward.get(value);
        if (keys == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(keys);
    }

    @Override
    public boolean remove(K key, V value) {
        Set<V> values = forward.get(key);
        if (values == null) {
            return false;
        }

        boolean removed = values.remove(value);
        if (!removed) {
            return false;
        }

        if (values.isEmpty()) {
            forward.remove(key);
        }

        Set<K> keys = backward.get(value);
        if (keys != null) {
            keys.remove(key);
            if (keys.isEmpty()) {
                backward.remove(value);
            }
        }

        counter.size--;

        return true;
    }

    @Override
    public Set<V> removeAll(K key) {
        Set<V> removedValues = forward.remove(key);
        if (removedValues == null) {
            return Collections.emptySet();
        }

        Set<V> result = new HashSet<>(removedValues);

        for (V value : removedValues) {
            Set<K> keys = backward.get(value);
            if (keys != null) {
                keys.remove(key);
                if (keys.isEmpty()) {
                    backward.remove(value);
                }
            }
        }

        counter.size -= removedValues.size();

        return Collections.unmodifiableSet(result);
    }

    @Override
    public Set<K> removeAllByValue(V value) {
        Set<K> removedKeys = backward.remove(value);
        if (removedKeys == null) {
            return Collections.emptySet();
        }

        Set<K> result = new HashSet<>(removedKeys);

        for (K key : removedKeys) {
            Set<V> values = forward.get(key);
            if (values != null) {
                values.remove(value);
                if (values.isEmpty()) {
                    forward.remove(key);
                }
            }
        }

        counter.size -= removedKeys.size();

        return Collections.unmodifiableSet(result);
    }

    @Override
    public boolean containsEntry(K key, V value) {
        Set<V> values = forward.get(key);
        return values != null && values.contains(value);
    }

    @Override
    public boolean containsKey(K key) {
        return forward.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return backward.containsKey(value);
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(forward.keySet());
    }

    @Override
    public Set<V> valueSet() {
        return Collections.unmodifiableSet(backward.keySet());
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        Set<Map.Entry<K, V>> result = new HashSet<>();

        for (Map.Entry<K, Set<V>> entry : forward.entrySet()) {
            K key = entry.getKey();
            for (V value : entry.getValue()) {
                result.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
            }
        }

        return Collections.unmodifiableSet(result);
    }

    @Override
    public Map<K, Set<V>> asMap() {
        return immutableMapCopy(forward);
    }

    @Override
    public Map<V, Set<K>> inverseAsMap() {
        return immutableMapCopy(backward);
    }

    @Override
    public BiSetMultimap<V, K> inverse() {
        return new HashBiSetMultimap<>(backward, forward, counter);
    }

    @Override
    public int size() {
        return counter.size;
    }

    @Override
    public int keySize() {
        return forward.size();
    }

    @Override
    public boolean isEmpty() {
        return counter.size == 0;
    }

    @Override
    public void clear() {
        forward.clear();
        backward.clear();
        counter.size = 0;
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

    private static final class Counter {
        private int size;
    }
}
