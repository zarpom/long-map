package de.comparus.opensource.longmap;

import de.comparus.opensource.longmap.LongMap;

import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private int size;
    private Node<V>[] table;

    public LongMapImpl() {
        this.table = new Node[DEFAULT_CAPACITY];
    }

    private static class Node<V> {
        final long key;
        V value;
        Node<V> next;

        Node(long key, V value, Node<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Adds the specified key-value pair to the hash table. If the table already
     * contains a pair with the specified key, the old value is replaced by the
     * specified value and the old value is returned. If the key is not present
     * in the table, adds the new pair and returns null.
     *
     * @param key   the key of the pair to be added
     * @param value the value of the pair to be added
     * @return the previous value associated with the key, or null if the key was not present in the table
     */
    @Override
    public V put(long key, V value) {
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }


        int index = indexFor(key); //??
        for (Node<V> node = table[index]; node != null; node = node.next) {
            if (node.key == key) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        table[index] = new Node<>(key, value, table[index]);
        size++;
        return null;
    }

    private int indexFor(long key) {
        return (int) (key % table.length);
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Node<V>[] newTable = new Node[newCapacity];

        for (Node<V> node : table) {
            while (node != null) {
                Node<V> next = node.next;
                int index = (int) (node.key % newCapacity);
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }

        table = newTable;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if the
     * hash table contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if the table contains no mapping for the key
     */
    @Override
    public V get(long key) {
        int index = indexFor(key);
        for (Node<V> node = table[index]; node != null; node = node.next) {
            if (node.key == key) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Removes the key-value pair with the specified key from the hash table and
     * returns its value. If the table does not contain a pair with the specified
     * key, returns null.
     *
     * @param key the key of the pair to be removed
     * @return the value of the removed pair, or null if the table does not contain a pair with the specified key
     */
    @Override
    public V remove(long key) {
        int index = indexFor(key);
        Node<V> previous = null;
        for (Node<V> node = table[index]; node != null; previous = node, node = node.next) {
            if (node.key == key) {
                if (previous == null) {
                    table[index] = node.next;
                } else {
                    previous.next = node.next;
                }
                size--;
                return node.value;
            }
        }
        return null;
    }

    /**
     * Returns true if the hash table contains no key-value pairs.
     *
     * @return true if the hash table is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if the hash table contains a pair with the specified key.
     *
     * @param key the key whose presence in the table is to be tested
     * @return true if the hash table contains a pair with the specified key, false otherwise
     */
    @Override
    public boolean containsKey(long key) {
        return get(key) != null;
    }

    /**
     * Returns true if the hash table contains a pair with the specified value.
     *
     * @param value the value whose presence in the table is to be tested
     * @return true if the hash table contains a pair with the specified value, false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        for (Node<V> node : table) {
            while (node != null) {
                if ((value == null && node.value == null) || (value != null && value.equals(node.value))) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    /**
     * Returns an array of all the keys stored in the hash table.
     *
     * @return a long[] containing all the keys in the hash table
     */
    @Override
    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;
        for (Node<V> node : table) {
            while (node != null) {
                keys[index++] = node.key;
                node = node.next;
            }
        }
        return keys;
    }

    /**
     * Returns an array of all the values stored in the hash table.
     *
     * @return a V[] containing all the values in the hash table
     */
    @Override
    public V[] values() {
        V[] values = (V[]) new Object[size];
        int index = 0;
        for (Node<V> node : table) {
            while (node != null) {
                values[index++] = node.value;
                node = node.next;
            }
        }
        return values;
    }

    /**
     * Returns the number of key-value pairs stored in the hash table.
     *
     * @return the number of key-value pairs in the hash table
     */
    @Override
    public long size() {
        return size;
    }

    /**
     * Removes all the key-value pairs from the hash table.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

}
