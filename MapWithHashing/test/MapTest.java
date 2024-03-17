import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Grant Smith & Aidan McDonald
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /*
     * Constructor test cases -------------------------------------------------
     */

    /**
     * No argument contructor.
     */
    @Test
    public final void testNoArgumentConstructor() {

        Map<String, String> map = this.constructorTest();
        Map<String, String> mapExpected = this.constructorRef();

        assertEquals(mapExpected, map);
    }

    /*
     * Add test cases ---------------------------------------------------------
     */

    /**
     * Add, to empty map case.
     */
    @Test
    public final void testAddToEmpty() {

        Map<String, String> map = this.createFromArgsTest();
        map.add("1", "one");
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one");

        assertEquals(mapExpected, map);
    }

    /**
     * Add, to non-empty map case.
     */
    @Test
    public final void testAddToNonEmpty() {

        Map<String, String> map = this.createFromArgsTest("1", "one");
        map.add("2", "two");
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one",
                "2", "two");

        assertEquals(mapExpected, map);
    }

    /*
     * Remove test cases ------------------------------------------------------
     */

    /**
     * Remove, resulting in empty map case.
     */
    @Test
    public final void testRemoveResultingInEmpty() {

        Map<String, String> map = this.createFromArgsTest("1", "one");
        map.remove("1");
        Map<String, String> mapExpected = this.createFromArgsRef();

        assertEquals(mapExpected, map);
    }

    /**
     * Remove, resulting in non-empty map case.
     */
    @Test
    public final void testRemoveResultingInNonEmpty() {

        Map<String, String> map = this.createFromArgsTest("1", "one", "2",
                "two");
        map.remove("1");
        Map<String, String> mapExpected = this.createFromArgsRef("2", "two");

        assertEquals(mapExpected, map);
    }

    /*
     * RemoveAny test cases ---------------------------------------------------
     */

    /**
     * RemoveAny, resulting in empty map case.
     */
    @Test
    public final void testRemoveAnyResultingInEmpty() {

        Map<String, String> map = this.createFromArgsTest("1", "one");
        Pair<String, String> pair = map.removeAny();
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one");

        assertEquals(true, mapExpected.hasKey(pair.key()));
        mapExpected.remove(pair.key());
        assertEquals(mapExpected, map);
    }

    /**
     * RemoveAny, resulting in non-empty map case.
     */
    @Test
    public final void testRemoveAnyResultingInNonEmpty() {

        Map<String, String> map = this.createFromArgsTest("1", "one", "2",
                "two", "3", "three");
        Pair<String, String> pair = map.removeAny();
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one",
                "2", "two", "3", "three");

        assertEquals(true, mapExpected.hasKey(pair.key()));
        mapExpected.remove(pair.key());
        assertEquals(mapExpected, map);
    }

    /*
     * Value test cases -------------------------------------------------------
     */

    /**
     * Value.
     */
    @Test
    public final void testValue() {

        Map<String, String> map = this.createFromArgsTest("1", "one", "2",
                "two");
        String result = map.value("1");
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one",
                "2", "two");

        assertEquals("one", result);
        assertEquals(mapExpected, map);
    }

    /*
     * HasKey test cases ------------------------------------------------------
     */

    /**
     * HasKey, true case.
     */
    @Test
    public final void testHasKeyTrue() {

        Map<String, String> map = this.createFromArgsTest("1", "one", "2",
                "two");
        boolean result = map.hasKey("1");
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one",
                "2", "two");

        assertEquals(true, result);
        assertEquals(mapExpected, map);
    }

    /**
     * HasKey, false case.
     */
    @Test
    public final void testHasKeyFalse() {

        Map<String, String> map = this.createFromArgsTest("1", "one", "2",
                "two");
        boolean result = map.hasKey("3");
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one",
                "2", "two");

        assertEquals(false, result);
        assertEquals(mapExpected, map);
    }

    /*
     * Size test cases --------------------------------------------------------
     */

    /**
     * Size, empty case.
     */
    @Test
    public final void testSizeEmpty() {

        Map<String, String> map = this.createFromArgsTest();
        int result = map.size();
        Map<String, String> mapExpected = this.createFromArgsRef();

        assertEquals(0, result);
        assertEquals(mapExpected, map);
    }

    /**
     * Size, non-empty case.
     */
    @Test
    public final void testSizeNonEmpty() {

        Map<String, String> map = this.createFromArgsTest("1", "one", "2",
                "two");
        int result = map.size();
        Map<String, String> mapExpected = this.createFromArgsRef("1", "one",
                "2", "two");

        assertEquals(2, result);
        assertEquals(mapExpected, map);
    }

}
