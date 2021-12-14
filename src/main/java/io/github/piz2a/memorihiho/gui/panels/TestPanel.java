package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.utils.JsonArrayShuffler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class TestPanel extends MHPanel {

    // TestPanel은 크게 raise(문제를 냄)와 check(채점)으로 동작한다.

    public int maximumScore = 100;

    JSONObject fileObject;
    JSONArray elements;
    int testLength;
    int index;
    double score;  // out of 'maximumScore'
    private boolean testing;

    public TestPanel(MemoriHiHo frame) {
        super(frame);
    }

    @Override
    public void initialize() {
        index = -1;
        score = 0;
        fileObject = frame.getCurrentFileObject();
        elements = (JSONArray) fileObject.get("elements");
        testLength = elements.size();
        if ((boolean) fileObject.get("shuffle")) {  // Shuffle elements
            elements = JsonArrayShuffler.shuffle(elements);
        }
        super.initialize();
        raise();
    }

    abstract String getTestType();

    class TopPanel extends JPanel {
        TopPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            JLabel testTypeLabel = new JLabel(getTestType());
            testTypeLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.BOLD, 32));
            add(testTypeLabel);

            JLabel titleLabel = new JLabel((String) fileObject.get("title"));
            titleLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 24));
            add(titleLabel);

            JLabel authorLabel = new JLabel("by " + fileObject.get("author"));
            authorLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.ITALIC, 16));
            add(authorLabel);
        }
    }

    @Override
    JPanel getTopPanel() {
        return new TopPanel();
    }

    abstract static class DefaultCenterPanel extends JPanel {
        abstract void raise(); // 문제 준비
        abstract void check(); // 문제 입력 차단 & 점수 보여주기
        abstract double ratio(); // out of 1
        abstract double score(); // score which needs to be added
    }

    @Override
    abstract JPanel getCenterPanel();

    class BottomPanel extends JPanel {

        LeftBottomPanel leftBottomPanel;
        RightBottomPanel rightBottomPanel;

        BottomPanel(TestPanel panel) {
            setLayout(new BorderLayout());
            leftBottomPanel = new LeftBottomPanel();
            rightBottomPanel = new RightBottomPanel(panel);
            add(leftBottomPanel, BorderLayout.WEST);
            add(rightBottomPanel, BorderLayout.EAST);
        }

        public void raise() {  // 타이머 시작, 체크 아이콘 숨기기
            leftBottomPanel.timerLabel.startTimer();
            rightBottomPanel.checkLabel.removeCheckIcon();
        }

        public void check() {  // 점수 갱신, 체크 아이콘 표시
            rightBottomPanel.checkLabel.setCheckIcon(((DefaultCenterPanel) centerPanel).ratio() >= 1);
            score += ((DefaultCenterPanel) centerPanel).score();
            rightBottomPanel.scoreLabel.refresh();
            rightBottomPanel.nextButton.requestFocus();
        }

        class LeftBottomPanel extends JPanel {
            TimerLabel timerLabel = new TimerLabel();
            LeftBottomPanel() {
                setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
                add(timerLabel);
            }

            class TimerLabel extends JLabel {
                TimerLabel() {
                    setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 40));
                }
                void startTimer() {
                    new Thread(() -> {
                        double timeElapsed = 0;
                        while (isTesting()) {
                            timeElapsed += 0.1;
                            setText(String.format("%.1f", timeElapsed));
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }

        class RightBottomPanel extends JPanel {
            TestPanel panel;
            ScoreLabel scoreLabel;
            CheckLabel checkLabel;
            JButton nextButton, cancelButton;
            RightBottomPanel(TestPanel panel) {
                this.panel = panel;

                setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
                setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

                // "Next" Button
                nextButton = panel.getBottomButton(frame.getLanguage().getProperty("testPanel.nextButton.onRaise"), null);
                nextButton.addActionListener(e -> nextButtonAction());
                add(nextButton);

                // "Cancel" Button
                cancelButton = panel.getBottomButton(frame.getLanguage().getProperty("testPanel.cancelButton"), null);
                cancelButton.addActionListener(e -> {
                    if (JOptionPane.showConfirmDialog(frame,
                            frame.getLanguage().getProperty("message.confirmCancelTest"),
                            frame.getLanguage().getProperty("message.confirmCancelTest.title"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL);
                    }
                });
                add(cancelButton);

                // Check Icon Label
                checkLabel = new CheckLabel();
                add(checkLabel);

                // Score Label
                scoreLabel = new ScoreLabel();
                add(scoreLabel);
            }

            public void nextButtonAction() {
                if (testing) {
                    panel.check();
                    nextButton.setText(frame.getLanguage().getProperty("testPanel.nextButton.onCheck"));
                } else {
                    panel.raise();
                    nextButton.setText(frame.getLanguage().getProperty("testPanel.nextButton.onRaise"));
                }
            }

            class ScoreLabel extends JLabel {
                ScoreLabel() {
                    super();
                    setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 30));
                    refresh();
                }
                public void refresh() {
                    setText(String.format("%s: %.1f", frame.getLanguage().getProperty("testPanel.scoreLabel"), score));
                }
            }

            class CheckLabel extends JLabel {
                CheckLabel() {
                    super();
                    setPreferredSize(new Dimension(60, 60));
                }
                void setCheckIcon(boolean correct) {
                    URL checkIconResource = frame.classloader.getResource(correct ? "icons/correct.png" : "icons/incorrect.png");
                    assert checkIconResource != null;
                    checkLabel.setIcon(new ImageIcon(checkIconResource));
                }
                void removeCheckIcon() {
                    checkLabel.setIcon(null);
                }
            }
        }

    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(this);
    }

    boolean isTesting() {
        return testing;
    }

    public void raise() {
        index++;
        if (index < testLength) {
            testing = true;
            ((DefaultCenterPanel) centerPanel).raise();
            ((BottomPanel) bottomPanel).raise();
        } else {
            endTest();
        }
    }

    public void check() {
        testing = false;
        ((DefaultCenterPanel) centerPanel).check();
        ((BottomPanel) bottomPanel).check();
    }

    private void endTest() {
        fileObject = null;
        elements = null;
        index = -1;
        ((TestCompletePanel) frame.getPanelManager().getPanelByName(PanelManager.TEST_COMPLETE_PANEL))
                .receiveTestInfo(getTestType(), score);
        frame.getPanelManager().setPanel(PanelManager.TEST_COMPLETE_PANEL);
    }

}
