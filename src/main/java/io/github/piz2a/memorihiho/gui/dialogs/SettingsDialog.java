package io.github.piz2a.memorihiho.gui.dialogs;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.utils.InstantButton;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

public class SettingsDialog extends MHDialog {

    public SettingsDialog(MemoriHiHo frame) {
        super("OptionDialog", frame, null, "Options");
    }

    class SettingsPanel extends JPanel {
        HashSet<DefaultOptionPanel> optionPanelHashSet = new HashSet<>();
        SettingsPanel() {
            final int width = 400, height = 300;
            setSize(width, height);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(20, 10, 20, 10));
            registerOptions();
            add(new SaveButtonPanel());
        }

        void registerOptions() {  // 모든 옵션 여기에서 등록
            register(new LanguageOptionPanel());
            // 다른 옵션 생기면 여기에 추가
        }

        void register(DefaultOptionPanel optionPanel) {
            optionPanelHashSet.add(optionPanel);
            add(optionPanel);
        }

        void save() {
            for (DefaultOptionPanel optionPanel : optionPanelHashSet) {
                optionPanel.saveOptions();
            }
        }

        abstract class DefaultOptionPanel extends JPanel {
            JLabel optionNameLabel;
            void create(String labelTextKey, JPanel operationPanel, String descriptionText) {
                setLayout(new BorderLayout());
                optionNameLabel = new JLabel(frame.getLanguage().getProperty(String.format("settingsDialog.options.%s", labelTextKey)));
                optionNameLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 16));
                optionNameLabel.setPreferredSize(new Dimension(100, 40));
                add(optionNameLabel, BorderLayout.WEST);

                JPanel rightPanel = new JPanel(new BorderLayout());
                rightPanel.add(operationPanel, BorderLayout.NORTH);

                JLabel descriptionLabel = new JLabel(descriptionText);
                descriptionLabel.setFont(new Font(frame.getLanguage().getProperty("font"), Font.PLAIN, 12));
                rightPanel.add(descriptionLabel, BorderLayout.SOUTH);
                add(rightPanel, BorderLayout.EAST);
            }
            abstract void saveOptions();
        }

        class LanguageOptionPanel extends DefaultOptionPanel {
            JSONArray availableLanguages;
            JComboBox<String> comboBox;
            LanguageOptionPanel() {
                String jsonData = frame.getTextFileReader().getStringFromResources("lang/availablelanguages.json");
                JSONParser parser = new JSONParser();
                try {
                    availableLanguages = (JSONArray) parser.parse(jsonData);
                    Vector<String> availableLanguageNames = new Vector<>();
                    String currentLanguageName = frame.getSettings().getProperty("lang");
                    int currentLanguageIndex = 0;
                    for (int i = 0; i < availableLanguages.size(); i++) {
                        JSONObject languageObject = (JSONObject) availableLanguages.get(i);
                        availableLanguageNames.add((String) languageObject.get("as"));
                        if (languageObject.get("language").equals(currentLanguageName)) {  // 현재 언어 index 찾기
                            currentLanguageIndex = i;
                        }
                    }
                    comboBox = new JComboBox<>(availableLanguageNames);
                    comboBox.setSelectedIndex(currentLanguageIndex);  // comboBox에서 현재 언어 선택
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JPanel operationPanel = new JPanel();
                operationPanel.add(comboBox);

                create("language", operationPanel, frame.getLanguage().getProperty("settingsDialog.options.language.description"));
            }

            @Override
            void saveOptions() {
                System.out.println((String) ((JSONObject) availableLanguages.get(comboBox.getSelectedIndex())).get("language"));
                frame.getSettings().setProperty("lang", (String) ((JSONObject) availableLanguages.get(comboBox.getSelectedIndex())).get("language"));
                frame.saveSettings();
            }
        }

        class SaveButtonPanel extends JPanel {
            SaveButtonPanel() {
                setLayout(new FlowLayout(FlowLayout.RIGHT));

                JButton saveButton = InstantButton.getButton(
                        frame,
                        frame.getLanguage().getProperty("settingsDialog.save"),
                        null,
                        e -> {save();close();},
                        new Dimension(100, 40),
                        16
                );
                add(saveButton);
                JButton cancelButton = InstantButton.getButton(
                        frame,
                        frame.getLanguage().getProperty("settingsDialog.cancel"),
                        null,
                        e -> close(),
                        new Dimension(100, 40),
                        16
                );
                add(cancelButton);
            }
        }
    }

    @Override
    public void decorate() {
        add(new SettingsPanel());
        setLocation(
                frame.getScreenWidth() / 4,
                frame.getScreenHeight() / 4
        );
    }

}
