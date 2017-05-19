package us.rlit.bowling.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * File loader loads a file and does the i/o handling outside the bowling objects
 */
public class FileLoader {

    public List<String> readFileAsString(String file) {
        Path path = Paths.get(file);
        if (!path.toFile().exists()) {
            System.out.println("[ERROR] Path to file doesn't exist: " + path);
        }
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("[ERROR] Reading file error: " + e);
        }
        return Collections.emptyList();
    }
}