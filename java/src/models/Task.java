package models;

public class Task {
    private int id;
    private String title;
    private boolean complete;

    public Task(int id, String title, boolean complete) {
        this.id = id;
        this.title = title;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isComplete() {
        return complete;
    }
}
