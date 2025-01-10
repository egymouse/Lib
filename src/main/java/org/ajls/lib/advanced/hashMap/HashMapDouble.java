package org.ajls.lib.advanced.hashMap;

import org.ajls.lib.command.D;

import java.util.HashMap;
import java.util.Objects;

public class HashMapDouble<K> extends HashMap<K, Double> {
    Double defaultValue = 0.0;
    public HashMapDouble() {

    }
//    public HashMapDouble(Double defaultValue) {
//        this.defaultValue = defaultValue;
//    }

    @Override
    public Double get(Object key) {
        if (!super.containsKey(key)) { return defaultValue; }
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        Double value = get(key);
        return value != null;
    }

//    @Override
//    public Double remove(Object key) {
//        if
//        Double value = super.remove(key);
//
//    }

    @Override
    public Double put(K key, Double value) {
        if (Objects.equals(value, defaultValue)) {
            if (!super.containsKey(key)) { //avoid put return null
                return defaultValue;
            }
            return super.remove(key);
        }
        else {
            if (!super.containsKey(key)) { //avoid put return null
                super.put(key, value);
                return defaultValue;
            }
            return super.put(key, value);
        }
    }

    public Double getDiffAndPut(K key, Double value) {
        Double oldValue = put(key, value);
        if (oldValue == null) return null;
        return value - oldValue;
    }

    public Double setDefaultValue(Double defaultValue) {
        Double oldValue = this.defaultValue;
        this.defaultValue = defaultValue;
        return oldValue;
    }

    public Double add(K key, double amount) {
//        Double value = get(key);
//        if (value >= upperBound) {
//            put(key, lowerBound);
////            return lowerBound;
//        }
        return put(key, get(key) + amount);
//        return value;
    }

    public Double add(K key, double amount, boolean after) {
        Double value = get(key);
//        if (value >= upperBound) {
//            put(key, lowerBound);
////            return lowerBound;
//        }
        put(key, get(key) + amount);
        return value + amount;
    }

    public boolean putMax(K key, Double value) {
        double oldValue = get(key);
        if (oldValue > value) { return false; }
        put(key, value);
        return true;
    }

}
