package io.github.piz2a.memorihiho.gui.dialogs;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.panels.MHPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class MHDialog extends JDialog {

    String dialogName;
    MemoriHiHo frame;
    MHPanel panel;

    public MHDialog(String dialogName, MemoriHiHo frame, MHPanel panel, String title) {
        super(frame, title);
        this.dialogName = dialogName;
        this.frame = frame;
        this.panel = panel;
    }

    private void initialize() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                frame.isDialogOpen.put(dialogName, null);
            }
        });

        decorate();

        setVisible(true);
        pack();
    }

    public abstract void decorate();

    public void open() {
        if (!frame.isDialogOpen.containsKey(dialogName))
            frame.isDialogOpen.put(dialogName, null);
        if (frame.isDialogOpen.get(dialogName) == null) {
            frame.isDialogOpen.put(dialogName, this);
            initialize();
        } else {
            frame.isDialogOpen.get(dialogName).toFront();
            frame.isDialogOpen.get(dialogName).requestFocus();
        }
    }

}
