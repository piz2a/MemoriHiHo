package io.github.piz2a.memorihiho.listener;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;
import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.gui.panels.EditPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MHWindowListener extends WindowAdapter {

    private final MemoriHiHo frame;

    public MHWindowListener(MemoriHiHo frame) {
        this.frame = frame;
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        if (MenuItemActions.FileActions.confirmWhenClosing(frame)) {
            if (JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to exit MemorizationTest?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

}
