package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleLabel extends JLabel{

    private Color colour;

    public CircleLabel(String text, Color colour){
        super(text);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width = Math.max(size.width, size.height);
        size.height = size.width;
        return size;
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Insets insets = getInsets();
        int width = (getWidth() - (insets.left + insets.right));
        int height = getHeight() - (insets.top + insets.bottom);
        int radius = Math.max(width, height);

        int x = insets.left + ((width - radius) / 2);
        int y = insets.top + ((height - radius) / 2);

        g2d.drawOval(x, y, radius, radius);
        super.paintComponent(g2d);

        g2d.setColor(colour); // Colour not working, investigate

        super.paintComponent(g2d);
        g2d.dispose();
    }
}
