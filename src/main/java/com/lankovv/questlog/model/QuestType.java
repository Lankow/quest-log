package com.lankovv.questlog.model;

public enum QuestType {

    MAIN("Main Quest"),
    SIDE("Side Quest"),
    DAILY("Daily Quest");

    String name;

    QuestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
