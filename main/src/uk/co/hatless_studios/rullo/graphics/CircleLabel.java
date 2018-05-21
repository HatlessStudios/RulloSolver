package uk.co.hatless_studios.rullo.graphics;

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

    private Color colour;

    public CircleLabel(String text, Color colour, Dimension size){
        super(text);
        setForeground(Color.WHITE);
        this.colour = colour;
        setFont(new Font("Serif", Font.PLAIN, size.height));
        super.setPreferredSize(size);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width = Math.max(size.width, size.height);
        size.height = size.width;
        return size;
    }

    @Override
    protected void paintComponent(Graphics g){
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            draw2D(g2d);
        } else {
            draw(g);
        }

        g.dispose();
    }

    protected void draw(Graphics g) {
        Insets insets = getInsets();
        int width = (getWidth() - (insets.left + insets.right));
        int height = getHeight() - (insets.top + insets.bottom);
        int radius = Math.max(width, height);
        int x = insets.left + ((width - radius) / 2);
        int y = insets.top + ((height - radius) / 2);

        g.setColor(colour);
        super.paintComponent(g);
        g.drawOval(x, y, radius, radius);
    }

    protected void draw2D(Graphics2D g2d) {
        Insets insets = getInsets();
        int width = (getWidth() - (insets.left + insets.right));
        int height = getHeight() - (insets.top + insets.bottom);
        int radius = Math.max(width, height);
        int x = insets.left + ((width - radius) / 2);
        int y = insets.top + ((height - radius) / 2);
        g2d.setColor(colour);
        super.paintComponent(g2d);
        g2d.setStroke(new BasicStroke(20F));
        g2d.draw(new Ellipse2D.Double(x, y, radius, radius));
    }
}
