package io.github.piz2a.memorihiho.gui;

import javax.swing.*;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MHMenuBar extends JMenuBar {

    private final MemoriHiHo frame;

    public MHMenuBar(MemoriHiHo frame) {
        super();
        this.frame = frame;
        create();
    }

    private void create() {
        JMenu fileMenu = new JMenu(frame.getLanguage().getProperty("menuBar.file"));
        fileMenu.add(getIconMenuItem("new_file", e -> MenuItemActions.FileActions.newFile(frame)));
        fileMenu.add(getIconMenuItem("open", e -> MenuItemActions.FileActions.open(frame)));
        /*if (frame.getSettings().getProperty("showRecentFiles").equals("true")) {
            fileMenu.add(getRecentFilesSubMenu());
        }*/
        fileMenu.addSeparator();
        fileMenu.add(getIconMenuItem("edit", e -> MenuItemActions.FileActions.edit(frame)));
        fileMenu.addSeparator();
        fileMenu.add(getIconMenuItem("settings", e -> MenuItemActions.FileActions.settings(frame)));
        fileMenu.addSeparator();
        fileMenu.add(getIconMenuItem("save", e -> MenuItemActions.FileActions.save(frame)));
        fileMenu.add(getIconMenuItem("save_as", e -> MenuItemActions.FileActions.saveAs(frame)));
        fileMenu.addSeparator();
        fileMenu.add(getIconMenuItem("exit", e -> MenuItemActions.FileActions.exit(frame)));
        add(fileMenu);

        JMenu testMenu = new JMenu(frame.getLanguage().getProperty("menuBar.test"));
        testMenu.add(getIconMenuItem("subjective_test", e -> MenuItemActions.TestActions.subjectiveTest(frame)));
        testMenu.add(getIconMenuItem("multiple_choice_test", e -> MenuItemActions.TestActions.multipleChoiceTest(frame)));
        testMenu.add(getIconMenuItem("reversed_subjective_test", e -> MenuItemActions.TestActions.reversedSubjectiveTest(frame)));
        testMenu.add(getIconMenuItem("reversed_multiple_choice_test", e -> MenuItemActions.TestActions.reversedMultipleChoiceTest(frame)));
        add(testMenu);

        JMenu helpMenu = new JMenu(frame.getLanguage().getProperty("menuBar.help"));
        helpMenu.add(getIconMenuItem("manual", e -> MenuItemActions.HelpActions.manual(frame)));
        helpMenu.add(getIconMenuItem("about", e -> MenuItemActions.HelpActions.about(frame)));
        add(helpMenu);
    }

    private JMenuItem getIconMenuItem(String menuItemName, ActionListener actionListener) {
        return getMenuItem(menuItemName, frame.getLanguage().getProperty("menuBarItem." + menuItemName), true, actionListener);
    }

    private JMenuItem getMenuItem(String menuItemName, String menuItemText, boolean icon, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(menuItemText) {
            // Set item size
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.width = Math.max(d.width, 40);
                d.height = Math.max(d.height, 30);
                return d;
            }
        };
        if (icon) {
            URL iconResource = frame.classloader.getResource(String.format("icons/menubar/%s.png", menuItemName));
            if (iconResource != null)
                item.setIcon(new ImageIcon(iconResource));
        }
        item.addActionListener(actionListener);

        return item;
    }

    private JMenu getSubMenu(String menuItemText, String menuItemName, boolean icon) {
        JMenu menu = new JMenu(menuItemText) {
            // Set item size
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.width = Math.max(d.width, 40);
                d.height = Math.max(d.height, 30);
                return d;
            }
        };
        if (icon) {
            URL iconResource = frame.classloader.getResource(String.format("icons/menubar/%s.png", menuItemName));
            if (iconResource != null)
                menu.setIcon(new ImageIcon(iconResource));
        }

        return menu;
    }

    private JMenu getRecentFilesSubMenu() {
        JMenu menu = getSubMenu(frame.getLanguage().getProperty("menuBarItem.recent_files"), "recent_files", true);
        for (int i = 0; i < Integer.parseInt(frame.getDefaultVariables().getProperty("numberOfRecentFiles")); i++) {
            if (frame.getSettings().containsKey(String.format("recentFiles.%d", i))) {
                String path = new String(frame.getSettings().getProperty(String.format("recentFiles.%d", i)).getBytes(), StandardCharsets.UTF_8);
                if (path.length() != 0) {
                    String[] split = path.split("/");
                    menu.add(getMenuItem(null, split[split.length - 1], false, e -> MenuItemActions.FileActions.openFile(frame, new File(path))));
                }
            }
        }
        return menu;
    }

}
