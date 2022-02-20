package io.github.piz2a.memorihiho.gui.dialogs;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;
import io.github.piz2a.memorihiho.gui.panels.MHPanel;
import io.github.piz2a.memorihiho.utils.InstantButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public class PreviewTestDialog extends MHDialog {

    public PreviewTestDialog(String dialogName, MemoriHiHo frame, MHPanel panel, String title) {
        super(dialogName, frame, panel, title);
    }

    @Override
    public void decorate() {
        final int width = 240, height = 160, fontSize = 16;

        setLayout(new BorderLayout()/*new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)*/);
        setSize(width, height);

        URL subjectiveIconResource = frame.classloader.getResource("icons/menubar/subjective_test.png");
        URL multipleChoiceIconResource = frame.classloader.getResource("icons/menubar/multiple_choice_test.png");
        URL reversedSubjectiveIconResource = frame.classloader.getResource("icons/menubar/reversed_subjective_test.png");
        URL reversedMultipleChoiceIconResource = frame.classloader.getResource("icons/menubar/reversed_multiple_choice_test.png");
        try {
            assert subjectiveIconResource != null && multipleChoiceIconResource != null
                    && reversedSubjectiveIconResource != null && reversedMultipleChoiceIconResource != null;
        } catch (AssertionError e) {
            e.printStackTrace();
        }

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        // Subjective Test Button
        upperPanel.add(InstantButton.getButton(
                frame,
                frame.getLanguage().getProperty("menuBarItem.subjective_test"),
                new ImageIcon(subjectiveIconResource),
                e -> {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));  // Close
                    MenuItemActions.TestActions.subjectiveTest(frame);
                },
                new Dimension(width, height / 4),
                fontSize
        ), BorderLayout.NORTH);
        // Multiple Choice Test Button
        upperPanel.add(InstantButton.getButton(
                frame,
                frame.getLanguage().getProperty("menuBarItem.multiple_choice_test"),
                new ImageIcon(multipleChoiceIconResource),
                e -> {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));  // Close
                    MenuItemActions.TestActions.multipleChoiceTest(frame);
                },
                new Dimension(width, height / 4),
                fontSize
        ), BorderLayout.SOUTH);
        add(upperPanel, BorderLayout.NORTH);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        // Reversed Subjective Test Button
        lowerPanel.add(InstantButton.getButton(
                frame,
                frame.getLanguage().getProperty("menuBarItem.reversed_subjective_test"),
                new ImageIcon(reversedSubjectiveIconResource),
                e -> {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));  // Close
                    MenuItemActions.TestActions.reversedSubjectiveTest(frame);
                },
                new Dimension(width, height / 4),
                fontSize
        ), BorderLayout.NORTH);
        // Reversed Multiple Choice Test Button
        lowerPanel.add(InstantButton.getButton(
                frame,
                frame.getLanguage().getProperty("menuBarItem.reversed_multiple_choice_test"),
                new ImageIcon(reversedMultipleChoiceIconResource),
                e -> {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));  // Close
                    MenuItemActions.TestActions.reversedMultipleChoiceTest(frame);
                },
                new Dimension(width, height / 4),
                fontSize
        ), BorderLayout.SOUTH);
        add(lowerPanel, BorderLayout.SOUTH);

        setLocation(
                (frame.getScreenWidth() - width) / 2,
                (frame.getScreenHeight() - height) / 2
        );
    }

}
