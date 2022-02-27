package io.github.piz2a.memorihiho.listener;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MHKeyDispatcher implements KeyEventDispatcher {

    private boolean pressedControl = false;
    private final MemoriHiHo frame;

    public MHKeyDispatcher(MemoriHiHo frame) {
        this.frame = frame;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {  // Key Pressed
            int key = e.getKeyCode();
            //System.out.println(pressedControl + " " + key);
            if (pressedControl) {
                switch (key) {
                    case KeyEvent.VK_N:
                        MenuItemActions.FileActions.newFile(frame);
                        break;
                    case KeyEvent.VK_O:
                        MenuItemActions.FileActions.open(frame);
                        break;
                    case KeyEvent.VK_S:
                        MenuItemActions.FileActions.save(frame);
                        break;
                    case KeyEvent.VK_E:
                        MenuItemActions.FileActions.edit(frame);
                        break;
                }
            } else {
                pressedControl = key == KeyEvent.VK_CONTROL;
            }
        }

        else if (e.getID() == KeyEvent.KEY_RELEASED) {  // Key Released
            if (e.getKeyCode() == KeyEvent.VK_CONTROL)
                pressedControl = false;
        }

        return false;
    }

}
