package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleFileReader {
    public static List<String> readFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            List<String> lines = new ArrayList<>();

            String line = fileReader.readLine();

            do {
                lines.add(line);

                line = fileReader.readLine();
            }
            while (line != null);

            return lines;
        }
        catch (IOException | NullPointerException exception) {
            System.out.printf("Something unexpected happened while trying to read file: '%s'\n", file.getPath());
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
