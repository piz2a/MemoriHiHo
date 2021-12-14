package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;
import io.github.piz2a.memorihiho.gui.dialogs.ErrorDialog;
import io.github.piz2a.memorihiho.gui.dialogs.MHDialog;
import io.github.piz2a.memorihiho.gui.dialogs.PreviewTestDialog;
import org.json.simple.JSONArray;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public class PreviewPanel extends MHPanel {

    public PreviewPanel(MemoriHiHo frame) {
        super(frame);
    }

    class TopPanel extends JPanel {
        private TopPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            JLabel titleLabel = new JLabel((String) frame.getCurrentFileObject().get("title"));
            titleLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.BOLD, 24));
            add(titleLabel);

            JLabel authorLabel = new JLabel("by " + frame.getCurrentFileObject().get("author"));
            authorLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.ITALIC, 12));
            add(authorLabel);

            JLabel descriptionLabel = new JLabel((String) frame.getCurrentFileObject().get("description"));
            descriptionLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 16));
            add(descriptionLabel);
        }
    }

    @Override
    JPanel getTopPanel() {
        return new TopPanel();
    }

    class CenterPanel extends JPanel {
        private CenterPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JSONArray elementsArray = (JSONArray) frame.getCurrentFileObject().get("elements");
            int i = 1;
            for (Object element : elementsArray) {
                JSONArray elementArray = (JSONArray) element;
                String key = (String) elementArray.get(0);
                String data = (String) elementArray.get(1);

                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
                itemPanel.setBorder(new LineBorder(Color.BLACK));

                JLabel itemLabel = new JLabel(Integer.toString(i++));
                itemLabel.setPreferredSize(new Dimension(20, 20));
                itemPanel.add(itemLabel);

                JPanel elementItemPanel = new JPanel();
                elementItemPanel.setLayout(new GridLayout(1, 2));
                elementItemPanel.setPreferredSize(new Dimension(frame.width - 200, 20));

                JLabel keyLabel = new JLabel(key);
                JLabel dataLabel = new JLabel(data);
                elementItemPanel.add(keyLabel);
                elementItemPanel.add(dataLabel);

                itemPanel.add(elementItemPanel);

                add(itemPanel);
            }
        }
    }

    @Override
    JPanel getCenterPanel() {
        return new CenterPanel();
    }

    // Bottom Panel including buttons
    class BottomPanel extends JPanel {
        private BottomPanel(PreviewPanel panel) {
            JPanel leftBottomPanel = new JPanel();
            setLayout(new BorderLayout());

            leftBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel shuffleLabel = new JLabel(frame.getLanguage().getProperty(
                    (boolean) frame.getCurrentFileObject().get("shuffle") ? "previewPanel.label.shuffle" : "previewPanel.label.notShuffle"
            ));
            shuffleLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 12));
            leftBottomPanel.add(shuffleLabel);
            add(leftBottomPanel, BorderLayout.WEST);

            JPanel rightBottomPanel = new JPanel();
            rightBottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            rightBottomPanel.add(panel.getBottomButton(
                    frame.getLanguage().getProperty("previewPanel.button.edit"),
                    e -> MenuItemActions.FileActions.edit(frame)
            ));
            rightBottomPanel.add(panel.getBottomButton(
                    frame.getLanguage().getProperty("previewPanel.button.test"),
                    e -> new PreviewTestDialog("PreviewPanel.testDialog", frame, panel, "Choose Test Type").open()
            ));
            add(rightBottomPanel, BorderLayout.EAST);
        }
    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(this);
    }

}
