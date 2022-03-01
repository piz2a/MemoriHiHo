package io.github.piz2a.memorihiho.gui.dialogs;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutDialog extends MHDialog {

    public AboutDialog(MemoriHiHo frame) {
        super("AboutDialog", frame, null, "About MemoriHiHo");
    }

    class AboutPanel extends JPanel {
        AboutPanel() {
            final int width = 160, height = 280;
            setSize(width, height);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(20, 20, 20, 20));

            add(new JLabel("MemoriHiHo by piz2a"));

            add(new JLabel(String.format("Version: %s", frame.getDefaultVariables().getProperty("version"))));

            JButton githubButton = new JButton("Github");
            githubButton.addActionListener(event -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(frame.getDefaultVariables().getProperty("github")));
                    } catch (URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            add(githubButton);
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
