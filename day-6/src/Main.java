import utils.SimpleFileReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("datastream.txt")).getFile());

        parseFileLines(Objects.requireNonNull(SimpleFileReader.readFile(file)));
    }

    private static void parseFileLines(List<String> lines) {
        String stream = lines.get(0);
        String streamWindow;
        int i;

        for (i = 4; i < stream.length(); i++) {
            // Go through the data stream inspecting 4 characters at a time.
            streamWindow = stream.substring(i - 4, i);
            
            boolean duplicatesFound = false;

            for (char c: streamWindow.toCharArray()) {
                String processStreamWindow = streamWindow.replace(Character.toString(c), "");
                
                if (duplicatesFound) break;
                
                // Check how many characters are replaced. If only 1 character is replaced, this means
                // That there was only 1 of such character in the streamWindow, meaning that we found the start-of-packet marker.
                duplicatesFound = processStreamWindow.length() <= 2;
            }
            if (!duplicatesFound) break;
        }
        System.out.println(i);
    }
}
