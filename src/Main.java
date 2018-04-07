import java.util.Scanner;

public class Main {

    private static Node[][] matrix;

    public static void main(String[] args){

        System.out.println("Rullo solver is running...");

        int node_val;
        int m = getIntInput("Please enter the width of your matrix."); // x direction
        int n = getIntInput("Please enter the height of your matrix."); // y direction
        matrix = new Node[n][m];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                node_val = getIntInput(String.format("Please enter the node value for position y=[%d] x=[%d]", i + 1, j + 1));
                matrix[i][j] = new Node(node_val);
            }
        }

        printMatrix();

    }

    private static int getIntInput(String prompt){

        System.out.println(prompt);
        Scanner inputter = new Scanner(System.in);

        return inputter.nextInt();
    }

    private static void printMatrix(){
        for (Node[] j: matrix) {
            for (Node i: j){
                System.out.print(String.format("[%d]", i.getValue()));
            }
            System.out.println();
        }
    }
}
