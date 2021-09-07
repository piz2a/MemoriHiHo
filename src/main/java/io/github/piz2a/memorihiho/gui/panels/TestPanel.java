package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;
import java.awt.*;

public abstract class TestPanel extends MHPanel {

    public TestPanel(MemoriHiHo frame) {
        super(frame);
    }

    abstract String getTestType();

    class InfoPanel extends JPanel {
        InfoPanel(MemoriHiHo frame) {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            JLabel testTypeLabel = new JLabel(getTestType());
            testTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            add(testTypeLabel);

            JLabel titleLabel = new JLabel((String) frame.getCurrentFileObject().get("title"));
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            add(titleLabel);

            JLabel authorLabel = new JLabel("by " + frame.getCurrentFileObject().get("author"));
            authorLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            add(authorLabel);
        }
    }

    @Override
    JPanel getTopPanel() {
        return new InfoPanel(frame);
    }

    @Override
    abstract JPanel getCenterPanel();

    class BottomPanel extends JPanel {
        BottomPanel(MemoriHiHo frame) {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        }
    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(frame);
    }

}
