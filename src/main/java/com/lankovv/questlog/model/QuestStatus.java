package com.lankovv.questlog.model;

public enum QuestStatus {

    INCOMPLETE("Incomplete"),
    COMPLETE("Complete"),
    FAILED("Failed");

    String name;

    QuestStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
