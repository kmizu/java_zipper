package com.github.kmizu.zipper4j;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SListTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SListTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SListTest.class );
    }

    public void testEquals1()
    {
        var a = SList.of(1, 2, 3);
        var b = SList.of(1, 2, 3);
        assertEquals(a, b);
    }

    public void testEquals2()
    {
        var a = SList.of(1, 2, 3);
        var b = SList.of(2, 3, 4);

        assertFalse(a.equals(b));
    }

    public void testEquals3()
    {
        var a = SList.of();
        var b = new SNil<>();
        assertEquals(a, b);
    }

    public void testMap() {
        var a = SList.of(1, 2, 3);
        var b = SList.of(2, 3, 4);
        var mappedA = a.map(x -> x + 1);
        assertEquals(mappedA, b);
    }
}
