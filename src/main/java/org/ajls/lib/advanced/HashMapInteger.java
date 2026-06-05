package org.ajls.lib.advanced;

import org.ajls.lib.references.Time;

import java.util.HashMap;
import java.util.Objects;

public class HashMapInteger<K> extends HashMap<K, Integer> {
    int upperBound = Integer.MAX_VALUE;
    int lowerBound = 0;
    Integer defaultValue = 0;
    //defaultValue可以是自然数或者Null，如果是自然数，那么映射：def对于不存储，remove和null都对应null
    //如果是null 那么

    //老版本比较暴力，def对应不存储，不存储和null都对应Null，也就是说舍去了null的信息，你Put null和remove没有区别
    //下面是新版本
    //todo 我们计划映射是，def对应不存储，不存储对应Null，null对应def的这样轮换存
    //如果def是自然数0，那么0对应不存储，不存储对应Null，null对应0  //前者是这里，后者是super
    //如果def是Null，那么null对应不存储，不存储对应null，null就是def直接汇集到def来存 等价于null和不存储交换了
    public HashMapInteger() {
        this.upperBound = Integer.MAX_VALUE;
    }
    public HashMapInteger(int upperBound) {
        this.upperBound = upperBound;
        this.lowerBound = 0;
    }
    public HashMapInteger(int upperBound, int lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
    public HashMapInteger(int upperBound, int lowerBound, Integer defaultValue) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean containsKey(Object key) {
//        if (!super.containsKey(key)) { return true; }
//        else {
//            if ()
//        }
//        return super.containsKey(key);
        Integer value = get(key);
        return value != null;
    }
    @Override
    public Integer get(Object key) {
        if (!super.containsKey(key)) { return defaultValue; }
        int value = super.get(key);

        return super.get(key); //如果super是null，那么这里就是不存储，get不存储也是对应Null所以没区别
        //todo 但是如果super是def，那么这里就是null，需要单独写匹配


//        Integer value = super.get(key);
//        if (value == null) {
//            return 0;
//        }
//        return value;
    }



    @Override
    public Integer put(K key, Integer value) {
        if (Objects.equals(value, defaultValue)) {  //如果和默认值相等
            if (!super.containsKey(key)) { //avoid put return null  //如果本来就是默认值（没key）
                return defaultValue;  //那么存储的就是默认值，返回默认值，然后不更改任何东西
            }
            return super.remove(key);  //如果不是默认值，那么就直接返回super就行
        }
        else {
            if (!super.containsKey(key)) { //avoid put return null
                super.put(key, value);
                return defaultValue;
            }
            return super.put(key, value);
        }
    }

    public boolean putMax(K key, Integer value) {
        if (get(key) >= value) return false;
        put(key, value);
        return true;
    }

    public boolean putMaxTime(K key, int duration) {
        return putMax(key, Time.getTime() + duration);
    }

    @Override
    public Integer remove(Object key) {
        return put((K) key, null);
    }

    public int increment(K key) {
        Integer value = get(key);
//        if (value >= upperBound) {
//            put(key, lowerBound);
////            return lowerBound;
//        }
        put(key, get(key) + 1);
        return value;
    }

    public int increment(K key, int max) {
        Integer value = get(key);
        if (value < max) {
            return increment(key);
        }
        return value;
    }

    public int decrement(K key) {
        Integer value = get(key);
//        if (value <= lowerBound) {
//            put(key, upperBound);
////            return upperBound;
//        }
        put(key, value-1);
        return value;
    }

    public int pageUp(K key) {
        Integer value = get(key);
        if (value >= upperBound) {
            put(key, lowerBound);
            return upperBound;
        }
        return increment(key);
    }

    public int getUpperBound() {
        return upperBound;
    }

    public boolean biggerThanTime(K key) {
        return get(key) > Time.getTime();
    }

    public boolean biggerOrEqualToTime(K key) {
        return get(key) >= Time.getTime();
    }


}
