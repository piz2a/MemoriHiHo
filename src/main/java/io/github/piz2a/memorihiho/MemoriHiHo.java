package io.github.piz2a.memorihiho;

import io.github.piz2a.memorihiho.gui.MHMenuBar;
import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.listener.MHWindowListener;
import io.github.piz2a.memorihiho.listener.MHKeyListener;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class MemoriHiHo extends JFrame {

    public final String title = "Memorization Test by piz2a";
    public final int width = 800, height = 600;
    private int screenWidth, screenHeight;

    private PanelManager panelManager;
    private File file;
    private JSONObject currentFileObject = null;
    private boolean isNewFileBoolean;
    private boolean haveChangesBoolean;

    public MemoriHiHo() {
        basicGuiSettings();

        createGUI();

        addListeners();

        // Make the window appear
        setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);
        setVisible(true);

        // KeyListener focus
        requestFocus();
    }

    private void basicGuiSettings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();

        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(width, height);
        setResizable(false);

        // Set Icon
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try {
            URL url = classloader.getResource("icon.png");
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

        MenuItemActions.FileActions.newFile(this);
    }

    private void addListeners() {
        addWindowListener(new MHWindowListener(this));
        addKeyListener(new MHKeyListener(this));
    }

    public void updateTitle() {
        File file = getFile();
        setTitle(
                String.format("%s - %s [%s]",
                        title, (file == null ? "Untitled" : file.getName()), getPanelManager().getCurrentPanelName())
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

    public void setIsNewFile(boolean b) {
        this.isNewFileBoolean = b;
    }

    public void setHaveChanges(boolean b) {
        this.haveChangesBoolean = b;
    }

    // Get values
    public PanelManager getPanelManager() {
        return panelManager;
    }

    public File getFile() {
        return file;
    }

    public JSONObject getCurrentFileObject() {
        return currentFileObject;
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
