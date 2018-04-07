import java.awt.*;
import javax.swing.*;
import graphics.CircleLabel;

public class GUI extends JFrame{

    private Label lblCount;
    JPanel solution;

    GUI(int m, int n, Node[][] matrix){

        //Creates window with solution heading and with solution pane
        setLayout(new FlowLayout());
        lblCount = new Label("Solution");
        add(lblCount);

        //Set window properties
        setTitle("Rullo Solution");
        setSize(1000, 1000);
        setLocation(250, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        //Generate solution panel and pack into window
        panel(m, n, matrix);
        this.getContentPane().add(solution, BorderLayout.CENTER);
        this.pack();
        setSize(1000, 1000);
        setVisible(true);
    }

    public static void main(String[] args){
        GUI app = new GUI(2, 2, new Node[][] {{new Node(1), new Node(1)}, {new Node(1), new Node(1)}});
    }

    /**
     * Creates the panel in the grid layout
     * @param m, n The dimensions of the grid
     */
    void panel(int m, int n, Node[][] matrix){

        solution = new JPanel();
        solution.setLayout(new GridLayout(m,n));

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                add(new CircleLabel(" " + Integer.toString(matrix[j][i].getValue()), matrix[j][i].getState() ? Color.YELLOW : Color.BLACK));
            }
        }
    }
}
