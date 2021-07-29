package io.github.piz2a.memorihiho.utils;

import javax.swing.*;

public class ErrorDialog {

    public static void show(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error: " + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

}
