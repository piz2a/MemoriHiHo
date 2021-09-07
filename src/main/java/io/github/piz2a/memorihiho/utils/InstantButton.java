package io.github.piz2a.memorihiho.utils;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InstantButton {

    public static JButton getButton(MemoriHiHo frame, String text, ImageIcon icon, ActionListener actionListener, Dimension dimension, int fontSize) {
        JButton testButton = new JButton(text, icon);
        testButton.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, fontSize));
        testButton.setPreferredSize(dimension);
        testButton.addActionListener(actionListener);
        return testButton;
    }

}
