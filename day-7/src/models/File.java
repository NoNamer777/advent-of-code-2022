package models;

public record File(String name, int size) {
    @Override
    public String toString() {
        return String.format("- %s (file, size=%d)", this.name, this.size);
    }
}
