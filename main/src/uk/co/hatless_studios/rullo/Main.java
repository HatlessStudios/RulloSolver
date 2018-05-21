package uk.co.hatless_studios.rullo;

import java.util.Scanner;

public class Main {

    private static Node[][] matrix;

    public static void main(String[] args){

        System.out.println("Rullo solver is running...");

        int node_val;
        int m = getIntInput("Please enter the width of your matrix."); // x direction
        int n = getIntInput("Please enter the height of your matrix."); // y direction
        matrix = new Node[m][n];

        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (j != n - 1 || i != m - 1) {
                    node_val = getIntInput(String.format("Please enter the node value for position x=[%d] y=[%d]", j + 1, i + 1));

                    if (j == n - 1 || i == m - 1)
                        matrix[i][j] = new RCDiff(node_val);
                    else
                        matrix[i][j] = new Node(node_val);
                }
                else
                    matrix[i][j] = new Node(0);
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
