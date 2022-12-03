import utils.SimpleFileReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> collectionOfCaloriesPerElf = SimpleFileReader.readFile("input.txt");

        if (collectionOfCaloriesPerElf == null) return;

        int highestCaloriesCount = 0;

        for (List<Integer> caloriesPerElf: collectionOfCaloriesPerElf) {
            int totalCalories = caloriesPerElf
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

            highestCaloriesCount = Math.max(highestCaloriesCount, totalCalories);
        }

        System.out.printf("The Elf which is carrying the highest total Calories, is carrying: %d Calories\n", highestCaloriesCount);
    }
}
