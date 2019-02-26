package de.pauhull.utils.fetcher;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Element expires after given time
 *
 * @author pauhull
 */
public class TimedHashMap<K, V> implements Map<K, V> {

    private final HashMap<K, V> entries = new HashMap<>();
    private final HashMap<K, Long> timestamps = new HashMap<>();
    private final long expireAfter;

    public TimedHashMap(TimeUnit unit, long expireAfter) {
        this.expireAfter = unit.toMillis(expireAfter);
    }

    @Override
    public int size() {
        this.checkForExpiredElements();
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        this.checkForExpiredElements();
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        this.checkForExpiredElements();
        return entries.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        this.checkForExpiredElements();
        return entries.containsValue(value);
    }

    @Override
    public V get(Object key) {
        this.checkForExpiredElements();
        return entries.get(key);
    }

    @Override
    public V put(K key, V value) {
        timestamps.put(key, System.currentTimeMillis());
        return entries.put(key, value);
    }

    @Override
    public V remove(Object key) {
        timestamps.remove(key);
        return entries.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        entries.clear();
        timestamps.clear();
    }

    @Override
    public Set<K> keySet() {
        this.checkForExpiredElements();
        return entries.keySet();
    }

    @Override
    public Collection<V> values() {
        this.checkForExpiredElements();
        return entries.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        this.checkForExpiredElements();
        return entries.entrySet();
    }

    public boolean isExpired(K key) {
        return System.currentTimeMillis() - timestamps.get(key) > expireAfter;
    }

    public boolean checkForExpiredElements() {
        boolean removedItem = false;

        Iterator<K> iterator = entries.keySet().iterator();
        while (iterator.hasNext()) {
            K key = iterator.next();

            if (isExpired(key)) {
                timestamps.remove(key);
                iterator.remove();
                removedItem = true;
            }
        }

        return removedItem;
    }

}
