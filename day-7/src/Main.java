import models.Dir;
import utils.SimpleFileReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("commands-input.txt")).getFile());

        Dir directory = parseFileLines(Objects.requireNonNull(SimpleFileReader.readFile(file)));

        System.out.println(directory.getDetails());
        
        directory.getTotalSize();
    }

    private static Dir parseFileLines(List<String> lines) {
        String command;
        Dir directory = null;
        
        for (String line: lines) {
            if (line.startsWith("$")) {
                command = line.replace("$ ", "");
                
                if (command.startsWith("cd")) {
                    String dirName = command.replace("cd ", "");
                    
                    if (!dirName.equals("..")) {
                        if (directory == null) {
                            directory = new Dir(dirName);
                        }
                        else {
                            Dir subDir = new Dir(dirName);
                            directory.addSubdirectory(subDir);

                            directory = subDir;
                        }
                    }
                    else {
                        if (directory != null && directory.getSuperDirectory() != null) {
                            directory = directory.getSuperDirectory();
                        }
                    }
                }
                continue;
            }
            if (!line.startsWith("dir")) {
                String[] fileInfo = line.split(" ");
                models.File file = new models.File(fileInfo[1], Integer.parseInt(fileInfo[0]));
                
                directory.addFile(file);
            }
        }
        if (directory != null) {
            while (directory.getSuperDirectory() != null) {
                directory = directory.getSuperDirectory();
            }
        }
        return directory;
    }
}
