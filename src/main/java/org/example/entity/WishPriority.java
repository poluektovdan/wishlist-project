package org.example.entity;

public enum WishPriority {
    ONE("★"),
    TWO("★★"),
    THREE("★★★"),
    FOUR("★★★★"),
    FIVE("★★★★★");

    private final String priority;

    private WishPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }
}
