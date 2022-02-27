package io.github.piz2a.memorihiho;

import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.gui.dialogs.AboutDialog;
import io.github.piz2a.memorihiho.gui.dialogs.SettingsDialog;
import io.github.piz2a.memorihiho.gui.panels.EditPanel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Desktop;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuItemActions {

    public static class FileActions {

        public static void newFile(MemoriHiHo frame) {
            System.out.println("NewFile");
            if (!confirmWhenClosing(frame)) {
                System.out.println("NewFile cancelled");
                return;
            }
            String jsonData = frame.getTextFileReader().getStringFromResources(
                    String.format("untitled_%s.json", frame.getSettings().getProperty("lang"))
            );
            openFile(frame, jsonData, null, true);
        }

        public static void open(MemoriHiHo frame) {
            System.out.println("Open");

            final JFileChooser fc = new JFileChooser();
            String extension = frame.getDefaultVariables().getProperty("extension");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    String.format("%s (.%s)", frame.getLanguage().getProperty("extension.description"), extension), extension
            );
            fc.setFileFilter(filter);

            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                java.io.File file = fc.getSelectedFile();
                System.out.println("Opening " + file.getName());
                String jsonData = frame.getTextFileReader().getString(file.toURI());

                if (!confirmWhenClosing(frame)) {
                    System.out.println("Opening cancelled");
                    return;
                }

                openFile(frame, jsonData, file,false);
            } else {
                System.out.println("Opening cancelled");
            }
        }

        public static void openFile(MemoriHiHo frame, String jsonData, java.io.File file, boolean isNewFile) {
            JSONParser parser = new JSONParser();
            try {
                if (jsonData == null) throw new NullPointerException("jsonData is null");
                frame.setCurrentFileObject((JSONObject) parser.parse(jsonData));
                frame.setIsNewFile(isNewFile);
                frame.setHaveChanges(false);
                // System.out.println(frame.getCurrentFileObject()); // Debug

                frame.setFile(file);
                frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL);
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        frame,
                        frame.getLanguage().getProperty("message.cannotOpenFile"),
                        frame.getLanguage().getProperty("message.cannotOpenFile.title"),
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        public static void recentFiles() {
            System.out.println("RecentFiles");
        }

        public static void edit(MemoriHiHo frame) {
            System.out.println("Edit");
            frame.getPanelManager().setPanel(PanelManager.EDIT_PANEL);
        }

        public static void settings(MemoriHiHo frame) {
            System.out.println("Settings");
            new SettingsDialog(frame).open();
        }

        public static boolean save(MemoriHiHo frame) {
            System.out.println("Save");
            if (frame.isNewFile())
                return saveAs(frame);
            return saveFile(frame, frame.getFile());
        }

        public static boolean saveAs(MemoriHiHo frame) {
            System.out.println("SaveAs");
            final JFileChooser fc = new JFileChooser();
            String extension = frame.getSettings().getProperty("extension");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    String.format("%s (.%s)", frame.getLanguage().getProperty("extension.description"), extension), extension
            );
            fc.setFileFilter(filter);

            int returnVal = fc.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filePath = fc.getSelectedFile().getPath();
                // Checking extension
                String dotExtension = "." + extension;
                if (!filePath.endsWith(dotExtension))
                    filePath = filePath + dotExtension;

                File savingFile = new File(filePath);
                return saveFile(frame, savingFile);
            }
            System.out.println("Saving cancelled");
            return false;
        }

        private static boolean saveFile(MemoriHiHo frame, File file) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                System.out.println("Saving " + file.getName());
                fileWriter.write(frame.getCurrentFileObject().toJSONString());
                fileWriter.flush();

                frame.setHaveChanges(false);
                frame.setIsNewFile(false);
                frame.setFile(file);
                frame.updateTitle();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        public static void exit(MemoriHiHo frame) {
            System.out.println("Exit");
            // Try to close JFrame
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

        public static boolean confirmWhenClosing(MemoriHiHo frame) {
            // Only when a file is open
            if (frame.getCurrentFileObject() == null)
                return true;

            // If the user is editing, make a popup and ask
            if (frame.getPanelManager().getCurrentPanelName().equals(PanelManager.EDIT_PANEL)) {
                switch (JOptionPane.showConfirmDialog(frame,
                        frame.getLanguage().getProperty("message.confirmEdit"),
                        frame.getLanguage().getProperty("message.confirmEdit.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE)) {
                    case JOptionPane.YES_OPTION:
                        ((EditPanel) frame.getPanelManager().getCurrentPanel()).applyChanges();
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                    case JOptionPane.CLOSED_OPTION:
                        return false;
                }
            }
            // If there are unsaved changes, make a popup and ask
            if (frame.haveChanges()) {
                switch (JOptionPane.showConfirmDialog(frame,
                        frame.getLanguage().getProperty("message.confirmSave"),
                        frame.getLanguage().getProperty("message.confirmSave.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE)) {
                    case JOptionPane.YES_OPTION:
                        if (!MenuItemActions.FileActions.save(frame)) return false;
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                    case JOptionPane.CLOSED_OPTION:
                        return false;
                }
            }
            return true;
        }

    }

    public static class TestActions {

        public static void subjectiveTest(MemoriHiHo frame) {
            System.out.println("SubjectiveTest");
            frame.getPanelManager().setPanel(PanelManager.SUBJECTIVE_TEST_PANEL);
        }

        public static void multipleChoiceTest(MemoriHiHo frame) {
            System.out.println("MultipleChoiceTest");
            frame.getPanelManager().setPanel(PanelManager.MULTIPLE_CHOICE_TEST_PANEL);
        }

        public static void reversedSubjectiveTest(MemoriHiHo frame) {
            System.out.println("ReversedSubjectiveTest");
            frame.getPanelManager().setPanel(PanelManager.REVERSED_SUBJECTIVE_TEST_PANEL);
        }

        public static void reversedMultipleChoiceTest(MemoriHiHo frame) {
            System.out.println("ReversedMultipleChoiceTest");
            frame.getPanelManager().setPanel(PanelManager.REVERSED_MULTIPLE_CHOICE_TEST_PANEL);
        }

    }

    public static class HelpActions {

        public static void manual(MemoriHiHo frame) {
            System.out.println("Manual");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI(frame.getDefaultVariables().getProperty("githubPages")));
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void about(MemoriHiHo frame) {
            System.out.println("About");
            new AboutDialog(frame).open();
        }

    }

}
