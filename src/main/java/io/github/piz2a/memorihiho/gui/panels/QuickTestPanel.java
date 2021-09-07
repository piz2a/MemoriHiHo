package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;

public class QuickTestPanel extends TestPanel {

    public QuickTestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    String getTestType() {
        return "Quick Test";
    }

    @Override
    JPanel getCenterPanel() {
        return null;
    }

}
