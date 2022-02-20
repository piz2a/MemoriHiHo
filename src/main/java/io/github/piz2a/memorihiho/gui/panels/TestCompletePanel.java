package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.PanelManager;

import javax.swing.*;
import java.awt.*;

public class TestCompletePanel extends MHPanel {

    String testName;
    double score;

    public TestCompletePanel(MemoriHiHo frame) {
        super(frame);
    }

    public void receiveTestInfo(String testName, double score) {
        this.testName = testName;
        this.score = score;
    }

    class TopPanel extends JPanel {
        TopPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            JLabel testTypeLabel = new JLabel(frame.getLanguage().getProperty("testCompletePanel.complete"));
            testTypeLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.BOLD, 32));
            add(testTypeLabel);

            JLabel titleLabel = new JLabel(testName);
            titleLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            add(titleLabel);
        }
    }

    @Override
    JPanel getTopPanel() {
        return new TopPanel();
    }

    class CenterPanel extends JPanel {
        CenterPanel() {
            JLabel scoreLabel = new JLabel(
                    String.format("%s: %.2f/100", frame.getLanguage().getProperty("testCompletePanel.scoreLabel"), score)
            );
            scoreLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            add(scoreLabel);
        }
    }

    @Override
    JPanel getCenterPanel() {
        return new CenterPanel();
    }

    class BottomPanel extends JPanel {
        BottomPanel(TestCompletePanel panel) {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            // "OK" Button
            JButton OKButton = panel.getBottomButton(frame.getLanguage().getProperty("testCompletePanel.OKButton"), null);
            OKButton.addActionListener(e -> frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL));
            add(OKButton);
            OKButton.requestFocus();
        }
    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(this);
    }
}
