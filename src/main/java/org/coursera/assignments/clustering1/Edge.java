package org.coursera.assignments.clustering1;

public class Edge implements Comparable<Edge> {
    int node1;
    int node2;
    int cost;

    public Edge(int n1, int n2, int c) {
        node1 = n1;
        node2 = n2;
        cost = c;
    }

    int n1() {
        return node1;
    }

    int n2() {
        return node2;
    }

    int cost() {
        return cost;
    }

    public String toString() {
        return new StringBuilder().append("[").append(node1).append(" --(").append(cost)
                .append(")-- ").append(node2).append("]").toString();
    }

    public int compareTo(Edge that) {
        if (cost > that.cost) {
            return 1;
        }
        if (cost < that.cost) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        Edge eThat = (Edge) that;
        if (this.node1 == eThat.node1 && this.node2 == eThat.node2 && this.cost == eThat.cost) {
            return true;
        }
        return false;
    }

}
