package io.github.piz2a.memorihiho.gui;

import javax.swing.*;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

public class MHMenuBar {

    private final MemoriHiHo frame;
    private final JMenuBar menuBar = new JMenuBar();

    public MHMenuBar(MemoriHiHo frame) {
        this.frame = frame;
        create();
    }

    private void create() {

        // Open and read menubar.json
        String jsonData = frame.getTextFileReader().getStringFromResources("menubar.json");
        //System.out.println(jsonData);

        // Parse json
        JSONParser parser = new JSONParser();
        try {
            JSONArray menuBarData = (JSONArray) parser.parse(jsonData);

            // Add menu
            addMenu(menuBarData);

            // Key bindings

            //menuBar.requestFocus();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void addMenu(JSONArray menuBarData) {

        for (Object i : menuBarData) {

            // Creates menu object
            JSONObject menuObject = (JSONObject) i;
            String menuName = (String) menuObject.get("menuName");
            JMenu menu = new JMenu(frame.getLanguage().getProperty("menuBar." + menuName)) {
                // Set menu size
                @Override
                public Dimension getPreferredSize() {
                    Dimension d = super.getPreferredSize();
                    d.width = Math.max(d.width, 40);
                    d.height = Math.max(d.height, 30);
                    return d;
                }
            };
            //menu.setFont(font);

            // Add items
            JSONArray itemsArray = (JSONArray) menuObject.get("items");
            for (Iterator<Object> j = itemsArray.iterator(); j.hasNext();) {
                // Separated
                for (Object menuItemObject : (JSONArray) j.next()) {
                    // Add an item
                    String menuItemName = (String) menuItemObject;
                    JMenuItem item = new JMenuItem(frame.getLanguage().getProperty("menuBarItem." + menuItemName)) {
                        // Set item size
                        @Override
                        public Dimension getPreferredSize() {
                            Dimension d = super.getPreferredSize();
                            d.width = Math.max(d.width, 40);
                            d.height = Math.max(d.height, 30);
                            return d;
                        }
                    };

                    URL iconResource = frame.classloader.getResource(String.format("icons/menubar/%s.png", menuItemName));
                    if (iconResource != null)
                        item.setIcon(new ImageIcon(iconResource));

                    item.addActionListener(getActionListener(menuItemName));

                    menu.add(item);
                }
                if (j.hasNext())
                    menu.addSeparator();
            }

            // Add menu object
            menuBar.add(menu);

        }

    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private ActionListener getActionListener(String actionName) {
        ActionListener listener = e -> {};
        switch(actionName) {
            case "new_file":
                listener = e -> MenuItemActions.FileActions.newFile(frame);
                break;
            case "open":
                listener = e -> MenuItemActions.FileActions.open(frame);
                break;
            case "recent_files":
                listener = e -> MenuItemActions.FileActions.recentFiles();
                break;
            case "edit":
                listener = e -> MenuItemActions.FileActions.edit(frame);
                break;
            case "settings":
                listener = e -> MenuItemActions.FileActions.settings(frame);
                break;
            case "save":
                listener = e -> MenuItemActions.FileActions.save(frame);
                break;
            case "save_as":
                listener = e -> MenuItemActions.FileActions.saveAs(frame);
                break;
            case "exit":
                listener = e -> MenuItemActions.FileActions.exit(frame);
                break;
            case "subjective_test":
                listener = e -> MenuItemActions.TestActions.subjectiveTest(frame);
                break;
            case "multiple_choice_test":
                listener = e -> MenuItemActions.TestActions.multipleChoiceTest(frame);
                break;
            case "reversed_subjective_test":
                listener = e -> MenuItemActions.TestActions.reversedSubjectiveTest(frame);
                break;
            case "reversed_multiple_choice_test":
                listener = e -> MenuItemActions.TestActions.reversedMultipleChoiceTest(frame);
                break;
            case "manual":
                listener = e -> MenuItemActions.HelpActions.manual(frame);
                break;
            case "about":
                listener = e -> MenuItemActions.HelpActions.about(frame);
                break;
        }
        return listener;
    }

}
