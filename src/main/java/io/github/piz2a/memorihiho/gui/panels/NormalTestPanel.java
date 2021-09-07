package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;

public class NormalTestPanel extends TestPanel {

    public NormalTestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    String getTestType() {
        return "Normal Test";
    }

    @Override
    JPanel getCenterPanel() {
        return null;
    }

}
