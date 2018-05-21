package uk.co.hatless_studios.rullo;

import java.util.ArrayList;

/**
 * Methods are listed in the order (descending) in which they should be used.
 * Each is logically both more powerful, and faster than the next.
 */
public class Rules {

    /**
     * Checks if all nodes in the list are locked.
     * @param row A list of nodes.
     * @return True if all nodes are locked, else False.
     */
    boolean allLocked(Node[] row){
        for (Node node : row) {
            if (!node.isLocked()){
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
    void checkSum(Node[] row, int aim){
        if (rowSum(row) == aim){
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
    void finalElement(Node[] row, int aim){

        int count = 0;
        for (Node node : row) {
            if (!node.isLocked()){
                count++;
            }
        }
        if (count == 1){
            if (rowSum(row) == aim){
                for (Node node : row) {
                    node.setLock();
                }
            }
            else{
                for (Node node : row) {
                    if (!node.isLocked()){
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
    void valDiff(Node[] row, int aim){
        int rowDiff = rowSum(row) - aim;
        for (Node node : row) {
            if (node.getValue() > rowDiff){
                node.setLock();
            }
        }
    }

    /**
     * Checks if a node can be eliminated purely based on its oddness.
     * @param row The row of nodes being checked.
     * @param aim The row's end node.
     */
    void singleOdd(Node[] row, int aim){
        if (((rowSum(row) - aim) % 2) == 0){
            return;
        }
        int count = 0;
        for (Node node : row) {
            if (node.isOdd() && node.isOn() && !node.isLocked()){
                count++;
            }
        }
        if (count == 1){
            for (Node node : row) {
                if (node.isOdd()){
                    node.setState();
                    node.setLock();
                    return;
                }
            }
        }
    }

    /**
     * Checks if any particular row value must be on XOR off due to presence in partitions of the aim.
     * @param row List of nodes.
     * @param aim The row edge node.
     */
    void singlePartition(Node[] row, int aim){

        ArrayList<Node> unlocked = new ArrayList<>();
        int diff = rowSum(row) - aim;

        for (Node node : row) {
            if (!node.isLocked()){
                unlocked.add(node);
            }
        }

    }

    /**
     * Sums the node values within a row.
     * @param row The row to be evaluated.
     * @return The total value.
     */
    private int rowSum(Node[] row){
        int sum = 0;
        for (Node node :
                row) {
            if (node.isOn()){
                sum += node.getValue();
            }
        }
        return sum;
    }
}
