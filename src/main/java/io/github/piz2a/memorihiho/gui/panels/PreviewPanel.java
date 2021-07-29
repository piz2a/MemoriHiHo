package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;
import org.json.simple.JSONArray;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PreviewPanel extends MHPanel {

    public PreviewPanel(MemoriHiHo frame) {
        super(frame);
    }

    static class InfoPanel extends JPanel {
        private InfoPanel(MemoriHiHo frame) {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            JLabel titleLabel = new JLabel((String) frame.getCurrentFileObject().get("title"));
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(titleLabel);

            JLabel authorLabel = new JLabel("by " + frame.getCurrentFileObject().get("author"));
            authorLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            add(authorLabel);

            JLabel descriptionLabel = new JLabel((String) frame.getCurrentFileObject().get("description"));
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            add(descriptionLabel);
        }
    }

    @Override
    JPanel getTopPanel() {
        return new InfoPanel(frame);
    }

    static class DisplayPanel extends JPanel {
        private DisplayPanel(MemoriHiHo frame) {
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
        return new DisplayPanel(frame);
    }

    // Bottom Panel including buttons
    static class BottomPanel extends JPanel {
        private BottomPanel(MemoriHiHo frame, PreviewPanel panel) {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

            panel.addBottomButton(this, "Edit", e -> MenuItemActions.FileActions.edit(frame));
            panel.addBottomButton(this, "Test", e -> {});
        }
    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(frame, this);
    }

}
