import utils.SimpleFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class Main {
    public static final Map<Character, Integer> PRIORITY_PER_ITEM = Map.<Character, Integer>ofEntries(
        entry('a', 1), entry('b', 2), entry('c', 3), entry('d', 4), entry('e', 5),
        entry('f', 6), entry('g', 7), entry('h', 8), entry('i', 9), entry('j', 10),
        entry('k', 11), entry('l', 12), entry('m', 13), entry('n', 14), entry('o', 15),
        entry('p', 16), entry('q', 17), entry('r', 18), entry('s', 19), entry('t', 20),
        entry('u', 21), entry('v', 22), entry('w', 23), entry('x', 24), entry('y', 25),
        entry('z', 26), entry('A', 27), entry('B', 28), entry('C', 29), entry('D', 30),
        entry('E', 31), entry('F', 32), entry('G', 33), entry('H', 34), entry('I', 35),
        entry('J', 36), entry('K', 37), entry('L', 38), entry('M', 39), entry('N', 40),
        entry('O', 41), entry('P', 42), entry('Q', 43), entry('R', 44), entry('S', 45),
        entry('T', 46), entry('U', 47), entry('V', 48), entry('W', 49), entry('X', 50),
        entry('Y', 51), entry('Z', 52)
    );

    public static void main(String[] args) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("rucksack-organisation.txt")).getFile());
        List<String[]> groups = parseFileLines(SimpleFileReader.readFile(file));

        if (groups == null) return;

        int sumOfCommonPriorityItems = 0;

        for (String[] group: groups) {
            for (Map.Entry<Character, Integer> entry: PRIORITY_PER_ITEM.entrySet()) {
                boolean itemPresentInRucksack1 = group[0].contains(entry.getKey().toString());
                boolean itemPresentInRucksack2 = group[1].contains(entry.getKey().toString());
                boolean itemPresentInRucksack3 = group[2].contains(entry.getKey().toString());

                if (itemPresentInRucksack1 && itemPresentInRucksack2 && itemPresentInRucksack3) {
                    sumOfCommonPriorityItems += entry.getValue();
                }
            }
        }
        System.out.printf("The sum of the priority of the item type that appears in all rucksacks in 1 group is: %d\n", sumOfCommonPriorityItems);
    }

    private static List<String[]> parseFileLines(List<String> lines) {
        if (lines == null) return null;

        final int maxGroupSize = 3;

        List<String[]> groups = new ArrayList<>();
        List<String> group = new ArrayList<>();

        for (String line: lines) {
            group.add(line);

            if (group.size() == maxGroupSize) {
                groups.add(group.toArray(String[]::new));
                group = new ArrayList<>();
            }
        }
        return groups;
    }
}
