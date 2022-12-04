import utils.SimpleFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("section-assignments.txt")).getFile());
        List<List<int[]>> pairs = parseFileLines(SimpleFileReader.readFile(file));

        if (pairs == null) return;

        int numberOfOverlappingAssignedSectionRanges = 0;

        for (List<int[]> pair: pairs) {
            int[] sectionsRange1 = pair.get(0);
            int[] sectionsRange2 = pair.get(1);

            if (areAssignedRegionsOverlapping(sectionsRange1[0], sectionsRange1[1], sectionsRange2[0], sectionsRange2[1])) {
                numberOfOverlappingAssignedSectionRanges++;
            }
        }
        System.out.printf("The number of overlapping assigned ranges of sections within a pair of elves is: %d\n", numberOfOverlappingAssignedSectionRanges);
    }

    private static List<List<int[]>> parseFileLines(List<String> lines) {
        if (lines == null) return null;

        List<List<int[]>> assignedSections = new ArrayList<>();

        for (String line: lines) {
            List<int[]> pair = new ArrayList<>();
            String[] elfPairs = line.split(",");

            for (String assignedSectionsRaw: elfPairs) {
                int[] range = new int[2];

                String[] sectionsRange = assignedSectionsRaw.split("-");

                range[0] = Integer.parseInt(sectionsRange[0]);
                range[1] = Integer.parseInt(sectionsRange[1]);

                pair.add(range);
            }
            assignedSections.add(pair);
        }
        return assignedSections;
    }

    // If the assigned range of sections for elf #1 in this pair is contained within the assigned range of
    // sections of elf #2 in this pair, or vice versa, then an overlap is found.
    private static boolean areAssignedRegionsOverlapping(int startRegion1, int endRegion1, int startRegion2, int endRegion2) {
        List<Integer> range1 = replicateRange(startRegion1, endRegion1);
        List<Integer> range2 = replicateRange(startRegion2, endRegion2);

        return range1.stream().anyMatch(range2::contains) || range2.stream().anyMatch(range1::contains);
    }

    private static List<Integer> replicateRange(int start, int end) {
        return IntStream
            .rangeClosed(start, end)
            .boxed()
            .collect(Collectors.toList());
    }
}
