package uk.co.hatless_studios.rullo;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {
    private static final Insets insets = new Insets(5, 5, 5, 5);
    private static Node[][] matrix;
    private final Object lock;
    private JFrame frame;
    private JPanel dimPanel;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JTextField widthField;
    private JTextField heightField;
    private JButton cancelButton;
    private JButton nextButton;
    private JPanel tblPanel;
    private JScrollPane tblPane;
    private JTable nodeTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private JButton startButton;

    public static void main(String[] args) {
        System.out.println("Rullo solver is running...");
        Main main = new Main();
        int m, n;
        synchronized (main.lock) {
            main.initializeDim();
            main.showDim();
            main.initializeTbl();
        }

        /*for (int i = 0; i < m; i++){
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
        }*/

        // TODO Forward matrix to GUI
    }

    private Main() {
        lock = new Object();
        frame = new JFrame("Rullo Solver");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeDim() {
        dimPanel = new JPanel(new GridBagLayout());
        widthLabel = new JLabel("Width:");
        heightLabel = new JLabel("Height:");
        widthField = new JTextField();
        heightField = new JTextField();
        cancelButton = new JButton("Cancel");
        nextButton = new JButton("Next");
        widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1,
                .6D, .6D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 5, 5);
        dimPanel.add(widthLabel, constraints);
        constraints.gridy++;
        dimPanel.add(heightLabel, constraints);
        constraints.gridy++;
        dimPanel.add(cancelButton, constraints);
        constraints.gridx++;
        dimPanel.add(nextButton, constraints);
        constraints.weightx = 1D;
        constraints.weighty = 1D;
        constraints.gridy--;
        dimPanel.add(heightField, constraints);
        constraints.gridy--;
        dimPanel.add(widthField, constraints);
        widthField.addKeyListener(this);
        heightField.addKeyListener(this);
        cancelButton.addKeyListener(this);
        nextButton.addKeyListener(this);
        cancelButton.addActionListener(event -> System.exit(0));
        nextButton.addActionListener(event -> onNext());
    }

    private void initializeTbl() {
        tblPanel = new JPanel(new BorderLayout());
        nodeTable = new JTable(tableModel = new DefaultTableModel());
        tblPane = new JScrollPane(nodeTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tblPanel.add(tblPane, BorderLayout.CENTER);
        nodeTable.setTableHeader(null);
        nodeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void showDim() {
        frame.add(dimPanel);
        frame.setResizable(false);
        frame.setSize(160, 140);
        frame.setLocationRelativeTo(null);
        frame.invalidate();
        frame.setVisible(true);
    }

    private void showTbl(int m, int n) {
        tableModel.setRowCount(m);
        tableModel.setColumnCount(n);
        frame.remove(dimPanel);
        frame.add(tblPanel);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.invalidate();
        frame.setVisible(true);
    }

    private void onNext() {
        synchronized (lock) {
            showTbl(Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getComponent() instanceof JTextField &&e.getKeyChar() < '0' || e.getKeyChar() > '9') {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (frame.getFocusOwner() != cancelButton) {
                    onNext();
                    e.consume();
                    break;
                }
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
