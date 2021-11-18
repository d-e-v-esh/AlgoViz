package com.dev.algoviz;

import java.util.Comparator;

class NodeComparator implements Comparator<Node> {

    public int compare(Node a, Node b) {
        return a.getWeight() - b.getWeight();
    }
}