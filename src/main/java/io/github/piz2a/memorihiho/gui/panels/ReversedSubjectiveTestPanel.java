package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;

import javax.swing.*;

public class ReversedSubjectiveTestPanel extends SubjectiveTestPanel {

    public ReversedSubjectiveTestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    String getTestType() {
        return frame.getLanguage().getProperty("menuBarItem.reversed_subjective_test");
    }

    @Override
    JPanel getCenterPanel() {
        return new QuestionAnswerPanel(true);
    }

}
