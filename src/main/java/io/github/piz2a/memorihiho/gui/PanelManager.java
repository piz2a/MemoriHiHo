package io.github.piz2a.memorihiho.gui;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// Panel Manager using CardLayout for switching panels
public class PanelManager extends JPanel {

    MemoriHiHo frame;
    private final CardLayout cardLayout = new CardLayout();
    private final HashMap<String, MHPanel> panelHashMap = new HashMap<>();
    private String currentPanelName;
    private MHPanel currentPanel = null;

    public static final String PREVIEW_PANEL = "Preview";
    public static final String EDIT_PANEL = "Edit";
    public static final String NORMAL_TEST_PANEL = "Normal Test";
    public static final String QUICK_TEST_PANEL = "Quick Test";
    public static final String TEST_COMPLETE_PANEL = "Test Complete";

    public PanelManager(MemoriHiHo frame) {
        this.frame = frame;
        setLayout(cardLayout);

        addPanels();
    }

    private void addPanels() {
        panelHashMap.put(PREVIEW_PANEL, new PreviewPanel(frame));
        panelHashMap.put(EDIT_PANEL, new EditPanel(frame));
        panelHashMap.put(NORMAL_TEST_PANEL, new NormalTestPanel(frame));
        panelHashMap.put(QUICK_TEST_PANEL, new QuickTestPanel(frame));
        panelHashMap.put(TEST_COMPLETE_PANEL, new TestCompletePanel(frame));

        for (String key : panelHashMap.keySet()) {
            add(panelHashMap.get(key), key);
        }
    }

    public String getCurrentPanelName() {
        return currentPanelName;
    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    public JPanel getPanelByName(String key) {
        return panelHashMap.get(key);
    }

    public void setPanel(String panelName) {
        // Clear current panel
        if (currentPanel != null)
            currentPanel.removeAll();

        // Swap
        currentPanelName = panelName;
        currentPanel = panelHashMap.get(currentPanelName);

        // Change title
        frame.updateTitle();

        currentPanel.initialize();

        cardLayout.show(this, panelName);

        frame.refresh();
    }

}
