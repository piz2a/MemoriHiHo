package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.utils.StringMatch;
import org.json.simple.JSONArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NormalTestPanel extends TestPanel {

    public NormalTestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    String getTestType() {
        return "Normal Test";
    }

    class CenterPanel extends DefaultCenterPanel {
        QuestionNumberPanel questionNumberPanel = new QuestionNumberPanel();
        QuestionPanel questionPanel = new QuestionPanel();
        AnswerPanel answerPanel = new AnswerPanel();
        JSONArray currentElement;

        CenterPanel(MemoriHiHo frame) {
            setLayout(new BorderLayout(20, 30));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            add(questionNumberPanel, BorderLayout.NORTH);
            add(questionPanel, BorderLayout.CENTER);
            add(answerPanel, BorderLayout.SOUTH);
        }

        class QuestionNumberPanel extends JPanel {  // ex. "Question 23 / 41"
            JLabel questionNumberLabel;
            QuestionNumberPanel() {
                setLayout(new FlowLayout(FlowLayout.LEFT));
                questionNumberLabel = new JLabel("Question");
                questionNumberLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 40));
                add(questionNumberLabel);
            }
        }

        class QuestionPanel extends JPanel {  // Includes question and text field
            JLabel questionLabel;
            JTextField answerTextField;
            QuestionPanel() {
                setLayout(new BorderLayout());
                questionLabel = new JLabel();
                questionLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 32));
                questionLabel.setForeground(new Color(0x3F48CC));
                questionLabel.setHorizontalAlignment(JLabel.CENTER);
                add(questionLabel, BorderLayout.NORTH);
                answerTextField = new JTextField("", 24);
                answerTextField.addActionListener(e -> ((BottomPanel) bottomPanel).rightBottomPanel.nextButtonAction());
                answerTextField.setPreferredSize(new Dimension(100, 32));
                add(answerTextField, BorderLayout.SOUTH);
            }
        }

        class AnswerPanel extends JPanel {  // Includes answer and ratio
            JLabel answerLabel, ratioLabel;
            AnswerPanel() {
                setLayout(new BorderLayout(0, 5));
                setPreferredSize(new Dimension(200, 96));
                answerLabel = new JLabel();
                answerLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
                answerLabel.setForeground(new Color(0xC00000));
                answerLabel.setHorizontalAlignment(JLabel.CENTER);
                answerLabel.setPreferredSize(new Dimension(100, 60));
                add(answerLabel, BorderLayout.NORTH);
                ratioLabel = new JLabel();
                ratioLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
                ratioLabel.setForeground(new Color(0x00C000));
                ratioLabel.setHorizontalAlignment(JLabel.CENTER);
                answerLabel.setPreferredSize(new Dimension(100, 60));
                add(ratioLabel, BorderLayout.SOUTH);
            }
        }

        @Override
        public void raise() {
            currentElement = (JSONArray) elements.get(index);
            questionNumberPanel.questionNumberLabel.setText(String.format("Question %d / %d", index + 1, testLength));
            questionPanel.questionLabel.setText((String) currentElement.get(0));
            questionPanel.answerTextField.setText("");
            questionPanel.answerTextField.setEnabled(true);
            answerPanel.answerLabel.setText("");
            answerPanel.ratioLabel.setText("");
            questionPanel.answerTextField.requestFocus();
        }

        @Override
        public void check() {
            questionPanel.answerTextField.setEnabled(false);
            answerPanel.answerLabel.setText(String.format("Answer: %s", currentElement.get(1)));
            answerPanel.ratioLabel.setText(String.format("%.1f%% (+%.2f points)", ratio() * 100, score()));
        }

        double ratio() {
            return StringMatch.ratio(questionPanel.answerTextField.getText(), (String) currentElement.get(1));
        }

        @Override
        double score() {
            return ratio() * maximumScore / testLength;
        }
    }

    @Override
    JPanel getCenterPanel() {
        return new CenterPanel(frame);
    }

}
