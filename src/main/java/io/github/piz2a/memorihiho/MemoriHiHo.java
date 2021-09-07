package io.github.piz2a.memorihiho;

import io.github.piz2a.memorihiho.gui.MHMenuBar;
import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.gui.dialogs.MHDialog;
import io.github.piz2a.memorihiho.listener.DragDropListener;
import io.github.piz2a.memorihiho.listener.MHWindowListener;
import io.github.piz2a.memorihiho.listener.MHKeyListener;
import io.github.piz2a.memorihiho.gui.dialogs.ErrorDialog;
import io.github.piz2a.memorihiho.utils.TextFileReader;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class MemoriHiHo extends JFrame {

    public final String title = "MemoriHiHo by piz2a";
    public final int width = 800, height = 600;
    public final String settingsFilePath = "settings.txt";
    private int screenWidth, screenHeight;

    public final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    public final HashMap<String, MHDialog> isDialogOpen = new HashMap<>();
    private final TextFileReader textFileReader = new TextFileReader();
    private PanelManager panelManager;
    private File file;
    private JSONObject currentFileObject = null;
    private Properties settings;
    private Properties language;
    private boolean isNewFileBoolean;
    private boolean haveChangesBoolean;

    public MemoriHiHo() {
        loadSettings();

        basicGUISettings();

        createGUI();

        addListeners();

        MenuItemActions.FileActions.newFile(this);

        // Make the window appear
        setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);
        setVisible(true);

        // KeyListener focus
        requestFocus();
    }

    public void loadSettings() {
        File settingsFile = new File(settingsFilePath);

        if (!settingsFile.exists()) {
            // Creates default settings file
            try {
                Properties properties = new Properties();
                InputStream inputStream = classloader.getResourceAsStream("settings_default.properties");
                properties.load(inputStream);

                FileWriter fileWriter = new FileWriter(settingsFile);
                properties.store(fileWriter, "Settings");
            } catch (IOException e) {
                e.printStackTrace();
                ErrorDialog.show(e);
                System.exit(1);
            }
        }

        // Settings
        settings = new Properties();
        try {
            FileReader fileReader = new FileReader(settingsFilePath);
            settings.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.show(e);
            System.exit(1);
        }

        // Language
        language = new Properties();

        InputStream inputStream = classloader.getResourceAsStream(
                String.format("lang/%s.properties", settings.getProperty("lang"))
        );
        if (inputStream == null) {
            JOptionPane.showMessageDialog(
                    this,
                    String.format("No language named \"%s\"", settings.getProperty("lang")),
                    "Unsupported Language",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }

        try {
            language.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.show(e);
            System.exit(1);
        }
    }

    private void basicGUISettings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();

        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(width, height);
        setResizable(false);

        // Set Icon
        try {
            URL url = classloader.getResource("icons/icon.png");
            assert url != null;
            ImageIcon img = new ImageIcon(url);
            setIconImage(img.getImage());
        } catch (AssertionError e) {
            e.printStackTrace();
        }

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createGUI() {
        // Menu bar
        JMenuBar menuBar = new MHMenuBar(this).getMenuBar();
        setJMenuBar(menuBar);

        // Content Pane Manager
        panelManager = new PanelManager(this);
        getContentPane().add(panelManager);
    }

    private void addListeners() {
        addWindowListener(new MHWindowListener(this));
        addKeyListener(new MHKeyListener(this));
        new DropTarget(this, new DragDropListener(this));
    }

    public void updateTitle() {
        File file = getFile();
        setTitle(
                String.format("%s - %s [%s]",
                        title, (file == null ? language.getProperty("untitled") : file.getName()), getPanelManager().getCurrentPanelName())
                        + (haveChanges() ? "*" : "")
        );
    }

    public void refresh() {
        revalidate();
        repaint();
    }

    // Set values
    public void setFile(File file) {
        this.file = file;
    }

    public void setCurrentFileObject(JSONObject fileObject) {
        this.currentFileObject = fileObject;
    }

    public void setSettings(Properties properties) {
        this.settings = properties;
    }

    public void setLanguage(Properties properties) {
        this.language = properties;
    }

    public void setIsNewFile(boolean b) {
        this.isNewFileBoolean = b;
    }

    public void setHaveChanges(boolean b) {
        this.haveChangesBoolean = b;
    }

    // Get values
    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public TextFileReader getTextFileReader() {
        return textFileReader;
    }

    public PanelManager getPanelManager() {
        return panelManager;
    }

    public File getFile() {
        return file;
    }

    public JSONObject getCurrentFileObject() {
        return currentFileObject;
    }

    public Properties getSettings() {
        return settings;
    }

    public Properties getLanguage() {
        return language;
    }

    public boolean isNewFile() {
        return isNewFileBoolean;
    }

    public boolean haveChanges() {
        return haveChangesBoolean;
    }

    // Main
    public static void main(String[] args) {
        new MemoriHiHo();
    }

}
