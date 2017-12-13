package org.fabio.ds;

import java.util.ArrayList;
import java.util.HashMap;

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
            getNodes()[i] = i;
        }

        // create and initialize the ranks array
        setRanks(new int[n]);

        for (int i = 0; i < n; i++) {
            getRanks()[i] = 1;
        }
    }

    /**
     * @return the ranks
     */
    public int[] getRanks() {
        return ranks;
    }

    /**
     * @param ranks
     *            the ranks to set
     */
    public void setRanks(int[] ranks) {
        this.ranks = ranks;
    }

    public int[] getNodes() {
        return nodes;
    }

    /**
     * Return the leader node that names the component where "node" is contained
     * 
     * @param node
     *            the node to have its leader found
     * @return the leader node of the component where "node" is contained
     */
    public int find(int node) {
        node--;
        if (node < 0) {
            throw new java.lang.IllegalArgumentException("node must be a positive number");
        }

        if (node > getNodes().length) {
            throw new java.util.NoSuchElementException(
                    "node must be an existing element (between 0 and "
                            .concat(Integer.toString(getNodes().length - 1)));
        }

        int n = getNodes()[node];
        while (n != getNodes()[n]) {
            n = getNodes()[n];
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
        if (getRanks()[n1l] > getRanks()[n2l]) {
            // n1l should be the new leader and its rank would remain the same
            getNodes()[n2l] = n1l;
        } else {
            if (getRanks()[n1l] < getRanks()[n2l]) {
                // n2 should be the new leader keeping its rank
                getNodes()[n1l] = n2l;
            } else {
                // by default we select n1l as the new leader
                getNodes()[n2l] = n1l;
                // and increment its rank
                getRanks()[n1l]++;
            }
        }
        return true;
    }

    /**
     * Prints the clusters
     */
    public String toString() {
        HashMap<Integer, ArrayList<Integer>> clusters = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < nodes.length; i++) {
            int nl = find(i+1);
            ArrayList<Integer> alist;
            if ((alist = clusters.get(nl)) != null) {
                alist.add(i);
            } else {
                alist = new ArrayList<Integer>();
                alist.add(i);
                clusters.put(nl, alist);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Integer> a : clusters.values()) {
            sb.append("[");
            for (Integer in : a) {
                sb.append(in).append(", ");
            }
            sb.delete(sb.length()-2, sb.length()).append("]; ");
        }
        return sb.delete(sb.length()-2, sb.length()).toString();
    }

}
