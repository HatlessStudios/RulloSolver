package uk.co.hatless_studios.rullo;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.IntStream;

class Solver {
    /**
     * Solves the matrix without showing step by step.
     * @param matrix The matrix to be solved.
     * @param rowVec The row sum vector.
     * @param colVec The column sum vector.
     */
    static void quickSolve(Node[][] matrix, int[] rowVec, int[] colVec, Runnable callback) {
        Deque<Row> queue = new ArrayDeque<>();
        Row[] columns = new Row[colVec.length];
        for (int c = 0; c < colVec.length; c++) columns[c] = new Row(new Node[rowVec.length], colVec[c]);
        for (int r = 0; r < matrix.length; r++) {
            Row row = new Row(matrix[r], columns, rowVec[r]);
            queue.add(row);
            for (int c = 0; c < matrix[r].length; c++) {
                columns[c].getNodes()[r] = matrix[r][c];
                columns[c].getRows()[r] = row;
            }
        }
        queue.addAll(Arrays.asList(columns));
        while (!queue.isEmpty()) nextStep(queue, callback);
    }

    /**
     * Runs through the algorithm hierarchy.
     * @param queue The queue of rows to update.
     */
    private static void nextStep(Deque<Row> queue, Runnable callback) {
        Row current = queue.remove();
        Node[] nodes = current.getNodes();
        boolean[] inOne = new boolean[nodes.length];
        boolean[] inAll = new boolean[nodes.length];
        Arrays.fill(inAll, true);
        int aim = current.getAim();
        for (int index = 0; index < nodes.length; index++) {
            if (nodes[index].isLocked()) {
                inOne[index] = true;
                inAll[index] = false;
                if (nodes[index].isOn()) aim -= nodes[index].getValue();
            }
        }
        Deque<Frame> stack = new ArrayDeque<>();
        for (int index = 0; index < nodes.length; index++) {
            if (!nodes[index].isLocked()) stack.add(new Frame(index, nodes.length, 0, inAll));
        }
        while (!stack.isEmpty()) {
            Frame frame = stack.getLast();
            int sum = frame.sum + nodes[frame.index].getValue();
            if (sum == aim) {
                for (int index = 0; index < frame.visited.length; index++) {
                    if (frame.visited[index]) inOne[index] = true;
                    else inAll[index] = false;
                }
                stack.removeLast();
            } else if (sum > aim || frame.unvisitedLength == 0) {
                stack.removeLast();
            } else {
                stack.add(frame.getNext(sum));
            }
        }
        for (int index = 0; index < nodes.length; index++) {
            if (!inOne[index]) current.setState(queue, index, callback);
            if (inAll[index]) current.setLock(queue, index, callback);
        }
    }

    private static class Frame {
        private int index;
        private int[] unvisited;
        private int unvisitedLength;
        private boolean[] visited;
        private int sum;

        private Frame(int index, int length, int sum, boolean[] filter) {
            this.index = index;
            unvisited = IntStream.range(0, length).filter(v -> v != index).filter(v -> filter[v]).toArray();
            unvisitedLength = unvisited.length;
            visited = new boolean[length];
            visited[index] = true;
            this.sum = sum;
        }

        private Frame(int index, int[] unvisited, boolean[] visited, int sum) {
            this.index = index;
            this.unvisited = unvisited;
            unvisitedLength = unvisited.length;
            this.visited = visited;
            visited[index] = true;
            this.sum = sum;
        }

        private Frame getNext(int sum) {
            int index = unvisited[--unvisitedLength];
            return new Frame(index, Arrays.copyOf(unvisited, unvisitedLength), Arrays.copyOf(visited, visited.length), sum);
        }
    }
}
