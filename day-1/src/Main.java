import utils.SimpleFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        final int numberOfHighestTotalCaloriesToFind = 3;

        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("input.txt")).getFile());

        List<List<Integer>> collectionOfCaloriesPerElf = parseFileLines(SimpleFileReader.readFile(file));
        List<Integer> totalCaloriesPerElf = new ArrayList<>();
        int[] highestTotalCaloriesCollection = new int[numberOfHighestTotalCaloriesToFind];

        if (collectionOfCaloriesPerElf == null) return;

        for (List<Integer> caloriesPerElf: collectionOfCaloriesPerElf) {
            int totalCalories = caloriesPerElf
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

            totalCaloriesPerElf.add(totalCalories);
        }

        for (int i = 0; i < numberOfHighestTotalCaloriesToFind; i++) {
            int highestTotalCalories = totalCaloriesPerElf.stream()
                .mapToInt(Integer::intValue)
                .max()
                .getAsInt();

            totalCaloriesPerElf.remove(totalCaloriesPerElf.indexOf(highestTotalCalories));
            highestTotalCaloriesCollection[i] = highestTotalCalories;
        }
        int totalCalories = Arrays.stream(highestTotalCaloriesCollection).sum();

        System.out.printf("The number of calories that the top %d of Elves are carrying is a total of %d Calories.\n", numberOfHighestTotalCaloriesToFind, totalCalories);
    }

    private static List<List<Integer>> parseFileLines(List<String> fileLines) {
        if (fileLines == null) return null;

        List<List<Integer>> collectionOfCaloriesPerElf = new ArrayList<>();
        List<Integer> caloriesPerElf = new ArrayList<>();

        for (String line: fileLines) {
            if (line.isBlank() || line.isEmpty()) {
                collectionOfCaloriesPerElf.add(caloriesPerElf);
                caloriesPerElf = new ArrayList<>();
                continue;
            }
            caloriesPerElf.add(Integer.parseInt(line));
        }
        return collectionOfCaloriesPerElf;
    }
}
