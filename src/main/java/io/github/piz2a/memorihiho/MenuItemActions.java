package io.github.piz2a.memorihiho;

import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.utils.TextFileToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

public class MenuItemActions {

    public static class FileActions {

        public static void newFile(MemoriHiHo frame) {
            System.out.println("NewFile");
            String jsonData = TextFileToString.getFromResources("untitled.json");
            openFile(frame, jsonData, null, true);
        }

        public static void open(MemoriHiHo frame) {
            System.out.println("Open");

            final JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON File", "json");
            fc.setFileFilter(filter);

            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                java.io.File file = fc.getSelectedFile();
                System.out.println("Opening " + file.getName());
                String jsonData = TextFileToString.get(file.toURI());

                openFile(frame, jsonData, file,false);
            } else {
                System.out.println("Opening cancelled");
            }
        }

        private static void openFile(MemoriHiHo frame, String jsonData, java.io.File file, boolean isNewFile) {
            JSONParser parser = new JSONParser();
            try {
                frame.setCurrentFileObject((JSONObject) parser.parse(jsonData));
                frame.setIsNewFile(isNewFile);
                // System.out.println(frame.getCurrentFileObject()); // Debug

                frame.setFile(file);
                frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public static void recentFiles() {
            System.out.println("RecentFiles");
        }

        public static void edit(MemoriHiHo frame) {
            System.out.println("Edit");
            frame.getPanelManager().setPanel(PanelManager.EDIT_PANEL);
        }

        public static void options() {
            System.out.println("Options");
        }

        public static void save(MemoriHiHo frame) {
            System.out.println("Save");
            if (frame.isNewFile()) {
                saveAs(frame);
            } else {
                saveFile(frame, frame.getFile());
            }
        }

        public static void saveAs(MemoriHiHo frame) {
            System.out.println("SaveAs");
            final JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON File", "json");
            fc.setFileFilter(filter);

            int returnVal = fc.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                saveFile(frame, fc.getSelectedFile());
            } else {
                System.out.println("Saving cancelled");
            }
        }

        private static void saveFile(MemoriHiHo frame, java.io.File file) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                System.out.println("Saving " + file.getName());
                fileWriter.write(frame.getCurrentFileObject().toJSONString());
                fileWriter.flush();

                frame.setHaveChanges(false);
                frame.setIsNewFile(false);
                frame.setFile(file);
                frame.updateTitle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void exit(JFrame frame) {
            System.out.println("Exit");
            // Try to close JFrame
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

    }

    public static class TestActions {
    }

    public static class HelpActions {
    }

}
