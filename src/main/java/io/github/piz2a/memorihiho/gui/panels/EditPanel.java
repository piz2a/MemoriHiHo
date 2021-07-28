package io.github.piz2a.memorihiho.gui.panels;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.gui.PanelManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditPanel extends MTPanel {

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

            titleAuthorPanel.add(new JLabel("Title: "));

            titleTextField = new JTextField((String) frame.getCurrentFileObject().get("title"), 16);
            titleAuthorPanel.add(titleTextField);

            titleAuthorPanel.add(new JLabel("Author: "));

            authorTextField = new JTextField((String) frame.getCurrentFileObject().get("author"), 16);
            titleAuthorPanel.add(authorTextField);

            return titleAuthorPanel;
        }

        private JPanel getDescriptionPanel() {
            JPanel descriptionPanel = new JPanel();
            descriptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            descriptionPanel.add(new JLabel("Description: "));

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
        ArrayList<ItemPanel> itemPanelList = new ArrayList<>();
        private DisplayPanel(MemoriHiHo frame) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JSONArray elementsArray = (JSONArray) frame.getCurrentFileObject().get("elements");
            int i = 0;
            for (Object element : elementsArray) {
                JSONArray elementArray = (JSONArray) element;
                String key = (String) elementArray.get(0);
                String data = (String) elementArray.get(1);

                // Adding a row
                add(new ItemPanel(this, frame, elementsArray.size(), i, key, data));

                i++;
            }
        }

        // Row class
        static class ItemPanel extends JPanel {
            DisplayPanel displayPanel;
            JLabel itemLabel;
            JPanel swapButtonPanel;
            ElementItemPanel elementItemPanel;
            ItemPanel(DisplayPanel displayPanel, MemoriHiHo frame, int arrayLength, int i, String key, String data) {
                super();
                this.displayPanel = displayPanel;

                setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
                setBorder(new LineBorder(Color.BLACK));

                // Number label
                itemLabel = new JLabel(Integer.toString(i+1));
                itemLabel.setPreferredSize(new Dimension(30, 20));
                add(itemLabel);

                // 위치 변경 버튼 설치
                swapButtonPanel = new SwapButtonPanel(displayPanel, arrayLength, i);
                add(swapButtonPanel);

                // Displaying keys and data
                elementItemPanel = new ElementItemPanel(frame, key, data);

                add(elementItemPanel);
            }

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
            }

            static class SwapButtonPanel extends JPanel {
                SwapButtonPanel(DisplayPanel displayPanel, int arrayLength, int i) {
                    setLayout(new BorderLayout(0, 6));

                    ShapedButton upButton = new ShapedButton(new Color(i == 0 ? 0xAAAAAA : 0x880000)) {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(2*size, size);
                        }
                        @Override
                        Shape createShape() {
                            // Triangle
                            Polygon p = new Polygon();
                            p.addPoint(0, size);
                            p.addPoint(size, 0);
                            p.addPoint(2 * size, size);
                            return p;
                        }
                    };
                    if (i != 0)
                        upButton.addActionListener(new SwapActionListener(displayPanel, i, i - 1));
                    add(upButton, BorderLayout.NORTH);

                    ShapedButton downButton = new ShapedButton(new Color(i == arrayLength - 1 ? 0xAAAAAA : 0x008800)) {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(2*size, size);
                        }
                        @Override
                        Shape createShape() {
                            // Triangle
                            Polygon p = new Polygon();
                            p.addPoint(0, 0);
                            p.addPoint(size, size);
                            p.addPoint(2 * size, 0);
                            return p;
                        }
                    };
                    if (i != arrayLength - 1)
                        downButton.addActionListener(new SwapActionListener(displayPanel, i, i + 1));
                    add(downButton, BorderLayout.SOUTH);
                }

                static class SwapActionListener implements ActionListener {
                    DisplayPanel displayPanel;
                    int index, swapIndex;
                    SwapActionListener(DisplayPanel displayPanel, int index, int swapIndex) {
                        this.displayPanel = displayPanel;
                        this.index = index;
                        this.swapIndex = swapIndex;
                    }

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Text swapping
                        System.out.printf("Text Swapping: %s, %s%n", index, swapIndex);
                        Component[] components = displayPanel.getComponents(); // ItemPanel을 add한 후에 함수를 호출해야 하므로 Constructor에서 하면 안 된다.
                        System.out.println(components.length); // Debug
                        ElementItemPanel thisPanel = ((ItemPanel) components[index]).elementItemPanel;
                        ElementItemPanel thatPanel = ((ItemPanel) components[swapIndex]).elementItemPanel;
                        String keyText = thisPanel.keyTextField.getText();
                        String dataText = thisPanel.dataTextField.getText();
                        thisPanel.keyTextField.setText(thatPanel.keyTextField.getText());
                        thisPanel.dataTextField.setText(thatPanel.dataTextField.getText());
                        thatPanel.keyTextField.setText(keyText);
                        thatPanel.dataTextField.setText(dataText);
                    }
                }
            }

            abstract static class ShapedButton extends JButton {
                private final Shape shape = createShape();
                private final Color color;
                final int size = 8;

                ShapedButton(Color color) {
                    this.color = color;
                }

                public void paintBorder(Graphics g) {
                    ((Graphics2D)g).draw(shape);
                }
                public void paintComponent(Graphics g) {
                    g.setColor(color);
                    ((Graphics2D)g).fill(shape);
                }
                public boolean contains(int x, int y) {
                    return shape.contains(x, y);
                }

                abstract public Dimension getPreferredSize();
                abstract Shape createShape();
            }

        }

    }

    @Override
    JPanel getCenterPanel() {
        return new DisplayPanel(frame);
    }


    static class BottomPanel extends JPanel {
        private BottomPanel(MemoriHiHo frame, EditPanel panel) {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

            panel.addBottomButton(this, "Apply", e -> panel.applyChanges());
            panel.addBottomButton(this, "Cancel", e -> frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL));
        }
    }

    @Override
    JPanel getBottomPanel() {
        return new BottomPanel(frame, this);
    }

    void applyChanges() {
        frame.setHaveChanges(true);

        JSONObject mtObject = new JSONObject();

        InfoPanel infoPanel = (InfoPanel) topPanel;
        mtObject.put("title", infoPanel.titleTextField.getText());
        mtObject.put("author", infoPanel.authorTextField.getText());
        mtObject.put("description", infoPanel.descriptionTextField.getText());

        JSONArray elementsArray = new JSONArray();
        DisplayPanel displayPanel = (DisplayPanel) centerPanel;
        for (Component component : displayPanel.getComponents()) {
            JPanel itemPanel = (JPanel) component;
            JPanel elementItemPanel = (JPanel) itemPanel.getComponent(1);
            JTextField keyTextField = (JTextField) elementItemPanel.getComponent(0);
            JTextField dataTextField = (JTextField) elementItemPanel.getComponent(1);

            JSONArray elementArray = new JSONArray();
            elementArray.add(keyTextField.getText());
            elementArray.add(dataTextField.getText());
            elementsArray.add(elementArray);
        }
        mtObject.put("elements", elementsArray);

        frame.setCurrentFileObject(mtObject);

        frame.getPanelManager().setPanel(PanelManager.PREVIEW_PANEL);
    }

}
