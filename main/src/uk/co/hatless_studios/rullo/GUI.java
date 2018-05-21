package uk.co.hatless_studios.rullo;


import uk.co.hatless_studios.rullo.graphics.CircleLabel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;


public class GUI extends JFrame {

    private Label lblCount;
    JPanel solution;

    GUI(int m, int n, Node[][] matrix){

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
        setLocation(250, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        //Generate solution panel and pack into window
        panel(m, n, matrix);
        this.getContentPane().add(solution, BorderLayout.CENTER);
        this.pack();
        setVisible(true);
    }

    public static void main(String[] args){
        GUI app = new GUI(3, 3, new Node[][] {{new Node(1), new Node(1), new Node(1)}, {new Node(1), new Node(1), new Node(1)}, {new Node(1), new Node(1), new Node(1)}});
    }

    /**
     * Creates the panel in the grid layout
     * @param m, n The dimensions of the grid
     */
    void panel(int m, int n, Node[][] matrix){

        solution = new JPanel();
        solution.setLayout(new GridBagLayout());
        solution.setBackground(Color.BLACK);

        GridBagConstraints constraints = new GridBagConstraints(0, 0, n, 1, 0D, 0D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 10, 10);
        solution.add(lblCount, constraints);

        constraints.gridwidth = 1;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                constraints.gridx = i;
                constraints.gridy = j + 1;
                solution.add(new CircleLabel(Integer.toString(matrix[j][i].getValue()), matrix[j][i].getState() ? Color.GREEN : Color.RED, new Dimension((this.getWidth()/m) - 50, (this.getHeight()/n) - 50)), constraints);
            }
        }
    }
}
