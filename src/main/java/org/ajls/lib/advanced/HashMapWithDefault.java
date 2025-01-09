package org.ajls.lib.advanced;

import java.util.HashMap;

public class HashMapWithDefault<K,V> extends HashMap<K, V> {
    public HashMapWithDefault(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public HashMapWithDefault() {

    }

    V defaultValue;
    boolean setDefaultValue(V defaultValue) {
        boolean changed = false;
        if (this.defaultValue != defaultValue) {
            changed = true;
            this.defaultValue = defaultValue;
        }
        return changed;
    }

    @Override
    public V get(Object key) {
        if (defaultValue == null) {
            return super.get(key);
        }
        else {
            V value = super.get(key);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }
    }
}
