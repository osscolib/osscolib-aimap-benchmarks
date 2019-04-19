package org.osscolib.aimap.benchmarks;

public class KeyValue<K,V> {

    private final K key;
    private final V value;

    public KeyValue(final K key, final V value) {
        super();
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

}
