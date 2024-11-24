package com.example.komodo_hub;

public class News {
    private String title;
    private String description;
    private String link;
    private String filePath;

    public News(String title, String description, String link, String filePath) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.filePath = filePath;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getFilePath() {
        return filePath;
    }
}

