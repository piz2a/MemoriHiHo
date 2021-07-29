package io.github.piz2a.memorihiho.listener;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MHKeyListener extends KeyAdapter {

    private boolean pressedControl = false;
    private final MemoriHiHo frame;

    public MHKeyListener(MemoriHiHo frame) {
        this.frame = frame;
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
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
            }
        } else {
            pressedControl = key == KeyEvent.VK_CONTROL;
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            pressedControl = false;
    }

}
