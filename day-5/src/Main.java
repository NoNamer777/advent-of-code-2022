import models.CargoCrateInstructions;
import utils.SimpleFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final List<CargoCrateInstructions> cargoInstructions = new ArrayList<>();
    private static final List<List<String>> cargoStorage = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("crates-rearrangement-steps.txt")).getFile());

        parseFileLines(Objects.requireNonNull(SimpleFileReader.readFile(file)));

        cargoInstructions.forEach(instruction -> {
            List<String> startPile = cargoStorage.get(instruction.startPile() - 1);
            List<String> destinationPile = cargoStorage.get(instruction.destinationPile() - 1);
            
            int destinationPileSize = destinationPile.size();
            
            for (int i = 0; i < instruction.numberOfCrates(); i++ ) {
                String crate = startPile.remove(startPile.size() - 1);

                destinationPile.add(destinationPileSize, crate);
            }
        });

        cargoStorage.forEach(System.out::println);
    }

    private static void parseFileLines(List<String> lines) {
        List<String> startingScenarioDescription = new ArrayList<>();

        for (String line: lines.toArray(String[]::new)) {
            lines.remove(line);

            if (lines.isEmpty() || line.isBlank()) break;

            startingScenarioDescription.add(line);
        }
        for (String line: lines) {
            line = line
                    .replace("move ", "")
                    .replace(" from ", "-")
                    .replace(" to ", "-");

            String[] instructions = line.split("-");

            cargoInstructions.add(new CargoCrateInstructions(
                Integer.parseInt(instructions[0]),
                Integer.parseInt(instructions[1]),
                Integer.parseInt(instructions[2])
            ));
        }
        String numberOfPiles = startingScenarioDescription.get(startingScenarioDescription.size() - 1);
        int finalPileNumber = Integer.parseInt(numberOfPiles.trim().substring(numberOfPiles.trim().length() - 1));

        startingScenarioDescription.remove(numberOfPiles);

        for (int i = 0; i < finalPileNumber; i++) {
            cargoStorage.add(new ArrayList<>());
        }
        for (String line: startingScenarioDescription) {
            for (int i = 0; i < finalPileNumber; i++) {
                char cargoBox = line.charAt(1 + 4 * i);

                if (!Character.isAlphabetic(cargoBox)) continue;

                cargoStorage.get(i).add(0, Character.toString(cargoBox));
            }
        }
    }
}
