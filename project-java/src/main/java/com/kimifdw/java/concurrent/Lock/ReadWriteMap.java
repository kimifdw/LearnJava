package com.kimifdw.java.concurrent.Lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 并发性高的访问【LinkedHashMap】
 *
 * @param <K>
 * @param <V>
 */
public class ReadWriteMap<K, V> {

    private final Map<K, V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    /**
     * 多线程写入
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }
    // 对remove()，putAll()，clear()等方法执行相同的操作

    /**
     * 多线程读取
     *
     * @param key
     * @return
     */
    public V get(Object key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }
}
