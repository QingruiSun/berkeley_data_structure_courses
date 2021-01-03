package lab9;

import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.ArrayList;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private double loadFactor() {
        double nowLoadFactor = (size * 1.0) / (buckets.length * 1.0);
        return nowLoadFactor;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        ArrayMap<K, V> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        return bucket.get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int index = hash(key);
        System.out.println("put" + key + " " + index);
        ArrayMap<K, V> bucket = buckets[index];
        if (bucket == null) {
            /* Be caution, you need buckets[index] instead bucket, because bucket is null,
               you need use bucket[index] to point to a new space.
               The bucket has no relationship with buckets[index].
             */
            buckets[index] = new ArrayMap<>();
            bucket = buckets[index];
        }
        if (!bucket.containsKey(key)) {
            size++;
        }
        bucket.put(key, value);
        double nowLoadFactor = loadFactor();
        if (nowLoadFactor > MAX_LF) {
            System.out.println(key);
            System.out.println("resize");
            resize();
        }
    }

    private void resize() {
        int length = buckets.length;
        int newLength = length * 2;
        ArrayMap<K, V>[] newBuckets = new ArrayMap[newLength];
        for (int i = 0; i < length; ++i) {
            ArrayMap<K, V> oldBucket = buckets[i];
            if (oldBucket != null) {
                Set<K> oldSet = oldBucket.keySet();
                for (K oldKey : oldSet) {
                    V oldValue = oldBucket.get(oldKey);
                    int newIndex = Math.floorMod(oldKey.hashCode(), newLength);
                    if (newBuckets[newIndex] == null) {
                        newBuckets[newIndex] = new ArrayMap<>();
                    }
                    newBuckets[newIndex].put(oldKey, oldValue);
                }
            }
        }
        this.buckets = newBuckets;
    }
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        int length = buckets.length;
        for (int i = 0; i < length; ++i) {
            ArrayMap<K, V> bucket = buckets[i];
            if (bucket != null) {
                for (K nowKey : bucket.keySet()) {
                    set.add(nowKey);
                }
            }
        }
        return set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int index = hash(key);
        ArrayMap<K, V> bucket = buckets[index];
        V result = bucket.remove(key);
        size--;
        return result;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            return null;
        }
        int index = hash(key);
        ArrayMap<K, V> bucket = buckets[index];
        V result = bucket.get(key);
        if ((value == null) || (result == null)) {
            return null;
        }
        bucket.remove(key);
        size--;
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {

        private Set<K> set;
        private List<K> keyList;
        int itemNumber;
        int index;

        MyHashMapIterator() {
            set = keySet();
            keyList = new ArrayList<>();
            for (K nowKey : set) {
                keyList.add(nowKey);
            }
            itemNumber = set.size();
            index = 0;
        }

        @Override
        public boolean hasNext() {
            if (index < itemNumber) {
                return true;
            }
            return false;
        }

        @Override
        public K next() {
            return keyList.get(index++);
        }
    }
}
