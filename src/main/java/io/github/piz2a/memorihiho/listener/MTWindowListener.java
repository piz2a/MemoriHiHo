package io.github.piz2a.memorihiho.listener;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MTWindowListener extends WindowAdapter {

    private final MemoriHiHo frame;

    public MTWindowListener(MemoriHiHo frame) {
        this.frame = frame;
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        // If there are unsaved changes, make a popup and ask
        if (frame.haveChanges()) {
            if (JOptionPane.showConfirmDialog(frame,
                    "Save Changes?", "Save Before Closing   ",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                MenuItemActions.FileActions.save(frame);
            }
        }

        if (JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to exit MemorizationTest?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
