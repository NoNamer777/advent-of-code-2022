import utils.SimpleFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    private static final char CODE_OPPONENT_ROCK = 'A';
    private static final char CODE_OPPONENT_PAPER = 'B';
    private static final char CODE_OPPONENT_SCISSORS = 'C';
    private static final char CODE_RESPONSE_ROCK = 'X';
    private static final char CODE_RESPONSE_PAPER = 'Y';
    private static final char CODE_RESPONSE_SCISSORS = 'Z';

    private static final Map<Character, Integer> SCORE_BONUS_PER_MOVE = Map.of(
        CODE_RESPONSE_ROCK, 1,
        CODE_RESPONSE_PAPER, 2,
        CODE_RESPONSE_SCISSORS, 3
    );

    private static final int SCORE_TIE = 3;
    private static final int SCORE_WIN = 6;

    public static void main(String... inputs) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("rock-paper-scissors-tournament.txt")).getFile());
        int score = 0;

        List<char[]> outcomeRounds = parseFileLines(SimpleFileReader.readFile(file));

        if (outcomeRounds == null) return;

        for (char[] round: outcomeRounds) {
            char opponentMove = round[0];
            char move = round[1];

            score += SCORE_BONUS_PER_MOVE.get(move);

            if (isTie(opponentMove, move)) {
                score += SCORE_TIE;
            }
            else if (isWin(opponentMove, move)) {
                score += SCORE_WIN;
            }
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

    private static boolean isTie(char opponentMove, char move) {
        return (opponentMove == CODE_OPPONENT_ROCK && move == CODE_RESPONSE_ROCK)
            || (opponentMove == CODE_OPPONENT_PAPER && move == CODE_RESPONSE_PAPER)
            || (opponentMove == CODE_OPPONENT_SCISSORS && move == CODE_RESPONSE_SCISSORS);
    }

    private static boolean isWin(char opponentMove, char move) {
        return (move == CODE_RESPONSE_ROCK && opponentMove == CODE_OPPONENT_SCISSORS)
            || (move == CODE_RESPONSE_PAPER && opponentMove == CODE_OPPONENT_ROCK)
            || (move == CODE_RESPONSE_SCISSORS && opponentMove == CODE_OPPONENT_PAPER);
    }
}
