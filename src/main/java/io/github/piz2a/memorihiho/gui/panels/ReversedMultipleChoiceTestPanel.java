package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;

public class ReversedMultipleChoiceTestPanel extends MultipleChoiceTestPanel {

    public ReversedMultipleChoiceTestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    String getTestType() {
        return frame.getLanguage().getProperty("menuBarItem.reversed_multiple_choice_test");
    }

    @Override
    JPanel getCenterPanel() {
        return new QuestionAnswerPanel(true);
    }

}
