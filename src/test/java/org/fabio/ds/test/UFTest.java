package org.fabio.ds.test;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.fabio.ds.UnionFind;
import org.junit.Test;

public class UFTest {

    UnionFind uf;
    int n;

    public UFTest() {
        System.out.println("Setting-up infrastructure.");
        n = 10;
        uf = new UnionFind(n);
        System.out.println("Done!");
    }
    
    @Test
    public void testInitialization() {
        System.out.print("Verifying arrays initialization... ");
        boolean verifyArrays = true;
        for (int i = 0; i < n; i++) {
            // checking nodes
            if (uf.getNodes()[i] != i) {
                verifyArrays = false;
            }
            // checking ranks
            if (uf.getRanks()[i] != 1) {
                verifyArrays = false;
            }
        }
        if (!verifyArrays) {
            fail("Arrays not initialized correctly");
        }

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExceptionInFind() {
        System.out.println("Testing IllegalArgumentException when argument is a negative index");
        uf.find(-1);
        fail("Exception IllegalArgumentException has not been thrown!");
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementExceptionInFind() {
        System.out.println("Testing NoSuchElementException when argument is out of bounds");
        uf.find(Integer.MAX_VALUE);
        fail("Exception NoSuchElementException has not been thrown!");
    }

    @Test
    public void testOneSimpleUnion() {
        System.out.println("Testing one simple union");
        if (uf.union(1, 2)) {
            if (uf.find(1) != 1 || uf.find(2) != 1) {
                fail("Union failed!");
            }
        } else {
            fail("Union failed!");
        }
    }

    @Test
    public void testUnionWithinSameComponent() {
        System.out.println("Testing union two nodes with the same leader");
        boolean result = true;
        result &= uf.union(1, 2);
        result &= uf.union(2, 3);
        if (result) {
            if (uf.union(1, 3)) {
                fail("Nodes with same element should not be united!");
            }
        } else {
            fail("Union failed!");
        }
    }

    
}
