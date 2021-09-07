package io.github.piz2a.memorihiho.gui.dialogs;

import javax.swing.*;

public class ErrorDialog {

    public static void show(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error: " + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

}
