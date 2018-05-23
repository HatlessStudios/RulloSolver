package uk.co.hatless_studios.rullo.graphics;

import uk.co.hatless_studios.rullo.Node;

import javax.swing.JLabel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;


public class CircleLabel extends JLabel {
    private Node node;
    private float thickness;

    public CircleLabel(Node node, Dimension size, int width) {
        this(node.getValue(), size, width);
        this.node = node;
    }

    public CircleLabel(int value, Dimension size, int width) {
        super(Integer.toString(value));
        this.thickness = size.height/10;
        setFont(new Font("Serif", Font.BOLD, (size.width / width)));
        setHorizontalAlignment(CENTER);
        setForeground(Color.WHITE);
        super.setPreferredSize(size);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.height = size.width = Math.max(size.width, size.height);
        return size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            draw2D(g2d);
        } else {
            draw(g);
        }
        g.dispose();
    }

    private void draw(Graphics g) {
        Insets insets = getInsets();
        int width = (getWidth() - (insets.left + insets.right));
        int height = getHeight() - (insets.top + insets.bottom);
        int radius = Math.max(width, height);
        int x = insets.left + ((width - radius) / 2);
        int y = insets.top + ((height - radius) / 2);
        g.setColor(getBackgroundColor());
        g.fillOval(x, y, radius, radius);
        super.paintComponent(g);
        g.setColor(getColor());
        g.drawOval(x, y, radius, radius);
    }

    private void draw2D(Graphics2D g2d) {
        Insets insets = getInsets();
        int width = (getWidth() - (insets.left + insets.right));
        int height = getHeight() - (insets.top + insets.bottom);
        double radius = Math.max(width, height) - thickness;
        double x = insets.left + ((width - radius) / 2);
        double y = insets.top + ((height - radius) / 2);
        g2d.setStroke(new BasicStroke(thickness));
        Ellipse2D shape = new Ellipse2D.Double(x, y, radius, radius);
        g2d.setColor(getBackgroundColor());
        g2d.fill(shape);
        super.paintComponent(g2d);
        g2d.setColor(getColor());
        g2d.draw(shape);
    }

    private Color getColor() {
        return node == null ? Color.YELLOW : node.getState() ? Color.GREEN : Color.RED;
    }

    private Color getBackgroundColor() {
        return node == null || !node.isLocked() ? Color.BLACK : Color.GRAY;
    }
}
