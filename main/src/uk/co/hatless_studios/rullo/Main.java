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
import javax.swing.table.AbstractTableModel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {
    private static final Insets insets = new Insets(5, 5, 5, 5);

    private final Object lock;
    private final JFrame frame;

    private JPanel dimPanel;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JTextField widthField;
    private JTextField heightField;
    private JButton cancelButton;
    private JButton nextButton;

    private JPanel tblPanel;
    private JPanel dummyPanel;
    private JScrollPane tblPane;
    private JTable nodeTable;
    private MatrixModel tableModel;
    private JButton backButton;
    private JButton startButton;

    public static void main(String[] args) {
        System.out.println("Rullo solver is running...");
        Main main = new Main();
        synchronized (main.lock) {
            main.initializeDim();
            main.showDim();
            main.initializeTbl();
        }
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
        tblPanel = new JPanel(new GridBagLayout());
        dummyPanel = new JPanel();
        nodeTable = new JTable(tableModel = new MatrixModel());
        tblPane = new JScrollPane(nodeTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        backButton = new JButton("Back");
        startButton = new JButton("Start");
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 3, 1,
                1D, 1D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        tblPanel.add(tblPane, constraints);
        constraints.gridy++;
        constraints.gridwidth = 1;
        constraints.weighty = constraints.weightx = 0D;
        tblPanel.add(backButton, constraints);
        constraints.gridx++;
        constraints.weightx = 1D;
        tblPanel.add(dummyPanel, constraints);
        constraints.gridx++;
        constraints.gridwidth = 1;
        constraints.weightx = 0D;
        tblPanel.add(startButton, constraints);
        nodeTable.setTableHeader(null);
        nodeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        backButton.addActionListener(event -> showDim());
        startButton.addActionListener(event -> onStart());
    }

    private void showDim() {
        if (tblPanel != null) frame.remove(tblPanel);
        frame.add(dimPanel);
        frame.setResizable(false);
        frame.setSize(160, 140);
        frame.setLocationRelativeTo(null);
        frame.invalidate();
        frame.setVisible(true);
    }

    private void showTbl(int m, int n) {
        tableModel.setSize(m, n);
        frame.remove(dimPanel);
        frame.add(tblPanel);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.invalidate();
        frame.setVisible(true);
    }

    private void onNext() {
        if (widthField.getText().length() == 0 || heightField.getText().length() == 0) return;
        synchronized (lock) {
            showTbl(Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()));
        }
    }

    private void onStart() {
        frame.setVisible(false);
        new GUI(tableModel.rows, tableModel.columns, tableModel.nodes, tableModel.rowSums, tableModel.columnSums);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getComponent() instanceof JTextField && e.getKeyChar() < '0' || e.getKeyChar() > '9' || ((JTextField) e.getComponent()).getText().length() >= 3) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (frame.getFocusOwner() instanceof JButton) {
                    ((JButton) frame.getFocusOwner()).doClick();
                } else {
                    onNext();
                    e.consume();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private class MatrixModel extends AbstractTableModel {
        private Node[][] nodes;
        private int[] rowSums;
        private int[] columnSums;
        private int rows;
        private int columns;

        private MatrixModel() {
            setSize(0, 0);
        }

        @Override
        public int getRowCount() {
            return rows + 1;
        }

        @Override
        public int getColumnCount() {
            return columns + 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex == 0 && columnIndex == 0) return null;
            else if (rowIndex == 0) return columnSums[columnIndex - 1];
            else if (columnIndex == 0) return rowSums[rowIndex - 1];
            else return nodes[rowIndex - 1][columnIndex - 1].getValue();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (rowIndex == 0) columnSums[columnIndex - 1] = (int) aValue;
            else if (columnIndex == 0) rowSums[rowIndex - 1] = (int) aValue;
            else nodes[rowIndex - 1][columnIndex - 1] = new Node((int) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return rowIndex != 0 || columnIndex != 0;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return Integer.class;
        }

        private void setSize(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            rowSums = new int[rows];
            columnSums = new int[columns];
            nodes = new Node[rows][columns];
            for (int r = 0; r < rows; r++) for (int c = 0; c < columns; c++) nodes[r][c] = new Node(0);
            fireTableStructureChanged();
        }
    }
}
