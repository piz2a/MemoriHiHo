package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.PanelManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TestCompletePanel extends MHPanel {

    String testName;
    int numberOfQuestions;
    int questionsSolved;
    double score;
    double totalTime;

    public TestCompletePanel(MemoriHiHo frame) {
        super(frame);
    }

    public void receiveTestInfo(String testName, int numberOfQuestions, int questionsSolved, double score, double totalTime) {
        this.testName = testName;
        this.numberOfQuestions = numberOfQuestions;
        this.questionsSolved = questionsSolved;
        this.score = score;
        this.totalTime = totalTime;
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
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(40, 20, 20, 40));

            JLabel scoreLabel = new JLabel(
                    String.format("%s: %.2f/100", frame.getLanguage().getProperty("testCompletePanel.scoreLabel"), score)
            );
            scoreLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            add(scoreLabel);

            JLabel questionsSolvedLabel = new JLabel(
                    String.format("%s: %d/%d", frame.getLanguage().getProperty("testCompletePanel.questionsSolvedLabel"), questionsSolved, numberOfQuestions)
            );
            questionsSolvedLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            questionsSolvedLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            add(questionsSolvedLabel);

            JLabel totalTimeLabel = new JLabel(
                    String.format("%s: %.1fs", frame.getLanguage().getProperty("testCompletePanel.totalTimeLabel"), totalTime)
            );
            totalTimeLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            totalTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            add(totalTimeLabel);

            JLabel averageTimeLabel = new JLabel(
                    String.format("%s: %.1fs", frame.getLanguage().getProperty("testCompletePanel.averageTimeLabel"), totalTime / numberOfQuestions)
            );
            averageTimeLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            averageTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            add(averageTimeLabel);

            JLabel impressionLabel = new JLabel(
                    frame.getLanguage().getProperty("testCompletePanel.impressionLabel." + (int)(score/20))
            );
            impressionLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.BOLD, 36));
            impressionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            impressionLabel.setForeground(new Color(0x09A527));
            impressionLabel.setBorder(new EmptyBorder(40, 0, 0, 0));
            add(impressionLabel);
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
