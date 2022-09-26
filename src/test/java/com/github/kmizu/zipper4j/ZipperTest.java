package com.github.kmizu.zipper4j;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ZipperTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ZipperTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ZipperTest.class );
    }

    public void testGet()
    {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        assertEquals((Integer)1, z.get());
    }

    public void testSet()
    {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        z = z.set(2);
        assertEquals((Integer)2, z.get());
    }

    public void testNext() {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        z = z.next();
        assertEquals((Integer)2, z.get());
    }

    public void testPrevious() {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        z = z.next().previous();
        assertEquals((Integer)1, z.get());
    }

    public void testToList() {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        var c = z.next().toList();
        assertEquals(SList.of(2, 3), c);
    }

    public void testMap() {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        var c = z.map(x -> x * 2).toList();
        assertEquals(SList.of(2, 4, 6), c);
    }

    public void testFilter() {
        var a = SList.of(1, 2, 3);
        var z = Zipper.of(a);
        var c = z.filter(x -> x % 2 == 1).toList();
        assertEquals(SList.of(1, 3), c);
    }

    public void testFindFirst() {
        var a = SList.of(1, 2, 3, 4, 5);
        var z = Zipper.of(a);
        z.findFirst(e -> e == 3).ifPresent(found -> {
            assertEquals((Integer)3, found.get());
            assertEquals((Integer)4, found.next().get());
        });
    }

    public void testFoldLeft() {
        var a = SList.of(1, 2, 3, 4, 5);
        var z = Zipper.of(a);
        var result = z.foldLeft(0, (x, y) -> x + y);
        assertEquals((Integer)15, result);

        result = z.next().foldLeft(0, (x, y) -> x + y);
        assertEquals((Integer)14, result);
    }

    public void testAllMatch() {
        var a = SList.of(1, 2, 3, 4, 5);
        var z = Zipper.of(a);
        assertTrue(z.allMatch(e -> e < 6));
        assertFalse(z.allMatch(e -> e >= 5));
    }

    public void testAnyMatch() {
        var a = SList.of(1, 2, 3, 4, 5);
        var z = Zipper.of(a);
        assertTrue(z.anyMatch(e -> e >= 5));
        assertFalse(z.anyMatch(e -> e > 5));
    }
}
