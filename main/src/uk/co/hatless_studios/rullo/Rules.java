package uk.co.hatless_studios.rullo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Methods are listed in the order (descending) in which they should be used.
 * Each is logically both more powerful, and faster than the next.
 */
class Rules {
    /**
     * Tests if every row in a matrix is fully locked.
     * @param matrix The matrix to be checked.
     * @return True if solved, else False.
     */
    static boolean checkSolved(Node[][] matrix) {
        for (Node[] row : matrix) {
            if (!allLocked(row)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all nodes in the list are locked.
     * @param row A list of nodes.
     * @return True if all nodes are locked, else False.
     */
    static boolean allLocked(Node[] row) {
        for (Node node : row) {
            if (!node.isLocked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the rowSum is equal to the aim, and locks everything if it is.
     * @param row A list of nodes.
     * @param aim The row's edge node.
     */
    static void checkSum(Node[] row, int aim) {
        if (rowSum(row) == aim) {
            for (Node node : row) {
                node.setLock();
            }
        }
    }

    /**
     * Fixes a final unlocked node within a row.
     * @param row A list of nodes.
     * @param aim The row's edge node.
     */
    static void finalElement(Node[] row, int aim) {
        int count = 0;
        for (Node node : row) {
            if (!node.isLocked()) {
                count++;
            }
        }
        if (count == 1) {
            if (rowSum(row) == aim){
                for (Node node : row) {
                    node.setLock();
                }
            }
            else{
                for (Node node : row) {
                    if (!node.isLocked()) {
                        node.setState();
                        node.setLock();
                        return;
                    }
                }
            }
        }
    }

    /**
     * Locks nodes that would dip the row sum below the aim.
     * @param row List of nodes in the row.
     */
    static void valDiff(Node[] row, int aim) {
        int rowDiff = rowSum(row) - aim;
        for (Node node : row) {
            if (node.getValue() > rowDiff) {
                node.setLock();
            }
        }
    }

    /**
     * Checks if a node can be eliminated purely based on its oddness.
     * @param row The row of nodes being checked.
     * @param aim The row's end node.
     */
    static void singleOdd(Node[] row, int aim) {
        if (((rowSum(row) - aim) % 2) == 0){
            return;
        }
        int count = 0;
        for (Node node : row) {
            if (node.isOdd() && node.isOn() && !node.isLocked()) {
                count++;
            }
        }
        if (count == 1) {
            for (Node node : row) {
                if (node.isOdd()) {
                    node.setState();
                    node.setLock();
                    return;
                }
            }
        }
    }

    /**
     * Checks if any particular row value must be on XOR off due to presence in partitions of the aim.
     * If there is such a row value, its node will be locked in the appropriate position.
     * @param row List of nodes.
      * @param aim The row edge node.
     */
    static void singlePartition(Node[] row, int aim) {
        int sum = rowSum(row);
        if (sum == aim) {
            for (Node node : row) {
                node.setLock();
            }
        }
        if (sum < aim) throw new IllegalStateException("impossible to reach aim");
        boolean mod;
        while (true) {
            mod = false;
            Map<Integer, List<Node>> duplicates = new HashMap<>();
            for (Node node : row) {
                if (!duplicates.containsKey(node.getValue())) {
                    duplicates.put(node.getValue(), new ArrayList<>());
                } else {
                    mod = true;
                }
                duplicates.get(node.getValue()).add(node);
            }
            if (!mod) break;
            List<Node> pruned = new ArrayList<>(row.length);
            for (List<Node> nodes : duplicates.values()) {
                if (nodes.size() == 1) {
                    pruned.add(nodes.get(0));
                } else {
                    pruned.add(new MergedNode(nodes.toArray(row)));
                }
            }
            row = pruned.toArray(row);
        }
        switch (row.length) {
            case 0: return;
            case 1: throw new IllegalStateException("impossible to reach aim");
        }
        Deque<Frame> stack = new ArrayDeque<>(row.length);
        stack.add(new Frame(row[0], sum, new int[row.length]));
        while (stack.size() > 0) {
            Frame current = stack.peekLast();
            sum = current.sum - current.node.getValue();
            if (sum == 0) {
                current.node.setState();
                for (Node node : row) if (node != current.node) node.setLock();
                stack.removeLast();
            } else if (sum < 0 || current.unvisited.length == 0) {
                stack.removeLast();
            } else {
                stack.add(new Frame(row[0], sum, Arrays.copyOf(current.unvisited, --current.unvisitedLength)));
            }
        }
    }

    /**
     * Sums the node values within a row.
     * @param row The row to be evaluated.
     * @return The total value.
     */
    private static int rowSum(Node[] row) {
        int sum = 0;
        for (Node node :
                row) {
            if (node.isOn()){
                sum += node.getValue();
            }
        }
        return sum;
    }

    /**
     * Merges two nodes into one.
     */
    private static class MergedNode extends Node {
        private Node[] nodes;

        MergedNode(Node[] nodes) {
            super(rowSum(nodes));
            this.nodes = nodes;
        }

        @Override
        void setLock() {
            super.setLock();
            for (Node node : nodes) node.setLock();
        }

        @Override
        void setState() {
            super.setState();
            for (Node node : nodes) node.setState();
        }
    }

    /**
     * Used for stack.
     */
    private static class Frame {
        private Node node;
        private int sum;
        private int[] unvisited;
        private int unvisitedLength;

        private Frame(Node node, int sum, int[] unvisited) {
            this.node = node;
            this.sum = sum;
            this.unvisited = unvisited;
            unvisitedLength = unvisited.length;
        }
    }
}
