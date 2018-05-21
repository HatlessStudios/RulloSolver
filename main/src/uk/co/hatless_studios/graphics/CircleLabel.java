package uk.co.hatless_studios.graphics;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;


public class CircleLabel extends JLabel {

    private Color colour;

    public CircleLabel(String text, Color colour){
        super(text);
        setForeground(Color.WHITE);
        this.colour = colour;
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
        }
        Insets insets = getInsets();
        int width = (getWidth() - (insets.left + insets.right));
        int height = getHeight() - (insets.top + insets.bottom);
        int radius = Math.max(width, height);

        int x = insets.left + ((width - radius) / 2);
        int y = insets.top + ((height - radius) / 2);

        g.setColor(colour); // Colour not working, investigate

        g.drawOval(x, y, radius, radius);
        super.paintComponent(g);

        g.dispose();
    }
}
