package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleFileReader {
    public static List<List<Integer>> readFile(String path) {
        try {
            File file = new File(Objects.requireNonNull(SimpleFileReader.class.getClassLoader().getResource(path)).getFile());
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            List<List<Integer>> caloriesPerElf = new ArrayList<>();
            List<Integer> calories = new ArrayList<>();

            String line = fileReader.readLine();

            do {
                // Total calories of one elf are collected, start over for the next elf.
                if (line.isBlank() || line.isEmpty()) {
                    caloriesPerElf.add(calories);
                    calories = new ArrayList<>();

                    line = fileReader.readLine();
                    continue;
                }
                calories.add(Integer.parseInt(line));

                line = fileReader.readLine();
            }
            while (line != null);

            return caloriesPerElf;
        }
        catch (IOException | NullPointerException exception) {
            System.out.printf("Something unexpected happened while trying to read file: '%s'\n", path);
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
