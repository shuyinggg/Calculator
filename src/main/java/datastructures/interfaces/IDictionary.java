package datastructures.interfaces;

import misc.exceptions.NoSuchKeyException;

/**
 * Represents a data structure that contains a bunch of key-value mappings. Each key must be unique.
 */
public interface IDictionary<K, V> {
    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    V get(K key);

    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    void put(K key, V value);

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    V remove(K key);

    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    boolean containsKey(K key);

    /**
     * Returns the number of key-value pairs stored in this dictionary.
     */
    int size();

    /**
     * Returns 'true' if this dictionary is empty and 'false' otherwise.
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }
}
