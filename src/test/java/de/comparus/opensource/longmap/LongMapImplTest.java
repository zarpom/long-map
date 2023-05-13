package de.comparus.opensource.longmap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LongMapImplTest {

    private LongMap<String> longMap;

    /**
     * Initializes the longMap object before each test.
     */
    @BeforeEach
    void setUp() {
        longMap = new LongMapImpl();
    }

    /**
     * Tests the put method by adding key-value pairs to the map and
     * verifying the return values and map contents.
     */
    @Test
    void testPut() {
        assertNull(longMap.put(1L, "one"));
        assertEquals("one", longMap.get(1L));

        assertEquals("one", longMap.put(1L, "ONE"));
        assertEquals("ONE", longMap.get(1L));
    }

    /**
     * Tests the get method by retrieving values for existing and
     * non-existing keys in the map.
     */
    @Test
    void testGet() {
        assertNull(longMap.get(1L));

        longMap.put(1L, "one");
        assertEquals("one", longMap.get(1L));

        assertNull(longMap.get(2L));
    }

    /**
     * Tests the remove method by removing pairs with existing and
     * non-existing keys in the map, and verifying the return values
     * and map contents.
     */
    @Test
    void testRemove() {
        assertNull(longMap.remove(1L));

        longMap.put(1L, "one");
        assertEquals("one", longMap.remove(1L));
        assertNull(longMap.get(1L));
    }

    /**
     * Tests the isEmpty method by checking the state of an initially
     * non-empty map and an empty map.
     */
    @Test
    void testIsEmpty() {
        assertTrue(longMap.isEmpty());

        longMap.put(1L, "one");
        assertFalse(longMap.isEmpty());

        longMap.remove(1L);
        assertTrue(longMap.isEmpty());
    }

    /**
     * Tests the containsKey method by checking the presence of existing
     * and non-existing keys in the map.
     */
    @Test
    void testContainsKey() {
        assertFalse(longMap.containsKey(1L));

        longMap.put(1L, "one");
        assertTrue(longMap.containsKey(1L));

        longMap.remove(1L);
        assertFalse(longMap.containsKey(1L));
    }

    /**
     * Tests the containsValue method by checking the presence of existing
     * and non-existing values in the map.
     */
    @Test
    void testContainsValue() {
        assertFalse(longMap.containsValue("one"));

        longMap.put(1L, "one");
        assertTrue(longMap.containsValue("one"));

        longMap.remove(1L);
        assertFalse(longMap.containsValue("one"));
    }

    /**
     * Tests the keys method by verifying the contents of the returned
     * array of keys.
     */
    @Test
    void testKeys() {
        longMap.put(1L, "one");
        longMap.put(2L, "two");
        long[] keys = longMap.keys();
        assertArrayEquals(new long[]{1L, 2L}, keys);
    }

    /**
     * Tests the values method by verifying the contents of the returned
     * array of values.
     */
    @Test
    void testValues() {
        longMap.put(1L, "one");
        longMap.put(2L, "two");
        Object[] valuesArray = longMap.values();
        String[] stringArray = Arrays.copyOf(valuesArray, valuesArray.length, String[].class);
        assertArrayEquals(new String[]{"one", "two"}, stringArray);
    }


    /**
     * Tests the size method by checking the size of the map before and
     * after adding and removing key-value pairs.
     */
    @Test
    void testSize() {
        assertEquals(0, longMap.size());

        longMap.put(1L, "one");
        assertEquals(1, longMap.size());

        longMap.put(2L, "two");
        assertEquals(2, longMap.size());

        longMap.remove(1L);
        assertEquals(1, longMap.size());
    }

    /**
     * Tests the resizing operation by adding many key-value pairs to the map,
     * ensuring that the map functions correctly after resizing.
     */
    @Test
    void testResize() {
        int numberOfElements = 1000;

        for (int i = 0; i < numberOfElements; i++) {
            longMap.put((long) i, "value" + i);
        }

        assertEquals(numberOfElements, longMap.size());

        for (int i = 0; i < numberOfElements; i++) {
            assertEquals("value" + i, longMap.get((long) i));
        }
    }

    /**
     * Tests the clear method by clearing the map and verifying that it
     * becomes empty and contains no key-value pairs.
     */
    @Test
    void testClear() {
        longMap.put(1L, "one");
        longMap.put(2L, "two");

        longMap.clear();
        assertTrue(longMap.isEmpty());
        assertEquals(0, longMap.size());
        assertNull(longMap.get(1L));
        assertNull(longMap.get(2L));
    }
}