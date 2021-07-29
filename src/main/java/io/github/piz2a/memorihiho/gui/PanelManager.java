package io.github.piz2a.memorihiho.gui;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.panels.EditPanel;
import io.github.piz2a.memorihiho.gui.panels.MHPanel;
import io.github.piz2a.memorihiho.gui.panels.PreviewPanel;

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

    public final static String PREVIEW_PANEL = "Preview";
    public final static String EDIT_PANEL = "Edit";
    public final static String NORMAL_TEST_PANEL = "Normal Test";
    public final static String QUICK_TEST_PANEL = "Quick Test";

    public PanelManager(MemoriHiHo frame) {
        this.frame = frame;
        setLayout(cardLayout);

        addPanels();
    }

    private void addPanels() {
        panelHashMap.put(PREVIEW_PANEL, new PreviewPanel(frame));
        panelHashMap.put(EDIT_PANEL, new EditPanel(frame));
        //panelHashMap.put(NORMAL_TEST_PANEL, new PreviewPanel(frame));
        //panelHashMap.put(QUICK_TEST_PANEL, new PreviewPanel(frame));

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

    public void setPanel(String panelName) {
        // Clear current panel
        if (currentPanel != null)
            currentPanel.removeAll();

        // Swap
        cardLayout.show(this, panelName);

        currentPanelName = panelName;
        currentPanel = panelHashMap.get(currentPanelName);

        // Change title
        frame.updateTitle();

        currentPanel.initialize();

        frame.refresh();
    }

}
