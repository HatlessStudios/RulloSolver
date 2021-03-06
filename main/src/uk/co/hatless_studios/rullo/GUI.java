package uk.co.hatless_studios.rullo;


import uk.co.hatless_studios.rullo.graphics.CircleLabel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

class GUI extends JFrame {
    private Label lblCount;
    private JPanel solution;

    GUI(int m, int n, Node[][] matrix, int[] rowSums, int[] columnSums) {
        //Creates window with solution heading and with solution pane
        setLayout(new BorderLayout(0, 10));
        this.getContentPane().setBackground(Color.BLACK);
        lblCount = new Label("Solution");
        lblCount.setAlignment(Label.CENTER);
        lblCount.setForeground(Color.WHITE);
        lblCount.setFont(new Font("Serif", Font.BOLD, 36));

        //Set window properties
        setTitle("Rullo Solution");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Generate solution panel and pack into window
        createPanel(m, n, matrix, rowSums, columnSums);
        this.getContentPane().add(solution, BorderLayout.CENTER);
        this.pack();
        setLocationRelativeTo(null);
        setVisible(true);
        createBufferStrategy(2);
        setVisible(true);
    }

    /**
     * Creates the panel in the grid layout
     * @param m, n : The dimensions of the grid
     * @param matrix : The matrix of nodes
     */
    private void createPanel(int m, int n, Node[][] matrix, int[] rowSums, int[] columnSums) {
        //Create panel and set layout and properties
        solution = new JPanel();
        solution.setLayout(new GridBagLayout());
        solution.setBackground(Color.BLACK);

        GridBagConstraints constraints = new GridBagConstraints(0, 0, n, 1, 0D, 0D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 20, 20);
        solution.add(lblCount, constraints);

        constraints.gridwidth = 1;

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++){
                if (Integer.toString(matrix[j][i].getValue()).length() > maxLength)
                    maxLength = Integer.toString(matrix[j][i].getValue()).length();
            }
        }

        for (int i = 0; i < n; i++) {
            if (Integer.toString(columnSums[i]).length() > maxLength)
                maxLength = Integer.toString(columnSums[i]).length();
        }

        for (int j = 0; j < m; j++){
            if (Integer.toString(rowSums[j]).length() > maxLength)
                maxLength = Integer.toString(rowSums[j]).length();
        }

        //Insert each row and column
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                constraints.gridx = i;
                constraints.gridy = j + 1;
                solution.add(new CircleLabel(matrix[j][i], new Dimension((this.getWidth()/m) - 50, (this.getHeight()/n) - 50), maxLength), constraints);
            }

            //Add column sum to end of column
            constraints.gridx = i;
            constraints.gridy = m + 1;
            solution.add(new CircleLabel(columnSums[i], new Dimension((this.getWidth()/m) - 50, (this.getHeight()/n) - 50), maxLength), constraints);
        }

        //Add row sums to end of rows
        for (int j = 0; j < m; j++){
            constraints.gridx = n;
            constraints.gridy = j + 1;
            solution.add(new CircleLabel(rowSums[j], new Dimension((this.getWidth()/m) - 50, (this.getHeight()/n) - 50), maxLength), constraints);
        }
    }
}
