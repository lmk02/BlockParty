package de.leonkoth.blockparty.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Package de.leonkoth.blockparty.util
 *
 * @author Leon Koth
 * © 2019
 */
public class DualHashMap<K, V> implements Map<K, V> {

    private HashMap<K, V> keyValue;
    private HashMap<V, K> valueKey;

    public DualHashMap()
    {
        this.keyValue = new HashMap<>();
        this.valueKey = new HashMap<>();
    }

    @Override
    public int size() {
        return this.keyValue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.keyValue.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.keyValue.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.keyValue.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.keyValue.get(key);
    }

    public K getKey(Object value) {
        return this.valueKey.get(value);
    }

    @Override
    public V put(K key, V value) {
        this.valueKey.put(value, key);
        return this.keyValue.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if (this.keyValue.containsKey(key))
        {
            this.valueKey.remove(this.keyValue.get(key));
            return this.keyValue.remove(key);
        }

        return null;
    }


    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {
        this.valueKey.clear();
        this.keyValue.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.keyValue.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.keyValue.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.keyValue.entrySet();
    }
}
