package io.github.piz2a.memorihiho.utils;

import javax.swing.*;
import java.awt.*;

public abstract class ShapedButton extends JButton {
    private final Shape shape = createShape();
    private Color color;

    public ShapedButton(Color color) {
        super();
        setColor(color);
        setBorderPainted(false);
    }

    @Override
    public void paintBorder(Graphics g) {
        ((Graphics2D)g).draw(shape);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        ((Graphics2D)g).fill(shape);
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public abstract Dimension getPreferredSize();

    protected abstract Shape createShape();
}
