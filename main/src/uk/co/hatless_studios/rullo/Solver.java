package uk.co.hatless_studios.rullo;

public class Solver {

    public static void quickSolve(Node[][] matrix, int[] rowVec, int[] colVec){

        boolean solved = false;

        while (!solved){
            solved = true;
            for (Node[] row : matrix) {
                if (!Rules.allLocked(row)){solved = false;}
            }
        }

    }
}
