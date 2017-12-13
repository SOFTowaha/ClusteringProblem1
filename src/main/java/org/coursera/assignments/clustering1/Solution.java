package org.coursera.assignments.clustering1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.fabio.ds.UnionFind;

public class Solution {
    
    public static void main(String[] args) throws Exception {
        // create the Edge class to represent an edge - OK
        // read the file and create an array of edges
        ArrayList<Edge> edgesList = new ArrayList<Edge>();
        // check command line arguments
        if (args.length < 1) {
            System.out.println("Need one parameter (for instance clustering1.txt)");
            return;
        }

        // open file
        File file = new File(args[0]);

        Scanner in;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // read its content
        int n = in.nextInt(); // number of nodes

        while (in.hasNext()) {
            int n1 = in.nextInt();
            int n2 = in.nextInt();
            int cost = in.nextInt();

            edgesList.add(new Edge(n1, n2, cost));
        }

        in.close();

        Edge[] edges = new Edge[0];
        edges = edgesList.toArray(edges);
        Arrays.sort(edges);

        // now we have a sorted array of edges with n nodes
        // to begin creating the clusters we initiate with n clusters and each
        // iteration we decrease the number of components by 1
        // I need a proper data structure to hold the clusters I will create
        // UnionFind is a good one, so let's code a simple union-find based on a
        // int key (that will be the index of the array)
        
        UnionFind uf = new UnionFind(n);
        
        int components = n;
        ArrayList<Integer> remaining = new ArrayList<Integer>();
        for (int i = 0; i < edges.length; i++) {
            if (uf.find(edges[i].node1) != uf.find(edges[i].node2)) {
                if (components > 4) {
                    boolean result = uf.union(edges[i].node1, edges[i].node2);
                    components--;
                    if (!result) {
                        throw new Exception("some error occurred!");
                    }
                } else {
                    remaining.add(edges[i].cost);
                }
            }
        }
        
        Integer[] aRemaining = new Integer[0];
        aRemaining = remaining.toArray(aRemaining);
        Arrays.sort(aRemaining);
        System.out.println(aRemaining[0]);
        System.out.println(uf);

    }

}
