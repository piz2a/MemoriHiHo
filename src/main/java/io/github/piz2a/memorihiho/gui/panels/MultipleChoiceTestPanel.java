package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import org.json.simple.JSONArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MultipleChoiceTestPanel extends TestPanel {

    public MultipleChoiceTestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    String getTestType() {
        return frame.getLanguage().getProperty("menuBarItem.multiple_choice_test");
    }

    class QuestionAnswerPanel extends DefaultCenterPanel {

        int numberOfChoices = ((Long) fileObject.get("numberOfChoices")).intValue();
        QuestionNumberPanel questionNumberPanel = new QuestionNumberPanel();
        QuestionPanel questionPanel = new QuestionPanel();
        OptionPanel optionPanel = new OptionPanel();
        QuestionOptionPanel questionOptionPanel = new QuestionOptionPanel();
        AnswerPanel answerPanel = new AnswerPanel();
        ArrayList<QuestionAndOptions> questionsAndOptionsArray;
        QuestionAndOptions currentElement;

        QuestionAnswerPanel(boolean reversed) {
            // System.out.println("numberOfChoices: " + numberOfChoices);
            if (reversed) reverseElement(elements);
            questionsAndOptionsArray = makeOptions(elements, numberOfChoices);
            setLayout(new BorderLayout(20, 10));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            add(questionNumberPanel, BorderLayout.NORTH);
            add(questionOptionPanel, BorderLayout.CENTER);
            add(answerPanel, BorderLayout.SOUTH);
        }

        class QuestionOptionPanel extends JPanel {
            QuestionOptionPanel() {
                setLayout(new BorderLayout());
                add(questionPanel, BorderLayout.NORTH);
                add(optionPanel, BorderLayout.SOUTH);
            }
        }

        class QuestionPanel extends JPanel {
            JLabel questionLabel;
            QuestionPanel() {
                setLayout(new BorderLayout());
                questionLabel = new JLabel();
                questionLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 32));
                questionLabel.setForeground(new Color(0x3F48CC));
                questionLabel.setHorizontalAlignment(JLabel.CENTER);
                add(questionLabel, BorderLayout.NORTH);
            }
        }

        class OptionPanel extends JPanel {
            JRadioButton[] radios = new JRadioButton[numberOfChoices];
            ButtonGroup radioGroup = new ButtonGroup();

            OptionPanel() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                for (int i = 0; i < numberOfChoices; i++) {
                    radios[i] = new JRadioButton();
                    radioGroup.add(radios[i]);
                    add(radios[i]);
                }
                System.out.println(numberOfChoices);
                System.out.println(radios.length);
            }

            void setRadioNames(ArrayList<String> radioNames) {
                for (int i = 0; i < numberOfChoices; i++) {
                    radios[i].setText(radioNames.get(i));
                }
            }

            void setRadiosEnabled(boolean b) {
                for (JRadioButton radio : radios) {
                    radio.setEnabled(b);
                }
            }

        }

        @Override
        void raise() {
            currentElement = questionsAndOptionsArray.get(index);
            questionNumberPanel.questionNumberLabel.setText(String.format("Question %d / %d", index + 1, testLength));
            questionPanel.questionLabel.setText(currentElement.getQuestion());
            optionPanel.setRadioNames(currentElement.getOptions());
            optionPanel.radioGroup.clearSelection();
            optionPanel.setRadiosEnabled(true);
            answerPanel.answerLabel.setText("");
            answerPanel.ratioLabel.setText("");
            optionPanel.radios[0].requestFocus();
        }

        @Override
        void check() {
            optionPanel.setRadiosEnabled(false);
            answerPanel.answerLabel.setText(String.format("Answer: %s", currentElement.getOptions().get(currentElement.getAnswer())));
            answerPanel.ratioLabel.setText(String.format("%.1f%% (+%.2f points)", ratio() * 100, score()));
        }

        @Override
        double ratio() {
            int selectedIndex = -1;
            for (int i = 0; i < numberOfChoices; i++) {
                if (optionPanel.radios[i].isSelected()) {
                    selectedIndex = i;
                }
            }
            return selectedIndex == currentElement.getAnswer() ? 1 : 0;
        }

        @Override
        double score() {
            return ratio() * maximumScore / testLength;
        }

        ArrayList<QuestionAndOptions> makeOptions(JSONArray elements, int numberOfChoices) {
            ArrayList<QuestionAndOptions> optionArray = new ArrayList<>();
            Random random = new Random();
            int size = elements.size();
            for (int i = 0; i < size; i++) {
                //System.out.println("i: " + i);
                ArrayList<Integer> optionAnswerNumbers = new ArrayList<>();  // ???????????? ???????????? ????????? ??????
                ArrayList<String> optionAnswers = new ArrayList<>();
                String question = (String) ((JSONArray) elements.get(i)).get(0);
                optionAnswerNumbers.add(i);
                // ????????? (numberOfChoices - 1)??? ???????????????, ??? ????????? ?????? ?????? ??????????????? ????????? ???????????? ????????????.
                // ex) numberOfChoices = 4, size = 10
                for (int j = 1; j < numberOfChoices; j++) {  // j < 4, j = 1, 2, 3
                    int optionAnswerNumber = i;
                    if (j < size - i) {
                        // j??? 10-i ????????? ?????? i+1?????? size ????????? ??? ??? ????????? ??????
                        // ex) i = 7; optionAnswerNumbers = {7, 8, 9, random.nextInt(10)}, j < 10 - 7 = 3, j = 1, 2
                        while (optionAnswerNumbers.contains(optionAnswerNumber)) {
                            optionAnswerNumber = random.nextInt(size - i - 1) + i + 1;
                        }
                    } else {
                        // j??? 10-i ????????? ?????? i+1?????? size ????????? ???????????? numberOfChoices ?????? ?????? ?????? ??? ???????????? ???????????? ?????? ??? ?????? i ????????? ?????? ??????
                        // ex) j >= 3, j = 3
                        optionAnswerNumber = random.nextInt(i);
                        while (optionAnswerNumbers.contains(optionAnswerNumber)) {
                            optionAnswerNumber = random.nextInt(i);
                        }
                    }
                    optionAnswerNumbers.add(optionAnswerNumber);
                    //System.out.println(optionAnswerNumber);
                    //System.out.println((((JSONArray) elements.get(optionAnswerNumber)).get(1)));
                }
                Collections.shuffle(optionAnswerNumbers);
                int answer = optionAnswerNumbers.indexOf(i);
                for (Integer optionAnswerNumber : optionAnswerNumbers) {
                    optionAnswers.add((String) ((JSONArray) elements.get(optionAnswerNumber)).get(1));
                }
                System.out.println(optionAnswerNumbers);
                System.out.println(optionAnswers);
                QuestionAndOptions questionAndOptions = new QuestionAndOptions(question, optionAnswers, answer);
                optionArray.add(questionAndOptions);
            }
            return optionArray;
        }

        class QuestionAndOptions {  // ?????? ????????? ?????????, ?????? ???????????? ?????? ??????
            private final String question;
            private final ArrayList<String> options;
            private final int answer;

            QuestionAndOptions(String question, ArrayList<String> options, int answer) {
                this.question = question;
                this.options = options;
                this.answer = answer;
            }
            String getQuestion() {
                return question;
            }
            ArrayList<String> getOptions() {
                return options;
            }
            int getAnswer() {
                return answer;
            }
        }

    }

    @Override
    JPanel getCenterPanel() {
        return new QuestionAnswerPanel(false);
    }

}
