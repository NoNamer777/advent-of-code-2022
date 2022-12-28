package models;

import java.util.ArrayList;
import java.util.List;

public class Dir {
    private final String name;
    private Dir superDirectory;
    private final List<File> files;
    private final List<Dir> subdirectories;

    public Dir(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.subdirectories = new ArrayList<>();
    }
    
    public String getName() {
        return this.name;
    }

    public Dir getSuperDirectory() {
        return this.superDirectory;
    }

    public void setSuperDirectory(Dir superDirectory) {
        this.superDirectory = superDirectory;
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public List<File> getFiles() {
        return this.files;
    }

    public void addSubdirectory(Dir subdirectory) {
        this.subdirectories.add(subdirectory);

        subdirectory.setSuperDirectory(this);
    }

    public List<Dir> getSubdirectories() {
        return this.subdirectories;
    }
    
    public int getTotalSize() {
        int totalSize = this.files.stream().mapToInt(File::size).sum();
        
        totalSize += this.subdirectories.stream().mapToInt(Dir::getTotalSize).sum();
        
        return totalSize;
    }
    
    public String getDetails() {
        return this.getDetails(0);
    }
    
    public String getDetails(int indents) {
        StringBuilder builder = new StringBuilder("  ".repeat(indents));
        
        builder.append(String.format("- %s (dir)\n", this.name));

        for (Dir subdirectory: this.subdirectories) {
            builder.append(subdirectory.getDetails(indents + 1));
        }
        for (File file: this.files) {
            builder.append("  ".repeat(indents + 1)).append(file.toString()).append("\n");
        }
        return builder.toString();
    }
}
