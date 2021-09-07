package io.github.piz2a.memorihiho.listener;

import io.github.piz2a.memorihiho.MemoriHiHo;
import io.github.piz2a.memorihiho.MenuItemActions;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class DragDropListener implements DropTargetListener {

    MemoriHiHo frame;

    public DragDropListener(MemoriHiHo frame) {
        this.frame = frame;
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        event.acceptDrop(DnDConstants.ACTION_COPY);
        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        System.out.println("File Dropped");
        //System.out.println(event.getDropAction());

        for (DataFlavor flavor : flavors) {  // Loop through the flavors
            try {
                if (flavor.isFlavorJavaFileListType()) {  // If the drop items are files
                    List<File> files = (List<File>) transferable.getTransferData(flavor); // Get all of the dropped files
                    if (files.size() == 1) {  // If there's only one file
                        // Opens it
                        File file = files.get(0);
                        String jsonData = frame.getTextFileReader().getString(file.toURI());
                        MenuItemActions.FileActions.openFile(frame, jsonData, file, false);
                    } else {  // If two or more
                        JOptionPane.showMessageDialog(
                                frame,
                                frame.getLanguage().getProperty("message.tooManyFilesOpening"),
                                frame.getLanguage().getProperty("message.tooManyFilesOpening.title"),
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dragEnter(DropTargetDragEvent event) {
    }

    @Override
    public void dragOver(DropTargetDragEvent event) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
    }

    @Override
    public void dragExit(DropTargetEvent event) {
    }

}
