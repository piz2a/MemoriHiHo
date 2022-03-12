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
        this.setResizable(false);
    }

    private void initialize() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dialogsOpened.put(dialogName, null);
            }
        });

        decorate();

        setVisible(true);
        pack();
    }

    public abstract void decorate();

    public void open() {
        // 중복 open 방지
        if (!frame.dialogsOpened.containsKey(dialogName))
            frame.dialogsOpened.put(dialogName, null);
        if (frame.dialogsOpened.get(dialogName) == null) {
            // 새로 Dialog 열기
            frame.dialogsOpened.put(dialogName, this);
            initialize();
        } else {
            // 이미 열려있는 Dialog에 focus
            frame.dialogsOpened.get(dialogName).toFront();
            frame.dialogsOpened.get(dialogName).requestFocus();
        }
    }

    public void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
