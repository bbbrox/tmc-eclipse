package fi.helsinki.cs.tmc.core.services.http;

public enum UrlExtension {
    COURSES("courses.json"), EXERCISES("courses/");

    private String extension;

    private UrlExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getExtension(String id) {
        return extension + id + ".json";
    }
}
