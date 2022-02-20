package io.github.piz2a.memorihiho.gui.dialogs;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AboutDialog extends MHDialog {

    public AboutDialog(MemoriHiHo frame) {
        super("AboutDialog", frame, null, "About MemoriHiHo");
    }

    class AboutPanel extends JPanel {
        AboutPanel() {
            final int width = 160, height = 280;
            setSize(width, height);
            setLayout(new BorderLayout(20, 30));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            add(new JLabel("MemoriHiHo by piz2a"));
        }
    }

    @Override
    public void decorate() {
        add(new AboutPanel());
        setLocation(
                frame.getScreenWidth() / 4,
                frame.getScreenHeight() / 4
        );
    }

}
