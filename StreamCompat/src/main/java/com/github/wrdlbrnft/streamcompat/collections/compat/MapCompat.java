package com.github.wrdlbrnft.streamcompat.collections.compat;

import com.github.wrdlbrnft.streamcompat.function.BiFunction;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Map;

public class MapCompat {

    public static <K, V> V compute(Map<K, V> map, K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(remappingFunction);
        final V oldValue = map.get(key);
        final V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null || map.containsKey(key)) {
                map.remove(key);
            }

            return null;
        }

        map.put(key, newValue);
        return newValue;
    }

    public static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        final V existingValue = map.get(key);

        if (existingValue != null) {
            return existingValue;
        }

        return map.put(key, value);
    }

    public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<? super K, ? extends V> mappingFunction) {
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

    public static <K, V> V computeIfPresent(Map<K, V> map, K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
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

    public static <K, V> void replaceAll(Map<K, V> map, BiFunction<? super K, ? super V, ? extends V> function) {
        Utils.requireNonNull(map);
        Utils.requireNonNull(function);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            final K key = entry.getKey();
            final V value = entry.getValue();
            final V newValue = function.apply(key, value);
            entry.setValue(newValue);
        }
    }
}
