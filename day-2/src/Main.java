import utils.SimpleFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    private static final char MOVE_ROCK = 'A';
    private static final char MOVE_PAPER = 'B';
    private static final char MOVE_SCISSORS = 'C';

    private static final Map<Character, Integer> SCORE_BONUS_PER_MOVE = Map.of(
        MOVE_ROCK, 1,
        MOVE_PAPER, 2,
        MOVE_SCISSORS, 3
    );

    private static final char PREDICTED_ROUND_STATE_LOSE = 'X';
    private static final char PREDICTED_ROUND_STATE_TIE = 'Y';
    private static final char PREDICTED_ROUND_STATE_WIN = 'Z';

    private static final Map<Character, Integer> SCORE_BONUS_PER_OUTCOME_ROUND = Map.of(
        PREDICTED_ROUND_STATE_LOSE, 0,
        PREDICTED_ROUND_STATE_TIE, 3,
        PREDICTED_ROUND_STATE_WIN, 6
    );

    public static void main(String... inputs) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("rock-paper-scissors-tournament.txt")).getFile());
        List<char[]> roundOutcomePredictions = parseFileLines(SimpleFileReader.readFile(file));

        if (roundOutcomePredictions == null) return;

        int score = 0;

        for (char[] round: roundOutcomePredictions) {
            char opponentMove = round[0];
            char roundOutcome = round[1];
            char predictedMove = predictMoveOfRound(opponentMove, roundOutcome);

            score += SCORE_BONUS_PER_MOVE.get(predictedMove) + SCORE_BONUS_PER_OUTCOME_ROUND.get(roundOutcome);
        }
        System.out.printf("My total score of this tournament is: %d\n", score);
    }

    private static List<char[]> parseFileLines(List<String> fileLines) {
        if (fileLines == null) return null;

        List<char[]> outcomeRounds = new ArrayList<>();

        for (String line: fileLines) {
            char[] round = new char[2];

            round[0] = line.charAt(0);
            round[1] = line.charAt(line.length() - 1);

            outcomeRounds.add(round);
        }
        return outcomeRounds;
    }

    private static char predictMoveOfRound(char opponentMove, char roundOutcome) {
        if (roundOutcome == PREDICTED_ROUND_STATE_TIE) {
            return switch (opponentMove) {
                case MOVE_ROCK -> MOVE_ROCK;
                case MOVE_PAPER -> MOVE_PAPER;
                default -> MOVE_SCISSORS;
            };
        }
        if (roundOutcome == PREDICTED_ROUND_STATE_WIN) {
            return switch (opponentMove) {
                case MOVE_ROCK -> MOVE_PAPER;
                case MOVE_PAPER -> MOVE_SCISSORS;
                default -> MOVE_ROCK;
            };
        }
        return switch (opponentMove) {
            case MOVE_ROCK -> MOVE_SCISSORS;
            case MOVE_PAPER -> MOVE_ROCK;
            default -> MOVE_PAPER;
        };
    }
}
