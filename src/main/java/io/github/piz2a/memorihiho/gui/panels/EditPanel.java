package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.PanelManager;
import io.github.piz2a.memorihiho.gui.ShapedButton;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EditPanel extends MHPanel {

    public EditPanel(MemoriHiHo frame) {
        super(frame);
    }

    // Top bar
    static class InfoPanel extends JPanel {
        MemoriHiHo frame;
        JTextField titleTextField, authorTextField, descriptionTextField;

        private InfoPanel(MemoriHiHo frame) {
            this.frame = frame;

            setLayout(new BorderLayout());

            add(getTitleAuthorPanel(), BorderLayout.NORTH);
            add(getDescriptionPanel(), BorderLayout.SOUTH);
        }

        private JPanel getTitleAuthorPanel() {
            JPanel titleAuthorPanel = new JPanel();
            titleAuthorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            titleAuthorPanel.add(new JLabel(String.format("%s: ", frame.getLanguage().getProperty("editPanel.label.title"))));

            titleTextField = new JTextField((String) frame.getCurrentFileObject().get("title"), 16);
            titleAuthorPanel.add(titleTextField);

            titleAuthorPanel.add(new JLabel(String.format("%s: ", frame.getLanguage().getProperty("editPanel.label.author"))));

            authorTextField = new JTextField((String) frame.getCurrentFileObject().get("author"), 16);
            titleAuthorPanel.add(authorTextField);

            return titleAuthorPanel;
        }

        private JPanel getDescriptionPanel() {
            JPanel descriptionPanel = new JPanel();
            descriptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            descriptionPanel.add(new JLabel(String.format("%s: ", frame.getLanguage().getProperty("editPanel.label.description"))));

            descriptionTextField = new JTextField((String) frame.getCurrentFileObject().get("description"), 48);
            descriptionPanel.add(descriptionTextField);

            return descriptionPanel;
        }
    }

    @Override
    JPanel getTopPanel() {
        return new InfoPanel(frame);
    }

    // Displaying elements
    static class DisplayPanel extends JPanel {
        private DisplayPanel(MemoriHiHo frame) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JSONArray elementsArray = (JSONArray) frame.getCurrentFileObject().get("elements");
            int i = 0;
            for (Object element : elementsArray) {
                JSONArray elementArray = (JSONArray) element;
                String key = (String) elementArray.get(0);
                String data = (String) elementArray.get(1);

                // Adding a row
                add(new ItemPanel(this, frame, i, key, data));

                i++;
            }
        }

        // Row class
        static class ItemPanel extends JPanel {
            DisplayPanel displayPanel;
            MemoriHiHo frame;
            JLabel itemLabel;
            JPanel swapButtonPanel;
            ElementItemPanel elementItemPanel;
            ShapedButton destroyButton;

            ItemPanel(DisplayPanel displayPanel, MemoriHiHo frame, int i, String key, String data) {
                super();
                this.displayPanel = displayPanel;
                this.frame = frame;

                setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
                setBorder(new LineBorder(Color.BLACK));

                // Number label
                itemLabel = new JLabel(Integer.toString(i+1));
                itemLabel.setPreferredSize(new Dimension(16, 20));
                add(itemLabel);

                // 위치 변경 버튼 설치
                swapButtonPanel = new SwapButtonPanel(displayPanel, frame, i);
                add(swapButtonPanel);

                // Displaying keys and data
                elementItemPanel = new ElementItemPanel(frame, key, data);
                add(elementItemPanel);

                // Destroy Button
                destroyButton = getDestroyButton(i);
                add(destroyButton);
            }

            static class SwapButtonPanel extends JPanel {
                MemoriHiHo frame;
                SwapButtonPanel(DisplayPanel displayPanel, MemoriHiHo frame, int i) {
                    this.frame = frame;
                    setLayout(new BorderLayout(0, 6));

                    ShapedButton upButton = new ShapedButton(new Color(i == 0 ? 0xAAAAAA : 0x6666AA)) {
                        final int size = 8;
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(2*size, size);
                        }
                        @Override
                        protected Shape createShape() {
                            // Triangle
                            Polygon p = new Polygon();
                            p.addPoint(0, size);
                            p.addPoint(size, 0);
                            p.addPoint(2 * size, size);
                            return p;
                        }
                    };
                    if (i != 0)
                        upButton.addActionListener(e -> {
                            // Text swapping
                            System.out.printf("Text Swapping: %s, %s%n", i, i - 1);
                            Component[] components = displayPanel.getComponents(); // ItemPanel을 add한 후에 함수를 호출해야 하므로 Constructor에서 하면 안 된다.
                            //System.out.println(components.length); // Debug
                            ItemPanel.ElementItemPanel thisPanel = ((ItemPanel) components[i]).elementItemPanel;
                            ItemPanel.ElementItemPanel thatPanel = ((ItemPanel) components[i - 1]).elementItemPanel;
                            String keyText = thisPanel.keyTextField.getText();
                            String dataText = thisPanel.dataTextField.getText();
                            thisPanel.keyTextField.setText(thatPanel.keyTextField.getText());
                            thisPanel.dataTextField.setText(thatPanel.dataTextField.getText());
                            thatPanel.keyTextField.setText(keyText);
                            thatPanel.dataTextField.setText(dataText);
                        });
                    add(upButton, BorderLayout.NORTH);

                    ShapedButton addButton = new ShapedButton(new Color(0x00AA00)) {
                        final int size = 5;
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(3*size, 3*size);
                        }
                        @Override
                        protected Shape createShape() {
                            // Cross shape
                            Polygon p = new Polygon();
                            p.addPoint(size, 0);
                            p.addPoint(size, size);
                            p.addPoint(0, size);
                            p.addPoint(0, 2*size);
                            p.addPoint(size, 2*size);
                            p.addPoint(size, 3*size);
                            p.addPoint(2*size, 3*size);
                            p.addPoint(2*size, 2*size);
                            p.addPoint(3*size, 2*size);
                            p.addPoint(3*size, size);
                            p.addPoint(2*size, size);
                            p.addPoint(2*size, 0);
                            return p;
                        }
                    };
                    addButton.addActionListener(e -> {
                        System.out.println("Add Button Clicked: " + i);
                        // Adding an ItemPanel
                        displayPanel.add(new ItemPanel(displayPanel, frame, displayPanel.getComponentCount(), "", ""));
                        Component[] components = displayPanel.getComponents();
                        // Pushing texts one by one
                        for (int k = components.length - 2; k >= i+1; k--) {
                            displayPanel.moveItemText(components, k, k+1);
                        }
                        ItemPanel itemPanel = (ItemPanel) components[i+1];
                        itemPanel.elementItemPanel.keyTextField.setText("");
                        itemPanel.elementItemPanel.dataTextField.setText("");
                        frame.refresh();
                    });
                    add(addButton, BorderLayout.SOUTH);
                } // End of constructor

            } // End of SwapButtonPanel

            // TextField Panel
            static class ElementItemPanel extends JPanel {
                JTextField keyTextField, dataTextField;
                ElementItemPanel(MemoriHiHo frame, String key, String data) {
                    setLayout(new GridLayout(1, 2));
                    setPreferredSize(new Dimension(frame.width - 200, 20));

                    keyTextField = new JTextField(key, 15);
                    dataTextField = new JTextField(data, 15);
                    add(keyTextField);
                    add(dataTextField);
                }
            } // End of ElementItemPanel

            ShapedButton getDestroyButton(int index) {
                ShapedButton destroyButton = new ShapedButton(new Color(0xFF0000)) {
                    final int size = 4;
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(6*size, 6*size);
                    }
                    @Override
                    protected Shape createShape() {
                        // X Shape
                        Polygon p = new Polygon();
                        p.addPoint(size, 0);
                        p.addPoint(0, size);
                        p.addPoint(2*size, 3*size);
                        p.addPoint(0, 5*size);
                        p.addPoint(size, 6*size);
                        p.addPoint(3*size, 4*size);
                        p.addPoint(5*size, 6*size);
                        p.addPoint(6*size, 5*size);
                        p.addPoint(4*size, 3*size);
                        p.addPoint(6*size, size);
                        p.addPoint(5*size, 0);
                        p.addPoint(3*size, 2*size);
                        return p;
                    }
                };
                destroyButton.addActionListener(e -> {
                    System.out.println("Destroy Button Clicked: " + index);
                    Component[] components = displayPanel.getComponents();
                    // Block the action if there's only one row left
                    if (components.length == 1) {
                        JOptionPane.showMessageDialog(
                                frame,
                                frame.getLanguage().getProperty("editPanel.message.blockOneRow"),
                                frame.getLanguage().getProperty("editPanel.message.blockOneRow.title"),
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                    // Pushing texts one by one
                    for (int k = index; k < components.length - 1; k++) {
                        displayPanel.moveItemText(components, k + 1, k);
                    }
                    // Removing the last row
                    displayPanel.remove(components.length - 1);
                    frame.refresh();
                });
                return destroyButton;
            }

        } // End of ItemPanel

        void moveItemText(Component[] components, int indexFrom, int indexTo) {
            ItemPanel.ElementItemPanel panelFrom = ((ItemPanel) components[indexFrom]).elementItemPanel;
            ItemPanel.ElementItemPanel panelTo = ((ItemPanel) components[indexTo]).elementItemPanel;
            panelTo.keyTextField.setText(panelFrom.keyTextField.getText());
            panelTo.dataTextField.setText(panelFrom.dataTextField.getText());
        }

    } // End of DisplayPanel

    @Override
    JPanel getCenterPanel() {
        return new DisplayPanel(frame);
    }

    // Bottom Panel including buttons
    static class BottomPanel extends JPanel {
        private BottomPanel(MemoriHiHo frame, EditPanel panel) {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

            panel.addBottomButton(this, frame.getLanguage().getProperty("editPanel.button.apply"), e -> panel.applyChanges());
            panel.addBottomButton(this, frame.getLanguage().getProperty("editPanel.button.cancel"), e -> frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL));
        }
    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(frame, this);
    }

    public void applyChanges() {
        JSONObject mtObject = new JSONObject();

        InfoPanel infoPanel = (InfoPanel) topPanel;
        mtObject.put("title", infoPanel.titleTextField.getText());
        mtObject.put("author", infoPanel.authorTextField.getText());
        mtObject.put("description", infoPanel.descriptionTextField.getText());

        JSONArray elementsArray = new JSONArray();
        DisplayPanel displayPanel = (DisplayPanel) centerPanel;
        for (Component component : displayPanel.getComponents()) {
            DisplayPanel.ItemPanel itemPanel = (DisplayPanel.ItemPanel) component;
            DisplayPanel.ItemPanel.ElementItemPanel elementItemPanel = itemPanel.elementItemPanel;
            JTextField keyTextField = elementItemPanel.keyTextField;
            JTextField dataTextField = elementItemPanel.dataTextField;
            String keyText = keyTextField.getText();
            String dataText = dataTextField.getText();

            if (keyText.equals("") || dataText.equals("")) {
                JOptionPane.showMessageDialog(
                        frame,
                        frame.getLanguage().getProperty("editPanel.message.fillTheBlanks"),
                        frame.getLanguage().getProperty("editPanel.message.fillTheBlanks.title"),
                        JOptionPane.WARNING_MESSAGE
                );
                System.out.println("Failed to apply changes.");
                return;
            }

            JSONArray elementArray = new JSONArray();
            elementArray.add(keyText);
            elementArray.add(dataText);
            elementsArray.add(elementArray);
        }
        mtObject.put("elements", elementsArray);

        frame.setCurrentFileObject(mtObject);
        frame.setHaveChanges(true);

        frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL);
        System.out.println("Successfully Applied Changes");
    }

}
