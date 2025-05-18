package org.ajls.lib.advanced;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HaxhMap<K, V> {
    HashMap<K, HashSet<V>> hashMap;
    public HaxhMap() {
        hashMap = new HashMap<>();
    }

    public boolean put(K key, V value) {
        HashSet<V> hashSet = new HashSet<>();
        if (hashMap.containsKey(key)) {
            hashSet = hashMap.get(key);
        }
        boolean contains = hashSet.add(value);
        hashMap.put(key, hashSet);
        return contains;
    }

    public HashSet<V> put(K key, HashSet<V> values) {
        return hashMap.put(key, values);
    }

    public boolean contains(K key, V value) {
        if (hashMap.containsKey(key)) {
            return hashMap.get(key).contains(value);
        }
        return false;
    }

    public HashSet<V> getValues(K key, boolean notNull) {
//        HashSet<V> values;
//        if (notNull) {
//            values = new HashSet<>();
//        }
        HashSet<V> values = hashMap.get(key);
        if (notNull && (values == null) ) {
            values = new HashSet<>();
        }
        return values;
    }

    public HashSet<V> getValues(K key) {
        return getValues(key, false);
    }

    public HashSet<K> getKeys(V value, boolean notNull) {
        HashSet<K> keys = new HashSet<>();
        for (K key : hashMap.keySet()) {
            HashSet<V> hashSet = hashMap.get(key);
            if (hashSet.contains(value)) {
                keys.add(key);
            }
        }
        if (keys.isEmpty() && !notNull) {
            return null;
        }
        return keys;
    }

    public HashSet<V> removeValues(K key) {
        return hashMap.remove(key);
    }

    public HashSet<K> removeKeys(V value) {
        HashSet<K> keys = new HashSet<>();
        for (K key : hashMap.keySet()) {
            HashSet<V> hashSet = hashMap.get(key);
            if (hashSet.contains(value)) {
                keys.add(key);
                hashSet.remove(value);
                if (hashSet.isEmpty()) {
                    hashMap.remove(key);
                }
            }
        }
        if (keys.isEmpty()) {
            return null;
        }
        return keys;
    }

    public Set<K> keySet() {
        return hashMap.keySet();
    }

    public boolean remove(K key, V value) {
        HashSet<V> hashSet = hashMap.get(key);
        boolean removed = hashSet.remove(value);
        if (hashSet.isEmpty()) {
            hashMap.remove(key);
        }
        else {
            hashMap.put(key, hashSet);
        }
        return removed;
    }




}
