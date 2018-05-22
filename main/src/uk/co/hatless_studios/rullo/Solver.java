package uk.co.hatless_studios.rullo;

public class Solver {
    /**
     * Solves the matrix without showing step by step.
     * @param matrix The matrix to be solved.
     * @param rowVec The row sum vector.
     * @param colVec The column sum vector.
     */
    public static Node[][] quickSolve(Node[][] matrix, int[] rowVec, int[] colVec) {
        // rOrC is false when checking rows, and true when checking columns.
        boolean rOrC = false;

        while (!Rules.checkSolved(matrix)) {
            if (!rOrC){
                nextStep(matrix, rowVec);
            }
            else {
                nextStep(matrix, colVec);
            }
            matrix = transposeMatrix(matrix);
            rOrC = !rOrC;
        }
        if (rOrC){
            return transposeMatrix(matrix);
        }
        return matrix;
    }

    public static void showSolve(Node[][] matrix, int[] rowVec, int[] colVec) {
        // TODO ???
    }

    /**
     * Finds the transpose of a matrix such that columns can be checked more easily.
     * @param startMatrix The matrix to be transposed.
     * @return The transposed matrix.
     */
    private static Node[][] transposeMatrix(Node[][] startMatrix) {
        int newLength = startMatrix.length;
        int newHeight = startMatrix[0].length;
        Node[][] newMatrix= new Node[newHeight][newLength];

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newLength; j++) {
                newMatrix[i][j] = startMatrix[j][i];
            }
        }
        return newMatrix;
    }

    /**
     * Runs through the algorithm hierarchy.
     * @param matrix The matrix to be adjusted.
     * @param rowVec Sum vector for the rows.
     */
    private static void nextStep(Node[][] matrix, int[] rowVec) {
        for (int i = 0; i < matrix.length; i++) {
            if (!Rules.allLocked(matrix[i])) {
                Rules.checkSum(matrix[i], rowVec[i]);
                Rules.finalElement(matrix[i], rowVec[i]);
                Rules.valDiff(matrix[i], rowVec[i]);
                Rules.singleOdd(matrix[i], rowVec[i]);
                Rules.singlePartition(matrix[i], rowVec[i]);
            }
        }
    }
}
