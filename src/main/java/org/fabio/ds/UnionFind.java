package org.fabio.ds;

/**
 * Simple UnionFind data structure. My first attempt to create such data
 * structure. The goal is to have the union done in O(1) and the find done in
 * O(logN), where N is the number of nodes.
 * 
 * It implements a rank to define the new leader of a united component and avoid
 * linear (O(N)) times for the find operation.
 * 
 * @author Fabio Fonseca
 *
 */
public class UnionFind {
    private final int[] nodes;
    private int[] ranks;

    /**
     * Constructor for the UnionFind data structure. Creates the internal array
     * to hold the nodes and the components.
     * 
     * @param n
     *            the number of nodes on the union-find data structure.
     */
    public UnionFind(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("number of nodes must be greater than 0");
        }

        // create and initialize the nodes array
        nodes = new int[n];

        for (int i = 0; i < n; i++) {
            nodes[i] = i;
        }

        // create and initialize the ranks array
        ranks = new int[n];

        for (int i = 0; i < n; i++) {
            ranks[i] = 1;
        }
    }

    /**
     * Return the leader node that names the component where "node" is contained
     * 
     * @param node
     *            the node to have its leader found
     * @return the leader node of the component where "node" is contained
     */
    public int find(int node) {
        if (node < 0) {
            throw new java.lang.IllegalArgumentException("node must be a positive number");
        }

        if (node > nodes.length) {
            throw new java.util.NoSuchElementException(
                    "node must be an existing element (between 0 and "
                            .concat(Integer.toString(nodes.length - 1)));
        }

        int n = nodes[node];
        while (n != nodes[n]) {
            n = nodes[n];
        }

        return n;
    }

    /**
     * The union operation joins together two components. The first one contains
     * n1 and the second contains n2.
     * 
     * @param n1
     *            The first node to be united.
     * @param n2
     *            The second node to be united.
     * @return true if the union was executed or false if not (for instance n1
     *         and n2 already part of the same component).
     */
    public boolean union(int n1, int n2) {
        int n1l = find(n1);
        int n2l = find(n2);

        // compare nodes component leaders to check if they belong to the same
        // component
        if (n1l == n2l) {
            return false;
        }

        // among their respective leaders select the highest ranked one to be
        // leader of the united component
        if (ranks[n1l] > ranks[n2l]) {
            // n1l should be the new leader and its rank would remain the same
            nodes[n2l] = n1l;
        } else {
            if (ranks[n1l] < ranks[n2l]) {
                // n2 should be the new leader keeping its rank
                nodes[n1l] = n2l;
            } else {
                // by default we select n1l as the new leader
                nodes[n2l] = n1l;
                // and increment its rank
                ranks[n1l]++;
            }
        }
        return true;
    }

    // for unit tests purposes
    public static void main(String[] args) {
        int n = 10;
        System.out.println("Starting unit tests for a UF of size ".concat(Integer.toString(n)));
        UnionFind uf = new UnionFind(n);
        
        System.out.print("Verifying arrays initialization... ");
        boolean verifyArrays = true;
        for (int i = 0; i < n; i++) {
            // checking nodes
            if (uf.nodes[i] != i) {
                verifyArrays = false;
            }
            // checking ranks
            if (uf.ranks[i] != 1) {
                verifyArrays = false;
            }
        }
        if (verifyArrays) {
            System.out.println("[OK]");
        } else {
            System.out.println("[FAILED!]");
        }
        
        // testing exceptions
        System.out.print("Verifying negative argument in find operation... ");
        boolean exceptionsTest = false;
        try {
            uf.find(-1);
        } catch(Exception e) {
            if (e.getClass().getName().equals("java.lang.IllegalArgumentException")) {
                exceptionsTest = true;
            }
        }
        if (exceptionsTest) {
            System.out.println("[OK]");
        } else {
            System.out.println("[FAILED!]");
        }

}
}
