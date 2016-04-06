package com.github.wrdlbrnft.streamcompat.collections.compat;

import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.function.LongFunction;
import com.github.wrdlbrnft.streamcompat.function.LongObjFunction;
import com.github.wrdlbrnft.streamcompat.util.Utils;

/**
 * Created by kapeller on 22/03/16.
 */
public class LongSparseArrayCompat {

    public static <V> V compute(LongSparseArray<V> map, long key, LongObjFunction<? super V, ? extends V> remappingFunction) {
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

    public static <V> V putIfAbsent(LongSparseArray<V> map, long key, V value) {
        final V existingValue = map.get(key);

        if (existingValue != null) {
            return existingValue;
        }

        map.put(key, value);
        return null;
    }

    public static <V> V computeIfAbsent(LongSparseArray<V> map, long key, LongFunction<? extends V> mappingFunction) {
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

    public static <V> V computeIfPresent(LongSparseArray<V> map, long key, LongObjFunction<? super V, ? extends V> remappingFunction) {
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

    public static <V> void replaceAll(LongSparseArray<V> map, LongObjFunction<? super V, ? extends V> function) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(function);
        for (int i = 0, count = map.size(); i < count; i++) {
            final long key = map.keyAt(i);
            final V value = map.valueAt(i);
            map.setValueAt(i, function.apply(key, value));
        }
    }
}
