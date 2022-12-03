import utils.SimpleFileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int numberOfHighestTotalCaloriesToFind = 3;

        List<List<Integer>> collectionOfCaloriesPerElf = SimpleFileReader.readFile("input.txt");
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
}
