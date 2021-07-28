package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class MTPanel extends JPanel {

    MemoriHiHo frame;
    JPanel topPanel, centerPanel, bottomPanel;

    // Constructor
    public MTPanel(MemoriHiHo frame) {
        this.frame = frame;
    }

    public void initialize() {
        setLayout(new BorderLayout());

        topPanel = getTopPanel();
        centerPanel = getCenterPanel();
        bottomPanel = getBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(new DisplayScrollPane(frame, this), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    abstract JPanel getTopPanel();

    abstract JPanel getCenterPanel();

    abstract JPanel getBottomPanel();

    private static class DisplayScrollPane extends JScrollPane {
        private DisplayScrollPane(MemoriHiHo frame, MTPanel panel) {
            super(
                    panel.centerPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            setBounds(0, 0, frame.width, 300);
            getHorizontalScrollBar().setUnitIncrement(16);
            getVerticalScrollBar().setUnitIncrement(16);
        }
    }

    void addBottomButton(JPanel bottomPanel, String text, ActionListener actionListener) {
        JButton testButton = new JButton(text);
        testButton.setFont(new Font("Arial", Font.PLAIN, 20));
        testButton.setPreferredSize(new Dimension(100, 60));
        testButton.addActionListener(actionListener);
        bottomPanel.add(testButton);
    }

}
