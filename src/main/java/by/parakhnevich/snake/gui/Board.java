package by.parakhnevich.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Board extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDonut(g);
    }

    private void drawDonut(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        RenderingHints renderingHints =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(renderingHints);
        Dimension size = getSize();

        Ellipse2D ellipse2D = new Ellipse2D.Double(0, 0, 200, 200);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setColor(Color.GRAY);

        for (double deg = 0; deg < 360; deg += 5) {
            AffineTransform affineTransform =
                    AffineTransform.getTranslateInstance(size.getWidth() / 2,
                            size.getHeight() / 2);
            affineTransform.rotate(Math.toRadians(deg));
            graphics2D.draw(affineTransform.createTransformedShape(ellipse2D));
        }
    }
}
