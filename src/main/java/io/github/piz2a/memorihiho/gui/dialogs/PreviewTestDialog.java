package io.github.piz2a.memorihiho.gui.dialogs;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;
import io.github.piz2a.memorihiho.gui.panels.MHPanel;
import io.github.piz2a.memorihiho.utils.InstantButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public class PreviewTestDialog extends MHDialog {

    public PreviewTestDialog(String dialogName, MemoriHiHo frame, MHPanel panel, String title) {
        super(dialogName, frame, panel, title);
    }

    @Override
    public void decorate() {
        final int width = 200, height = 80, fontSize = 16;

        setLayout(new BorderLayout());
        setSize(width, height);

        URL normalIconResource = frame.classloader.getResource("icons/menubar/normal_test.png");
        URL quickIconResource = frame.classloader.getResource("icons/menubar/quick_test.png");
        try {
            assert normalIconResource != null && quickIconResource != null;
        } catch (AssertionError e) {
            e.printStackTrace();
        }

        // Normal Test Button
        add(InstantButton.getButton(
                frame,
                frame.getLanguage().getProperty("menuBarItem.normal_test"),
                new ImageIcon(normalIconResource),
                e -> {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));  // Close
                    MenuItemActions.TestActions.normalTest(frame);
                },
                new Dimension(width, height / 2),
                fontSize
        ), BorderLayout.NORTH);
        // Quick Test Button
        add(InstantButton.getButton(
                frame,
                frame.getLanguage().getProperty("menuBarItem.quick_test"),
                new ImageIcon(quickIconResource),
                e -> {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));  // Close
                    MenuItemActions.TestActions.quickTest(frame);
                },
                new Dimension(width, height / 2),
                fontSize
        ), BorderLayout.SOUTH);
        setLocation(
                (frame.getScreenWidth() - width) / 2,
                (frame.getScreenHeight() - height / 2) / 2
        );
    }

}
