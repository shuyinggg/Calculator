package datastructures.concrete.dictionaries;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;

    // You're encouraged to add extra fields (and helper methods) though!
    private int size;
    private int cap = 5000;

    public ArrayDictionary() {
        this.size = 0;
        pairs = makeArrayOfPairs(cap);
    }


    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            V value = null;
            for (int i = 0; i < this.size; i++) {
                if (key == null && this.pairs[i].key == null) {
                    value = this.pairs[i].value;
                    break;
                } else if (this.pairs[i].key.equals(key)) {
                    value = this.pairs[i].value;
                    break;
                }
            }
            return value;
        } else {
            throw new NoSuchKeyException();
        }
        
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            for (int i = 0; i < this.size; i++) {
                Pair<K, V> pair = this.pairs[i];
                if (key == null && pair.key == null) {
                    pair.value = value;
                }
                else if (pair.key.equals(key)) {
                    pair.value = value;
                }
            }   
        } else {
            if (this.size == cap) {//double the capacity if full
                cap = cap * 2;
                Pair<K, V>[] pairs2 = makeArrayOfPairs(cap);
                for (int i = 0; i < this.size; i++) {//copy the old pairs into new ArrayDictionary
                    pairs2[i] = this.pairs[i];
                }
                this.pairs = pairs2;
            }
            Pair<K, V> pair = new Pair<K, V>(key, value);
            this.pairs[this.size] = pair;
            this.size++;
        }
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V value = null;
            int i = 0;
            for (i = 0; i < this.size; i++) {
                if (key == null && this.pairs[i].key == null) {
                    value = this.pairs[i].value;
                    break;
                } else if (this.pairs[i].key.equals(key)) {
                    value = this.pairs[i].value;
                    break;
                }
            }
            this.pairs[i] = this.pairs[size - 1];
            this.size--;
            return value;
        } else {
            throw new NoSuchKeyException();
        }
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < this.size; i++) {
            Pair<K, V> pair = pairs[i];
            if (key == null && pair.key == null) {
                return true;
            }
            else if (pair.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
