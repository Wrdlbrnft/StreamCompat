package com.github.wrdlbrnft.streamcompat.util;

import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntObjFunction;

/**
 * Created by kapeller on 22/03/16.
 */
public class SparseArrayCompat {

    public static <V> V compute(SparseArray<V> map, int key, IntObjFunction<? super V, ? extends V> remappingFunction) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(remappingFunction);
        final V oldValue = map.get(key);
        final V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null) {
                map.remove(key);
            }

            return null;
        }

        map.put(key, newValue);
        return newValue;
    }

    public static <V> V putIfAbsent(SparseArray<V> map, int key, V value) {
        final V existingValue = map.get(key);

        if (existingValue != null) {
            return existingValue;
        }

        map.put(key, value);
        return null;
    }

    public static <V> V computeIfAbsent(SparseArray<V> map, int key, IntFunction<? extends V> mappingFunction) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(mappingFunction);
        V v;
        if ((v = map.get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                map.put(key, newValue);
                return newValue;
            }
        }

        return v;
    }

    public static <V> V computeIfPresent(SparseArray<V> map, int key, IntObjFunction<? super V, ? extends V> remappingFunction) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(remappingFunction);
        V oldValue;
        if ((oldValue = map.get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);

            if (newValue != null) {
                map.put(key, newValue);
                return newValue;
            }

            map.remove(key);
        }

        return null;
    }

    public static <V> void replaceAll(SparseArray<V> map, IntObjFunction<? super V, ? extends V> function) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(function);
        for (int i = 0, count = map.size(); i < count; i++) {
            final int key = map.keyAt(i);
            final V value = map.valueAt(i);
            map.setValueAt(i, function.apply(key, value));
        }
    }
}
