package io.github.piz2a.memorihiho.utils;

import io.github.piz2a.memorihiho.gui.dialogs.ErrorDialog;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class TextFileReader {

    FileSystem fileSystem = null;

    public String getString(URI uri) {
        try {
            /*
             * (When in jar) jar:file:/C:/Users/jiho/Code/Java/MemoriHiHo/target/MemoriHiHo-0.1-SNAPSHOT-jar-with-dependencies.jar!/menubar.json
             * (When not in jar) file:/C:/Users/jiho/Code/Java/MemoriHiHo/target/classes/menubar.json
             */
            System.out.println(uri);
            final Path path;
            if (uri.toString().startsWith("jar:")) {  // jar
                Map<String, String> env = new HashMap<>();
                String[] array = uri.toString().split("!");
                if (fileSystem == null)
                    fileSystem = FileSystems.newFileSystem(URI.create(array[0]), env);
                path = fileSystem.getPath(array[1]);
            } else {  // not jar
                path = Paths.get(uri);
            }
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.show(e);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (FileSystemNotFoundException e) {
            e.printStackTrace();
            System.out.println(uri);
        }
        return null;
    }

    public String getStringFromResources(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try {
            URL resource = classloader.getResource(filename);
            if (resource == null)
                throw new IllegalArgumentException("file not found!");
            return getString(resource.toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            e.printStackTrace();
            ErrorDialog.show(e);
            System.exit(1);
        }
        return null;
    }

}
