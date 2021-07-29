package io.github.piz2a.memorihiho.utils;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFileToString {

    public static String get(URI uri) {
        try {
            return new String(Files.readAllBytes(Paths.get(uri)));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error: " + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static String getFromResources(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try {
            URL resource = classloader.getResource(filename);
            if (resource == null)
                throw new IllegalArgumentException("file not found!");
            return get(resource.toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error: " + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;
    }

}
